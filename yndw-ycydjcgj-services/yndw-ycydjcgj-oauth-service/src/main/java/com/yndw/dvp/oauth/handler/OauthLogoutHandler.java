package com.yndw.dvp.oauth.handler;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.auth.util.AuthUtils;
import com.yndw.dvp.common.redis.template.RedisRepository;
import com.yndw.dvp.oauth.factory.LoginLogFactory;
import com.yndw.dvp.oauth.model.OAuthLoginLog;
import com.yndw.dvp.oauth.service.IOAuthLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @date 2018/10/17
 */
@Slf4j
public class OauthLogoutHandler implements LogoutHandler {
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private IOAuthLoginLogService loginLogService;
	@Autowired
	private RedisRepository redisRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Assert.notNull(tokenStore, "tokenStore must be set");
		String token = request.getParameter("token");
		if (StrUtil.isEmpty(token)) {
			token = AuthUtils.extractToken(request);
		}
		if(StrUtil.isNotEmpty(token)){
			OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
			OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(existingAccessToken);
			final String principalName = auth2Authentication.getName();
			OAuth2RefreshToken refreshToken;
			if (existingAccessToken != null) {
				if (existingAccessToken.getRefreshToken() != null) {
					log.info("remove refreshToken!", existingAccessToken.getRefreshToken());
					refreshToken = existingAccessToken.getRefreshToken();
					tokenStore.removeRefreshToken(refreshToken);
				}
				log.info("remove existingAccessToken!", existingAccessToken);
				tokenStore.removeAccessToken(existingAccessToken);

				redisRepository.del("x-csrf-token:"+principalName);
				saveLoginLog(auth2Authentication,request);
			}
		}
	}

	private void saveLoginLog(OAuth2Authentication authentication,HttpServletRequest request){
		OAuthLoginLog loginLog = LoginLogFactory.createLogout(authentication,request);
		loginLogService.saveLoginLog(loginLog);
	}
}
