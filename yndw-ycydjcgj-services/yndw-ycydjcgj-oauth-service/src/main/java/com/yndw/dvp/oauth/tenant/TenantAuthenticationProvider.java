package com.yndw.dvp.oauth.tenant;

import com.yndw.dvp.common.auth.token.TenantUsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 增加租户id，解决不同租户单点登录时角色没变化
 *
 * 
 * @date 2020/6/10
 * <p>
 * 
 * 
 */
public class TenantAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication, UserDetails user) {
        TenantUsernamePasswordAuthenticationToken authenticationToken = (TenantUsernamePasswordAuthenticationToken) authentication;
        TenantUsernamePasswordAuthenticationToken result = new TenantUsernamePasswordAuthenticationToken(
                principal, authentication.getCredentials(), user.getAuthorities(), authenticationToken.getClientId());
        result.setDetails(authenticationToken.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TenantUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
