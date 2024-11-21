package com.yndw.dvp.oauth.csrf.strategy;

import com.yndw.dvp.common.core.constant.GrantType;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Create By Carlos
 * 2020/12/18
 */
public class PwdImgCodeCsrfStrategy extends AbstractCsrfStrategy {
    private static final String FORM_GRANT_TYPE = "grant_type";

    @Override
    public GrantType getGrantType() {
        return GrantType.PASSWORD_CODE;
    }

    @Override
    public boolean supports(Map<String, ?> parameters) {
        return StringUtils.equals(MapUtils.getString(parameters, FORM_GRANT_TYPE), getGrantType().getCode());
    }
}
