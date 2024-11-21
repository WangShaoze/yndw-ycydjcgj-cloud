package com.yndw.dvp.oauth.filter;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.constant.SecurityConstants;
import com.yndw.dvp.common.core.context.SecurityDlzhContextHolder;
import com.yndw.dvp.common.core.context.TenantContextHolder;
import com.yndw.dvp.common.core.feign.SecurityUserService;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.oauth.factory.LoginLogFactory;
import com.yndw.dvp.oauth.model.OAuthLoginLog;
import com.yndw.dvp.oauth.service.IOAuthLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * oauth-token拦截器
 * 1. 赋值租户
 * 2. 统一返回token格式
 *
 * 
 * @date 2020/3/29
 * <p>
 * 
 * 
 */
@Slf4j
@Component
@Aspect
public class OauthTokenAspect {

    @Resource
    private SecurityUserService securityUserService;

    @Autowired
    private IOAuthLoginLogService loginLogService;

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object[] args = joinPoint.getArgs();
            Principal principal = (Principal) args[0];
            if (!(principal instanceof Authentication)) {
                throw new InsufficientAuthenticationException(
                        "There is no client authentication. Try adding an appropriate authentication filter.");
            }
            String clientId = getClientId(principal);
            String remoteIP = getRemoteIp(principal);
            Map<String, String> parameters = (Map<String, String>) args[1];
            String grantType = parameters.get(OAuth2Utils.GRANT_TYPE);

            //保存租户id
            TenantContextHolder.setTenant(clientId);
            Object proceed = joinPoint.proceed();
            if (SecurityConstants.AUTHORIZATION_CODE.equals(grantType)) {
                /*
                 如果使用 @EnableOAuth2Sso 注解不能修改返回格式，否则授权码模式可以统一改
                 因为本项目的 sso-demo/ss-sso 里面使用了 @EnableOAuth2Sso 注解，所以这里就不修改授权码模式的token返回值了
                 */
                return proceed;
            } else {
                ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
                OAuth2AccessToken body = responseEntity.getBody();
                loginSuccess(remoteIP);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(CommonResult.succeed(body));
            }
        } finally {
            TenantContextHolder.clear();
        }
    }

    private String getClientId(Principal principal) {
        Authentication client = (Authentication) principal;
        if (!client.isAuthenticated()) {
            throw new InsufficientAuthenticationException("The client is not authenticated.");
        }
        String clientId = client.getName();
        if (client instanceof OAuth2Authentication) {
            clientId = ((OAuth2Authentication) client).getOAuth2Request().getClientId();
        }
        return clientId;
    }

    private String getRemoteIp(Principal principal) {
        Authentication client = (Authentication) principal;
        WebAuthenticationDetails details = (WebAuthenticationDetails) client.getDetails();
        return details != null && StrUtil.isNotBlank(details.getRemoteAddress()) ? details.getRemoteAddress() : "127.0.0.1";
    }

    private void loginSuccess(String remoteIP){
        SecurityUser user = securityUserService.getUserDetailsByDlzh(SecurityDlzhContextHolder.getDlzh());
        securityUserService.updateDlmmycwcsToZero(user.getBh());
        this.saveLoginLog(user,remoteIP);
        SecurityDlzhContextHolder.clear();
    }

    private void saveLoginLog(SecurityUser user,String remoteIP){
        OAuthLoginLog loginLog = LoginLogFactory.createLogin(user,remoteIP);
        loginLogService.saveLoginLog(loginLog);
    }
}
