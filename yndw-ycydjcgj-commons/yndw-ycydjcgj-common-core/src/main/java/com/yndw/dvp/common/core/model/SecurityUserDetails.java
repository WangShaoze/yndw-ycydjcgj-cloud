package com.yndw.dvp.common.core.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 用户实体绑定spring security
 */
@Getter
@Setter
public class SecurityUserDetails extends SecurityUser implements SocialUserDetails {
    private static final long serialVersionUID = -3685249101751401211L;

    private Set<String> permissions;

    /***
     * 权限重写
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (!CollectionUtils.isEmpty(super.getSecurityRoleList())) {
            super.getSecurityRoleList().parallelStream().forEach(role -> collection.add(new SimpleGrantedAuthority(role.getBh())));
        }
        return collection;
    }

    @Override
    public String getPassword() {
        return getDlmm();
    }

    @Override
    public String getUsername() {
        return getDlzh();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StrUtil.equals(getCzyztdm(), ZtdmConstant.NORMAL.getValue());
    }

    @Override
    public String getUserId() {
        return getBh();
    }
}
