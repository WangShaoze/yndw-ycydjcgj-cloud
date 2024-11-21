package com.yndw.dvp.gateway.filter.pre;

import cn.hutool.core.util.StrUtil;
import com.yndw.dvp.common.auth.util.AuthUtils;
import com.yndw.dvp.common.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

/**
 * Description: 自定义的 {@link CsrfTokenRepository}<br>
 * Details: 基于 Redis 的实现: 依托 Redis 读取, 刷新 CSRF-TOKEN.<br>
 * 执行顺序: <br>
 * <ol>
 *     <li>{@link CustomCsrfTokenRedisRepository#loadToken(HttpServletRequest)}</li>
 *     <li>{@link CustomCsrfTokenRedisRepository#generateToken(HttpServletRequest)}</li>
 *     <li>{@link CustomCsrfTokenRedisRepository#saveToken(CsrfToken, HttpServletRequest, HttpServletResponse)}</li>
 * </ol>
 * Create By Carlos
 * 2020/12/18
 */
@Slf4j
@Component
public class CustomCsrfTokenRedisRepository {

    private static final String DASH = "-";

    private static final String CSRF_PARAMETER_NAME = "_csrf";

    /**
     * CSRF-TOKEN 存在于响应头中的名称
     */
    private static final String CSRF_HEADER_NAME = "X-CSRF-TOKEN";

    /**
     * Authorization 在请求头中的名称
     */
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    /**
     * ACCESS-TOKEN 类型
     */
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    /**
     * 请求参数中的指纹码的名称
     */
    private static final String FORM_FINGERPRINT = "fingerprint";

    /**
     * 由授权服务器颁发的, 缓存的 csrf-token 的 Key 的前缀
     */
    private static final String CACHE_PREFIX_CSRF_TOKEN = /*"authorization-server." + */Strings.toLowerCase(CSRF_HEADER_NAME);

    // -----------------------------------------------------------------------------------------------------------------

    @Autowired
    private RedisRepository redisService;

    @Autowired
    private TokenStore tokenStore;

    // =================================================================================================================

    public CsrfToken generateToken() {
        log.debug("Generate token.");
        final String token = StringUtils.replace(UUID.randomUUID().toString(), DASH, StringUtils.EMPTY);
        return new DefaultCsrfToken(CSRF_HEADER_NAME, CSRF_PARAMETER_NAME, token);
    }

    /**
     * Description: 将 CSRF-TOKEN 保存至 Redis
     *
     * @see CsrfTokenRepository#saveToken(CsrfToken, HttpServletRequest, HttpServletResponse)
     */
    public void saveToken(CsrfToken token, String dlzh, HttpServletResponse response) {
        log.debug("Save token for {}", dlzh);
        if(StrUtil.isBlank(dlzh)){
            log.debug("csrf filter: exec login");
            return;
        }
        // 由于过期时间交由 Redis 管理, 所以 token 为 null 时, 不进行任何操作直接返回.
        if (Objects.isNull(token)) {
            log.debug("csrf filter: do nothing while token is null. The token's lifecycle will be handled by Redis.");
            return;
        }

        final String tokenValue = token.getToken();
        String key = CACHE_PREFIX_CSRF_TOKEN + ":" + dlzh;
        // 刷新 CSRF-TOKEN
        redisService.set(key, tokenValue);
        // 返回 CSRF-TOKEN
        response.setHeader(CSRF_HEADER_NAME, tokenValue);
    }

    /**
     * Description: 从 Redis 中读取期望的 CSRF-TOKEN
     *
     * @see CsrfTokenRepository#loadToken(HttpServletRequest)
     */
    public CsrfToken loadToken(String dlzh) {
        log.debug("Load token from request.");

        if(StrUtil.isBlank(dlzh)){
            return null;
        }

        String key = CACHE_PREFIX_CSRF_TOKEN + ":" + dlzh;

        // 获取期望 CSRF-TOKEN
        final Object cachedToken = redisService.get(key);

        if (cachedToken != null && StringUtils.isNotBlank(cachedToken.toString())) {
            return new DefaultCsrfToken(CSRF_HEADER_NAME, CSRF_PARAMETER_NAME, cachedToken.toString());
        }
        return null;
    }

}