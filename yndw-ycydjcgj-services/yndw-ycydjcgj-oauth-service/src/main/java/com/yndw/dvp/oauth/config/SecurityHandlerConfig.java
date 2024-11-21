package com.yndw.dvp.oauth.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.constant.ZtdmConstant;
import com.yndw.dvp.common.core.context.SecurityDlzhContextHolder;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.feign.SecurityUserService;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.utils.JsonUtil;
import com.yndw.dvp.oauth.exception.ValidateCodeException;
import com.yndw.dvp.oauth.factory.LoginLogFactory;
import com.yndw.dvp.oauth.handler.OauthLogoutHandler;
import com.yndw.dvp.oauth.model.OAuthLoginLog;
import com.yndw.dvp.oauth.service.IOAuthLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 认证错误处理
 */
@Slf4j
@Configuration
public class SecurityHandlerConfig {
    /**
     * #### 最大登录错误次数
     */
    @Value("${dvp.czyxx.dlmmycwcs}")
    private int dlmmycwcs;

    /**
     * #### #### 登录错误时间限定,如10分钟内登录错误{dvp.czyxx.dlmmycwcs}-5次
     * dvp.czyxx.dlmmcwsjxd=10
     */
    @Value("${dvp.czyxx.dlmmcwsjxd}")
    private int dlmmcwsjxd;

    /**
     * #### 账户锁定时间 单位：分钟
     */
    @Value("${dvp.czyxx.dlmmsdsj}")
    private int dlmmsdsj;

    @Resource
    private SecurityUserService securityUserService;

    @Autowired
    private IOAuthLoginLogService loginLogService;

    @Bean
    public OauthLogoutHandler oauthLogoutHandler() {
        return new OauthLogoutHandler();
    }

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            private static final String BAD_MSG = "坏的凭证";
            private static final String BAD_MSG_EN = "Bad credentials";

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                OAuth2Exception oAuth2Exception;
                if (e.getMessage() != null
                        && (BAD_MSG.equals(e.getMessage()) || BAD_MSG_EN.equals(e.getMessage()))) {
                    oAuth2Exception = pwdError(e);
                } else if (e instanceof InternalAuthenticationServiceException
                        || e instanceof ValidateCodeException) {
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else if (e instanceof OAuth2Exception) {
                    oAuth2Exception = (OAuth2Exception) e;
                } else {
                    oAuth2Exception = new UnsupportedResponseTypeException("服务内部错误", e);
                }
                ResponseEntity<OAuth2Exception> response = super.translate(oAuth2Exception);
                ResponseEntity.status(oAuth2Exception.getHttpErrorCode());
                response.getBody().addAdditionalInformation("code", oAuth2Exception.getHttpErrorCode() + "");
                response.getBody().addAdditionalInformation("msg", oAuth2Exception.getMessage());

                return response;
            }
        };
    }

    /**
     * 登陆成功
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    // 限制登录次数 > 5 就锁定
    private OAuth2Exception pwdError(Exception e) {
        try {
            SecurityUser securityUser = securityUserService.getUserByDlzh(SecurityDlzhContextHolder.getDlzh());
            //账户锁定时间为 限定时间，30分钟
            if (!StrUtil.equals(securityUser.getCzyztdm(), ZtdmConstant.LOCK.getValue())) {
                if (securityUser.getDlmmdyccwsj() != null) {
                    // 登录错误时间限定
                    long dlmmcwsjxdMs = dlmmcwsjxd * 60;
                    long sjc = DateUtil.betweenMs(securityUser.getDlmmdyccwsj(), new Date()) / 1000;
                    if (sjc > dlmmcwsjxdMs) {
                        securityUser.setDlmmycwcs(0);
                        securityUser.setDlmmdyccwsj(null);
                        securityUserService.updateDlmmycwcsToZero(securityUser.getBh());
                    }
                }
                //登录错误 dlmmycwcs +1
                securityUserService.updateDlmmycwcs(securityUser.getBh());
                securityUser.setDlmmycwcs(securityUser.getDlmmycwcs() + 1);
                SecurityDlzhContextHolder.clear();
                //登录错误次数 >= 登录限定错误次数   锁定账户
                if (securityUser.getDlmmycwcs() >= dlmmycwcs) {
                    securityUserService.lockCzy(securityUser.getBh(), "true");
                    // return new InvalidGrantException("密码错误次数超出" + dlmmycwcs + "次限制，账户已锁定，请" + dlmmsdsj + "分钟后重试！", e);
                    return new InvalidGrantException("账户已锁定", e);
                }
                return new InvalidGrantException("用户名不存在或密码错误", e);
                // return new InvalidGrantException("请注意：" + dlmmcwsjxd + "分钟内，密码错误次数超过" + dlmmycwcs + "次，账户将锁定" + dlmmsdsj + "分钟，还剩" + (dlmmycwcs - securityUser.getDlmmycwcs()) + "次！", e);
            }
        } catch (Exception ee) {
            return new InvalidGrantException(ee.getMessage(), ee);
        }
        return new InvalidGrantException("用户名不存在或密码错误", e);
    }
}
