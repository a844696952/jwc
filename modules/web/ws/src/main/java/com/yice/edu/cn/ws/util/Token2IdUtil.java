package com.yice.edu.cn.ws.util;

import com.yice.edu.cn.common.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;

public class Token2IdUtil {

    public static String token2Id(String token){
        Claims claims = JwtUtil.parseJWT(token);
        if(claims==null){
            return null;
        }
        return claims.getId();
    }
}
