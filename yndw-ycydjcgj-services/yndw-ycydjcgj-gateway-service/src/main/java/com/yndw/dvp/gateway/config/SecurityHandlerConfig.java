package com.yndw.dvp.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yndw.dvp.common.core.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 */
@Slf4j
@Configuration
public class SecurityHandlerConfig {
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 未登录，返回401
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> ResponseUtil.responseUnauthorized(objectMapper, response, authException.getMessage());
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    /**
     * 处理spring security oauth 处理失败返回消息格式
     */
    @Bean
    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler() {

            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
                authException.printStackTrace();
                if (authException instanceof MissingCsrfTokenException) {
                    ResponseUtil.responseFailed(objectMapper, response, "缺少跨站TOKEN");
                }else if(authException instanceof InvalidCsrfTokenException){
                    ResponseUtil.responseFailed(objectMapper, response, "跨站TOKEN不匹配");
                }else{
                    ResponseUtil.responseFailed(objectMapper, response, authException.getMessage());
                }
            }
        };
    }
}
