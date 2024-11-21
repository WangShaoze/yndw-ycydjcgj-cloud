package com.yndw.dvp.oauth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_login_log")
public class OAuthLoginLog extends Model<OAuthLoginLog> {
    private static final long serialVersionUID = -3944592867844761186L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String bh;
    private String czybh;
    private String czydlzh;
    private String czymc;
    private String dllx;
    private String ipdz;
    private LocalDateTime dlsj;
}
