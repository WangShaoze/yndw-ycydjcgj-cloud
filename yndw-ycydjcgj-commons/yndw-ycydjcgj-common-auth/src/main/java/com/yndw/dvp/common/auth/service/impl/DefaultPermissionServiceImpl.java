package com.yndw.dvp.common.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.constant.CommonConstant;
import com.yndw.dvp.common.core.context.TenantContextHolder;
import com.yndw.dvp.common.core.model.SecurityMenu;
import com.yndw.dvp.common.auth.properties.SecurityProperties;
import com.yndw.dvp.common.auth.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 请求权限判断service
 *
 * 
 * @date 2018/10/28
 */
@Slf4j
public abstract class DefaultPermissionServiceImpl {



    @Autowired
    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * 查询当前用户拥有的资源权限
     * @param jsbhs
     * @return
     */
    public abstract List<SecurityMenu> findMenuByJsbhs(String jsbhs);

    public abstract List<SecurityMenu> findGnxxByCzybh(String czybh);

    public abstract List<SecurityMenu> authGnxxByCzy(String czybh);

    public boolean hasPermission(Authentication authentication, String requestMethod, String requestURI) {
        // 前端跨域OPTIONS请求预检放行 也可通过前端配置代理实现
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(requestMethod)) {
            return true;
        }
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //判断是否开启url权限验证
            if (!securityProperties.getAuth().getUrlPermission().getEnable()) {
                return true;
            }
            //超级管理员admin不需认证
            String username = AuthUtils.getUsername(authentication);
            if (CommonConstant.ADMIN_USER_NAME.equals(username)) {
                return true;
            }

            OAuth2Authentication auth2Authentication = (OAuth2Authentication)authentication;
            //判断应用黑白名单
            if (!isNeedAuth(auth2Authentication.getOAuth2Request().getClientId())) {
                return true;
            }

            //判断不进行url权限认证的api，所有已登录用户都能访问的url
            for (String path : securityProperties.getAuth().getUrlPermission().getIgnoreUrls()) {
                if (antPathMatcher.match(path, requestURI)) {
                    return true;
                }
            }

            List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return false;
            }

            //保存租户信息
            String clientId = auth2Authentication.getOAuth2Request().getClientId();
            TenantContextHolder.setTenant(clientId);

            String jsbhs = grantedAuthorityList.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.joining(", "));
            String czybh = AuthUtils.getCzybh(authentication);

            //按角色鉴权
//            List<SecurityMenu> securityMenuList = findMenuByJsbhs(jsbhs);

            //按操作员编号鉴权
            ExecutorService es = Executors.newFixedThreadPool(20); //开启两个线程
            List<SecurityMenu> securityGnxxList = authGnxxByCzy(czybh);
            Race race = new Race(securityGnxxList,requestURI,requestMethod);
            Future<Boolean> future= es.submit(race);
            try {
                log.info("future线程返回数据："+future.get());
                if (future.get()){
                    es.shutdown();
                    return true;
                }else{
                    es.shutdown();
                    return false;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
//            for (SecurityMenu securityMenu : securityGnxxList) {
//                if (StringUtils.isNotEmpty(securityMenu.getGnljdz()) && antPathMatcher.match(securityMenu.getGnljdz(), requestURI)) {
//                    if (StrUtil.isNotEmpty(securityMenu.getGnqqff())) {
//                        return requestMethod.equalsIgnoreCase(securityMenu.getGnqqff());
//                    } else {
//                        return true;
//                    }
//                }
//            }

        }

        return false;
    }

    /**
     * 判断应用是否满足白名单和黑名单的过滤逻辑
     * @param clientId 应用id
     * @return true(需要认证)，false(不需要认证)
     */
    private boolean isNeedAuth(String clientId) {
        boolean result = true;
        //白名单
        List<String> includeClientIds = securityProperties.getAuth().getUrlPermission().getIncludeClientIds();
        //黑名单
        List<String> exclusiveClientIds = securityProperties.getAuth().getUrlPermission().getExclusiveClientIds();
        if (includeClientIds.size() > 0) {
            result = includeClientIds.contains(clientId);
        } else if(exclusiveClientIds.size() > 0) {
            result = !exclusiveClientIds.contains(clientId);
        }
        return result;
    }

}

// 多线程
class Race implements Callable<Boolean> {

    private List<SecurityMenu>  securityMenus;

    private String  requestURI;

    private String requestMethod;

    private AntPathMatcher antPathMatcher  = new AntPathMatcher();;

    public Race(List<SecurityMenu>  securityMenus, String requestURI, String requestMethod){
        this.securityMenus = securityMenus;
        this.requestURI = requestURI;
        this.requestMethod = requestMethod;
    }

    @Override
    public Boolean call() throws Exception {
        for (SecurityMenu securityMenu : securityMenus) {
            if (StringUtils.isNotEmpty(securityMenu.getGnljdz()) && antPathMatcher.match(securityMenu.getGnljdz(), requestURI)) {
                if (StrUtil.isNotEmpty(securityMenu.getGnqqff())) {
                    return requestMethod.equalsIgnoreCase(securityMenu.getGnqqff());
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
