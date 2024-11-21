package com.yndw.dvp.common.core.feign.fallback;

import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.feign.SecurityUserService;
import com.yndw.dvp.common.core.model.SecurityUserDetails;
import com.yndw.dvp.common.core.model.SecurityUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 降级工场
 *
 */
@Slf4j
public class SecurityUserServiceallbackFactory implements FallbackFactory<SecurityUserService> {
    @Override
    public SecurityUserService create(Throwable throwable) {
        return new SecurityUserService() {
            @Override
            public SecurityUser getUserByDlzh(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                SecurityUserDetails userDetails = new SecurityUserDetails();
                userDetails.setBh("-1");
                return userDetails;
            }

            @Override
            public SecurityUserDetails getUserDetailsByDlzh(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                SecurityUserDetails userDetails = new SecurityUserDetails();
                userDetails.setBh("-1");
                return userDetails;
            }

            @Override
            public SecurityUserDetails getUserDetailsByCzybh(String bh) {
                log.error("通过用户查询用户异常:{}", bh, throwable);
                SecurityUserDetails userDetails = new SecurityUserDetails();
                userDetails.setBh("-1");
                return userDetails;
            }

            @Override
            public Boolean lockCzy(String czybh, String isLock) {
                return false;
            }

            @Override
            public Boolean updateDlmmycwcs(String czybh) {
                return false;
            }

            @Override
            public Boolean updateDlmmycwcsToZero(String czybh) {
                return false;
            }
        };
    }
}
