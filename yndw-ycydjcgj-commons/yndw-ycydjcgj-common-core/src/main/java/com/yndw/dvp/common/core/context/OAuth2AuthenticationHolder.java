package com.yndw.dvp.common.core.context;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * Description: 与线程绑定的 {@link OAuth2Authentication} Holder<br>
 * Details: 区别于 {@link org.springframework.security.core.context.SecurityContextHolder}, {@link OAuth2AuthenticationHolder}
 * 持有的 {@link OAuth2Authentication} 独立于安全框架的认证逻辑之外.
 *
 * @author LiKe
 * @version 1.0.0
 * @date 2020-08-09 17:53
 */
public final class OAuth2AuthenticationHolder {

    private static final ThreadLocal<OAuth2Authentication> holder = new ThreadLocal<>();

    /**
     * Description: 获得与线程绑定的认证对象
     *
     * @return org.springframework.security.oauth2.provider.OAuth2Authentication
     * @author LiKe
     * @date 2020-08-10 10:58:36
     */
    public static OAuth2Authentication get() {
        return holder.get();
    }

    /**
     * Description: 将认证对象与线程绑定
     *
     * @param oAuth2Authentication {@link OAuth2Authentication}
     * @return void
     * @author LiKe
     * @date 2020-08-10 10:58:50
     */
    public static void set(OAuth2Authentication oAuth2Authentication) {
        holder.set(oAuth2Authentication);
    }

    /**
     * Description: 清除与线程绑定的认证对象
     *
     * @return void
     * @author LiKe
     * @date 2020-08-10 10:59:04
     */
    public static void clear() {
        holder.remove();
    }

}