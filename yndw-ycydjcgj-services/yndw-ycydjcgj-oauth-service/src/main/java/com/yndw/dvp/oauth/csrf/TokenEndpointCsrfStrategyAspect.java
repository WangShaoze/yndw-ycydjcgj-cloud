package com.yndw.dvp.oauth.csrf;

import com.yndw.dvp.common.core.model.CommonResult;
import com.yndw.dvp.oauth.csrf.strategy.AbstractCsrfStrategy;
import com.yndw.dvp.oauth.csrf.strategy.PwdImgCodeCsrfStrategy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Description: {@link org.springframework.security.oauth2.provider.endpoint.TokenEndpoint} 切面.<br>
 * Details: 在颁发 ACCESS-TOKEN 后, 生成 CSRF-TOKEN, 将其缓存并置入响应头.
 *
 * @author LiKe
 * @version 1.0.0
 * @date 2020-08-11 16:58
 */
@Slf4j
@Aspect
@Component
public class TokenEndpointCsrfStrategyAspect extends AbstractCsrfStrategyAspect {

    /**
     * 所有支持 CSRF 策略的授权模式
     */
    private final List<? extends AbstractCsrfStrategy> delegates = new ArrayList<>(Arrays.asList(
            new PwdImgCodeCsrfStrategy()
    ));

    @Pointcut(
            "execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)"
    )
    public void pointcut() {
    }

    @Around("pointcut()")
    @SuppressWarnings("unchecked")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Object any = proceedingJoinPoint.proceed();

        try {
            final ResponseEntity<CommonResult> responseEntity = (ResponseEntity<CommonResult>) any;

            // ~ 获取请求参数
            final Map<String, ?> parameters = super.extractParameters(proceedingJoinPoint);

            // ~ 获取匹配的策略
            final AbstractCsrfStrategy strategy = super.decide(parameters, delegates);

            if (Objects.isNull(strategy)) {
                log.warn("No eligible strategy.");
                return responseEntity;
            }

            log.debug("Eligible strategy {} for grant type: {}.", strategy.getClass().getSimpleName(), strategy.getGrantType().getCode());

            // ~ 执行策略并返回 CSRF-TOKEN
            final String csrfToken = strategy.execute(parameters);

            // ~ 重新封装 ResponseEntity, 置入 CSRF-TOKEN
            final CommonResult commonResult = responseEntity.getBody();
            final HttpHeaders headers = new HttpHeaders();
            headers.set("Cache-Control", "no-store");
            headers.set("Pragma", "no-cache");
            headers.set(AbstractCsrfStrategy.CSRF_HEADER_NAME, csrfToken);
            return new ResponseEntity<>(commonResult, headers, HttpStatus.OK);
        } catch (ClassCastException exception) {
            log.warn(exception.getMessage());
            return any;
        }
    }

}
