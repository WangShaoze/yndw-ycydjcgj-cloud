package com.yndw.dvp.oauth.csrf.strategy;

import com.yndw.dvp.common.core.constant.GrantType;
import com.yndw.dvp.common.core.constant.SecurityConstants;
import com.yndw.dvp.common.core.context.ApplicationContext;
import com.yndw.dvp.common.core.context.OAuth2AuthenticationHolder;
import com.yndw.dvp.common.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;
import java.util.UUID;

/**
 * Description: CSRF 防护机制抽象策略标准<br>
 * Details:
 * <ul>
 *   <li>
 *       对于授权码模式, 密码模式, 隐式模式, 这个 CSRF-TOKEN 以用户 ID 标识, 接下来外部携带 ACCESS-TOKEN 和 CSRF-TOKEN 访问资源服务器时,
 *       每次会刷新 CSRF-TOKEN 并返回给前端.<br>
 *       <b>可以采用用户 ID + 用户指纹码来标识来自指定浏览器的用户的请求</b>.
 *   </li>
 *   <li>
 *       对于客户端模式来说, 本身仅用于后端受信任客户端的交互, 如果一定也要用 CSRF 机制, 也需要在授权服务器颁发 ACCESS-TOKEN 时,
 *       也同时生成 CSRF-TOKEN.<br>
 *       <b>可以采用客户端 ID + MD5(JVM 进程 ID + 线程 ID) 标识来自指定客户端的请求</b>.
 *   </li>
 * </ul>
 * <b>Fingerprint Mechanism</b> (指纹码机制): 在原有 OAuth 2.0 标准请求参数之外追加 "指纹码" 参数, 授权服务器首次办法令牌时, 以指纹码为 Key 的一部分缓存 CSRF-TOKEN,
 * 同时请求方以这个指纹码作为额外凭证随授权服务器颁发的 CSRF-TOKEN 请求资源服务器. 只要指纹码变动, CSRF-TOKEN 也会唯一对应.<br>
 * ☞ 对于客户端来说, 这个机制保证了同一客户端短期内多次请求可能导致的 CSRF-TOKEN 冲突问题: 同一客户端第一次请求, 刷新了 CSRF-TOKEN,
 * 与此同时在第一次请求返回之前, 第二次请求携带旧的 CSRF-TOKEN 继续请求, 就会导致 CSRF-TOKEN 冲突.<br>
 * ☞ 对于用户端, 指纹码可以区分同一用户在不同浏览器的请求.
 * <p>
 * Create By Carlos
 * 2020/12/18
 */
@Slf4j
public abstract class AbstractCsrfStrategy {

    public static final String CSRF_HEADER_NAME = "X-CSRF-TOKEN";

    /**
     * 指纹码 (用于区分请求)
     */
    protected static final String FORM_FINGERPRINT = "fingerprint";

    /**
     * 由授权服务器颁发的, 缓存的 csrf-token 的 Key 的前缀
     */
    private static final String CACHE_PREFIX_CSRF_TOKEN = /*"authorization-server." + */Strings.toLowerCase(CSRF_HEADER_NAME);

    private static final String DASH = "-";

    // ~ Template methods
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 子类实现: 提供授权类型
     */
    public abstract GrantType getGrantType();

    /**
     * Description: 判断当前策略是否支持这个请求
     *
     * @param parameters 请求参数 Map
     * @return boolean
     * @author LiKe
     * @date 2020-08-10 13:53:14
     */
    public abstract boolean supports(Map<String, ?> parameters);

    // =================================================================================================================

    /**
     * Description: 执行策略逻辑
     *
     * @param parameters 请求参数 Map
     * @return void
     * @author LiKe
     * @date 2020-08-10 14:05:18
     */
    public String execute(Map<String, ?> parameters) {
        final OAuth2Authentication oAuth2Authentication = OAuth2AuthenticationHolder.get();

        // ~ 请求方 ID, 用于构建出唯一请求标识
        String id;
        if (this.getGrantType() == GrantType.CLIENT_CREDENTIALS) {
            id = oAuth2Authentication.getName();
        } else {
            id = oAuth2Authentication.getUserAuthentication().getName();
        }

        final String uniqueRequestIdentifier = buildUniqueRequestIdentifier(id, parameters);
        final String token = generateToken();

        final String clientId = oAuth2Authentication.getOAuth2Request().getClientId();
        saveToken(uniqueRequestIdentifier, token, getValiditySeconds(clientId));

        OAuth2AuthenticationHolder.clear();
        return token;
    }

    /**
     * Description: 生成 CSRF-TOKEN
     *
     * @return void
     * @author LiKe
     * @date 2020-08-10 14:34:24
     */
    private String generateToken() {
        return StringUtils.replace(UUID.randomUUID().toString(), DASH, StringUtils.EMPTY);
    }

    /**
     * Description: 保存 CSRF-TOKEN<br>
     * Details: 与 OAuth2AccessToken 同一生命周期
     *
     * @param id              唯一请求标识
     * @param csrfToken       CSRF-TOKEN
     * @param validitySeconds CSRF-TOKEN 的有效时间 (秒)
     * @return void
     * @author LiKe
     * @date 2020-08-11 20:04:58
     */
    private void saveToken(String id, String csrfToken, int validitySeconds) {
        String key = CACHE_PREFIX_CSRF_TOKEN + ":" + id;
        ApplicationContext.getBean(RedisRepository.class).setExpire(key, csrfToken, validitySeconds);
    }

    /**
     * Description: 构建唯一请求标识<br>
     * Details: 会尝试从请求参数中获取名为 {@link AbstractCsrfStrategy#FORM_FINGERPRINT} 的参数, 作为缓存 Key 的一级标识: <br>
     * - 对用户端, 用以区分同一用户在不同浏览器的访问, 分别颁发 CSRF-TOKEN;<br>
     * - 对客户端, 用以区分同一客户端的同时多次的请求.
     *
     * @param id         用户端 或者 客户端 ID (client_credentials)
     * @param parameters 用于构建唯一请求标识的参数 Map
     * @return java.lang.String 唯一标识请求的 ID
     * @author LiKe
     * @date 2020-08-11 14:52:52
     * @see AbstractCsrfStrategy
     */
    private String buildUniqueRequestIdentifier(String id, Map<String, ?> parameters) {
        final String fingerprint = MapUtils.getString(parameters, FORM_FINGERPRINT);
        if (StringUtils.isNotBlank(fingerprint)) {
            return StringUtils.join(id, ":", fingerprint);
        }
        return id;
    }

    /**
     * Description: 获取 CSRF-TOKEN 的有效时间
     *
     * @param clientId 客户端 ID
     * @return int ACCESS-TOKEN 的有效时间 (秒), 同时作为 CSRF-TOKEN 的
     * @author LiKe
     * @date 2020-08-13 13:53:28
     */
    private int getValiditySeconds(String clientId) {
        String key = SecurityConstants.CACHE_CLIENT_KEY + ":" + clientId;
        Object clientDetails = ApplicationContext.getBean(RedisRepository.class).get(key);
        if (clientDetails != null) {
            return ((ClientDetails) clientDetails).getAccessTokenValiditySeconds();
        }
        return SecurityConstants.ACCESS_TOKEN_VALIDITY_SECONDS;
    }
}
