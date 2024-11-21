package com.yndw.dvp.oauth.granter;

import com.yndw.dvp.common.core.context.OAuth2AuthenticationHolder;
import com.yndw.dvp.common.core.context.SecurityDlzhContextHolder;
import com.yndw.dvp.common.core.exception.BusinessException;
import com.yndw.dvp.common.core.utils.RsaUtils;
import com.yndw.dvp.oauth.service.IValidateCodeService;
import org.springframework.security.authentication.*;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * password添加图像验证码授权模式
 *
 * @author zlt
 * @date 2020/7/11
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
public class PwdImgCodeGranter extends ResourceOwnerPasswordTokenGranter {
    private static final String GRANT_TYPE = "password_code";

    private final IValidateCodeService validateCodeService;
    private String rsaPrivateKey;

    public PwdImgCodeGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, IValidateCodeService validateCodeService
            ,String rsaPrivateKey) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.validateCodeService = validateCodeService;
        this.rsaPrivateKey = rsaPrivateKey;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String deviceId = parameters.get("deviceId");
        String validCode = parameters.get("validCode");
        //校验图形验证码
        validateCodeService.validate(deviceId, validCode);

        String username = parameters.get("username");
        String password = parameters.get("password");

        try{
            //加密 解密逻辑处理
            username = RsaUtils.decryptByPrivateKey(rsaPrivateKey,username);
            password = RsaUtils.decryptByPrivateKey(rsaPrivateKey,password);

        }catch (Exception e){
            throw new BusinessException("账户密码解密失败："+e.getMessage());
        }

        // 替换解密后的值
        parameters.put("username",username);
        parameters.put("password",password);

        SecurityDlzhContextHolder.setDlzh(username);
        tokenRequest.setRequestParameters(parameters);

        final OAuth2Authentication oAuth2Authentication = super.getOAuth2Authentication(client, tokenRequest);
        OAuth2AuthenticationHolder.set(oAuth2Authentication);
        return oAuth2Authentication;
    }
}
