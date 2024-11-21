package com.yndw.dvp.gateway.csrf;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;

/**
 *  * Description: 总是匹配的 {@link RequestMatcher}
 * Create By Carlos
 * 2020/12/18
 */
@Deprecated
public class AlwaysMatchRequestMatcher implements RequestMatcher {
    private final HashSet<String> allowedMethods = new HashSet<>(
            Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

    private final String[] allowedUrls;

    public AlwaysMatchRequestMatcher(String... allowedUrls) {
        this.allowedUrls = allowedUrls;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String allowedUrl : allowedUrls) {
            if (uri.startsWith(allowedUrl)) {
                return false;
            }
        }
        return !this.allowedMethods.contains(request.getMethod());
    }


}