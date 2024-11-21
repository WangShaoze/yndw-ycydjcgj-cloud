package com.yndw.dvp.oauth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.constant.SecurityConstants;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.context.TenantContextHolder;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.feign.SecurityUserService;
import com.yndw.dvp.common.core.utils.JsonUtil;
import com.yndw.dvp.common.redis.template.RedisRepository;
import com.yndw.dvp.oauth.service.DefaultUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import com.yndw.dvp.common.core.model.SecurityUserDetails;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Slf4j
@Service
public class LoginUserDetailsServiceImpl implements DefaultUserDetailsService, SocialUserDetailsService {
    @Resource
    private SecurityUserService securityUserService;
    @Autowired
    private RedisRepository redisRepository;
    @Value("${dvp.max.session.connetions}")
    private Integer maxSessionConnetions;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SecurityUserDetails securityUserDetails = securityUserService.getUserDetailsByDlzh(username);
        if (securityUserDetails == null) {
            throw new InternalAuthenticationServiceException("用户名不存在或密码错误");
        }
        if (securityUserDetails != null && StrUtil.equals(securityUserDetails.getBh(),"-1")) {
            throw new BusinessException("服务异常");
        }
        return checkUser(securityUserDetails);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) {
        SecurityUserDetails securityUserDetails = securityUserService.getUserDetailsByCzybh(userId);
        return checkUser(securityUserDetails);
    }

    private SecurityUserDetails checkUser(SecurityUserDetails securityUserDetails) {
        String redisUserKey = SecurityConstants.REDIS_UNAME_TO_ACCESS + TenantContextHolder.getTenant() + ":" + securityUserDetails.getDlzh();
        long size = redisRepository.length(redisUserKey);
        if( size > 0){
            throw new BusinessException("账户已登录!");
        }
        redisUserKey = SecurityConstants.REDIS_UNAME_TO_ACCESS + TenantContextHolder.getTenant()+ ":";
        Set<String> keys = redisRepository.keys(redisUserKey);
        if( keys.size() > maxSessionConnetions){
            throw new BusinessException("会话数已到限制："+maxSessionConnetions);
        }
        if (securityUserDetails != null && !securityUserDetails.isEnabled()) {
            if(StrUtil.equals(securityUserDetails.getCzyztdm(), ZtdmConstant.STOP.getValue())){
                throw new BusinessException("账户已停用!");
            }
            if(StrUtil.equals(securityUserDetails.getCzyztdm(), ZtdmConstant.LOCK.getValue())){
                throw new BusinessException("账户已锁定!");
            }
            throw new BusinessException("账户已作废!");
        }
        return securityUserDetails;
    }

}
