package com.yndw.dvp.oauth.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yndw.dvp.common.core.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_details")
public class Client extends SuperEntity {
   private static final long serialVersionUID = -8185413579135897885L;
   private String clientId;
   /**
    * 应用名称
    */
   private String clientName;
   private String resourceIds = "";
   private String clientSecret;
   private String clientSecretStr;
   private String scope = "all";
   private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
   private String webServerRedirectUri;
   private String authorities = "";
   @TableField(value = "access_token_validity")
   private Integer accessTokenValiditySeconds = 18000;
   @TableField(value = "refresh_token_validity")
   private Integer refreshTokenValiditySeconds = 28800;
   private String additionalInformation = "{}";
   private String autoapprove = "true";
}
