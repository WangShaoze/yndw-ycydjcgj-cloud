package com.yndw.dvp.oauth.handler;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.common.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @date 2020/3/10
 * <p>
 * 
 * 
 */
@Slf4j
public class OauthLogoutSuccessHandler implements LogoutSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		String redirectUri = request.getParameter("redirect_uri");
		if (StrUtil.isNotEmpty(redirectUri)) {
			//重定向指定的地址
			redirectStrategy.sendRedirect(request, response, redirectUri);
		} else {
			response.setStatus(HttpStatus.OK.value());
			response.setCharacterEncoding("UTF-8");
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			PrintWriter writer = response.getWriter();
			String jsonStr = JsonUtil.toJSONString(CommonResult.succeed("登出成功"));
			writer.write(jsonStr);
			writer.flush();
		}
	}
}
