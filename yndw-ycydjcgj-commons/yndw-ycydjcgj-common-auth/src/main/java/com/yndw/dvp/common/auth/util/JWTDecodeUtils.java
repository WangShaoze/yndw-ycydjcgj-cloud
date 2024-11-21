package com.yndw.dvp.common.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.ArrayList;
import java.util.List;

public class JWTDecodeUtils {

    /**
     * JWT 解析工具
     * @param token
     * @param pasing
     * @return String
     * */
    public static String decodeJWT(String token,String pasing) {
        String val = null;
        try {
            // 解析token
            DecodedJWT jwt = JWT.decode(token);
            // 获取并返回需要取出的值
            val = jwt.getClaim(pasing).asString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return val;
    }

    /**
     * JWT 解析工具
     * @param token
     * @param pasing
     * @return list[]
     * */
    public static List<String> decodeWTList(String token, String pasing){
        List<String> val = new ArrayList<>();
        try {
            // 解析token
            DecodedJWT jwt = JWT.decode(token);
            // 获取并返回需要取出的值
            val = jwt.getClaim(pasing).asList(String.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return val;
    }

}
