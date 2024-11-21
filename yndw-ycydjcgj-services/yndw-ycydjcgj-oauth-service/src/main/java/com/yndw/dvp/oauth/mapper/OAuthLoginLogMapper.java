package com.yndw.dvp.oauth.mapper;

import com.yndw.dvp.common.db.mapper.SuperMapper;
import com.yndw.dvp.oauth.model.OAuthLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * Create By Carlos
 * 2020/7/9
 */
@Mapper
public interface OAuthLoginLogMapper extends SuperMapper<OAuthLoginLog> {
}
