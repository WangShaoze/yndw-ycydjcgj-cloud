package com.yndw.dvp.gateway.service.impl;

import com.yndw.dvp.common.auth.service.impl.DefaultPermissionServiceImpl;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.gateway.feign.SecurityMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 请求权限判断service
 *
 *
 * @date 2018/10/28
 */
@Slf4j
@Service("permissionService")
public class PermissionServiceImpl extends DefaultPermissionServiceImpl {
    @Resource
    private SecurityMenuService securityMenuService;

    @Override
    public List<SecurityMenu> findMenuByJsbhs(String roleCodes) {
        return securityMenuService.findMenuByJsbhs(roleCodes);
    }

    @Override
    public List<SecurityMenu> findGnxxByCzybh(String czybh) {
        return securityMenuService.findGnxxByCzybh(czybh);
    }

    @Override
    public List<SecurityMenu> authGnxxByCzy(String czybh) {
        return securityMenuService.authGnxxByCzy(czybh);
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        return hasPermission(authentication, request.getMethod(), request.getRequestURI());
    }
}
