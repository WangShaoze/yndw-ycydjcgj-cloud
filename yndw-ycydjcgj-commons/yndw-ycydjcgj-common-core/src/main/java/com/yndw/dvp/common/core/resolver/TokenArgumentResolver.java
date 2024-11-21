package com.yndw.dvp.common.core.resolver;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.annotation.LoginUser;
import com.yndw.dvp.common.core.constant.SecurityConstants;
import com.yndw.dvp.common.core.feign.SecurityUserService;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.model.SecurityRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Token转化SecurityCzyxx
 *
 *
 * @date 2018/12/21
 */
@Slf4j
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {
    private SecurityUserService securityUserService;

    public TokenArgumentResolver(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    /**
     * 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class) && methodParameter.getParameterType().equals(SecurityUser.class);
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        LoginUser loginUser = methodParameter.getParameterAnnotation(LoginUser.class);
        boolean isFull = loginUser.isFull();
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String userId = request.getHeader(SecurityConstants.USER_ID_HEADER);
        String username = request.getHeader(SecurityConstants.USER_HEADER);
        String roles = request.getHeader(SecurityConstants.ROLE_HEADER);  //  x-role-header
        if (StrUtil.isBlank(username)) {
            log.warn("resolveArgument error username is empty");
            return null;
        }
        SecurityUser securityUser = null;
        if (isFull) {
            securityUser = securityUserService.getUserDetailsByDlzh(username);
        } else {
            securityUser = securityUserService.getUserDetailsByDlzh(username);
        }

        List<SecurityRole> securityRoleList = new ArrayList<>();
        Arrays.stream(roles.split(",")).forEach(role -> {
            SecurityRole securityRole = new SecurityRole();
            securityRole.setBh(role);
            securityRoleList.add(securityRole);
        });
        securityUser.setSecurityRoleList(securityRoleList);
        return securityUser;
    }
}
