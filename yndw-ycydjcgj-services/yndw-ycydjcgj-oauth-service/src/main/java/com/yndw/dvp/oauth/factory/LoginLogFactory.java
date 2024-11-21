package com.yndw.dvp.oauth.factory;

import cn.hutool.core.bean.BeanUtil;
import com.yndw.dvp.common.core.model.SecurityUser;
import com.yndw.dvp.common.core.utils.AddrUtil;
import com.yndw.dvp.oauth.dto.OAuthLogExcelDto;
import com.yndw.dvp.oauth.model.OAuthLoginLog;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Carlos
 * 2020/7/9
 */
public class LoginLogFactory {
    public static OAuthLoginLog createLogin(SecurityUser user, String ip){
        OAuthLoginLog log = new OAuthLoginLog();
        log.setCzybh(user.getBh());
        log.setCzydlzh(user.getDlzh());
        log.setDllx("登入");
        log.setIpdz(ip);
        log.setDlsj(LocalDateTime.now());
        return log;
    }
    public static OAuthLoginLog createLogin(Authentication authentication, HttpServletRequest request){
        OAuthLoginLog log = new OAuthLoginLog();
        SecurityUser user = (SecurityUser)authentication.getPrincipal();
        log.setCzybh(user.getBh());
        log.setCzydlzh(user.getDlzh());
        log.setDllx("登入");
        log.setIpdz(AddrUtil.getRemoteAddr(request));
        log.setDlsj(LocalDateTime.now());
        return log;
    }
    public static OAuthLoginLog createLogout(OAuth2Authentication authentication, HttpServletRequest request){
        OAuthLoginLog log = new OAuthLoginLog();
        SecurityUser user = (SecurityUser)authentication.getPrincipal();
        log.setCzybh(user.getBh());
        log.setCzydlzh(user.getDlzh());
        log.setIpdz(AddrUtil.getRemoteAddr(request));
        log.setDllx("登出");
        log.setDlsj(LocalDateTime.now());
        return log;
    }

    public static List<OAuthLogExcelDto> buildExcelDto(List<OAuthLoginLog> entityList) {
        List<OAuthLogExcelDto> dtolist = new ArrayList<>();
        for (OAuthLoginLog entity : entityList) {
            dtolist.add(buildExcelDto(entity));
        }
        return dtolist;
    }

    public static OAuthLogExcelDto buildExcelDto(OAuthLoginLog entity) {
        OAuthLogExcelDto dto = new OAuthLogExcelDto();
        BeanUtil.copyProperties(entity, dto);
        return dto;
    }
}
