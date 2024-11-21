package com.yndw.dvp.gateway.filter.pre;

import cn.hutool.core.collection.CollectionUtil;
import com.yndw.dvp.common.core.constant.CommonConstant;
import com.yndw.dvp.common.core.constant.SecurityConstants;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER;

/**
 * 将认证用户的相关信息放入header中, 后端服务可以直接读取使用
 *
 *
 * @author carlos
 * @date 2018/11/20
 */
@Component
public class CustomCsrfFilter extends ZuulFilter {

    private final HashSet<String> allowedMethods = new HashSet<>(
            Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));
    private final HashSet<String> allowedUrls = new HashSet<>(
            Arrays.asList(SecurityConstants.LOGIN_URL,SecurityConstants.DEFAULT_VALIDATE_CODE_URL,
                        CommonConstant.UPLOAD_URL));
    @Autowired
    private CustomCsrfTokenRedisRepository csrfTokenRedisRepository;
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FORM_BODY_WRAPPER_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof SecurityUser) {
                SecurityUser user = (SecurityUser)authentication.getPrincipal();
                RequestContext ctx = RequestContext.getCurrentContext();
                HttpServletRequest request = ctx.getRequest();
                //需要处理不放行的方法和地址:
                if (!this.allowRequest(request)) {
                    //获取csrf token
                    //      缺少csrf token
                    //校验csrftoken
                    //刷新csrftoken
                    CsrfToken csrfToken = csrfTokenRedisRepository.loadToken(user.getDlzh());
                    final boolean missingToken = csrfToken == null;
                    if (missingToken) {
                        throw new BusinessException("缺少跨站TOKEN");
                    }
                    String actualToken = request.getHeader(csrfToken.getHeaderName());
                    if (!csrfToken.getToken().equals(actualToken)) {
                        throw new BusinessException("跨站TOKEN不正确");
                    }
                    csrfToken = csrfTokenRedisRepository.generateToken();
                    csrfTokenRedisRepository.saveToken(csrfToken, user.getDlzh(), ctx.getResponse());
                    return null;
                }
            }
        }
        return null;
    }

    public boolean allowRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String allowedUrl : allowedUrls) {
            if (uri.startsWith(allowedUrl)) {
                return true;
            }
        }
        return this.allowedMethods.contains(request.getMethod());
    }
}
