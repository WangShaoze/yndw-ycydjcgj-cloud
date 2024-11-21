package com.yndw.dvp.common.core.config;

import com.yndw.dvp.common.core.feign.SecurityUserService;
import com.yndw.dvp.common.core.resolver.ClientArgumentResolver;
import com.yndw.dvp.common.core.resolver.TokenArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 默认SpringMVC拦截器
 *
 * 
 * @date 2019/8/5
 * <p>
 * 
 * 
 */
public class DefaultWebMvcConfig implements WebMvcConfigurer {
	@Lazy
	@Autowired
	private SecurityUserService securityUserService;

	/**
	 * Token参数解析
	 *
	 * @param argumentResolvers 解析类
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		//注入用户信息
		argumentResolvers.add(new TokenArgumentResolver(securityUserService));
		//注入应用信息
		argumentResolvers.add(new ClientArgumentResolver());
	}
}
