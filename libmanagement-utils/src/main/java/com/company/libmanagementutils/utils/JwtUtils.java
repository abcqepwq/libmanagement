package com.company.libmanagementutils.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtUtils {
    private static String SIGNATURE = "token!@#$%^7890";

    private static String TOKEN_KEY_1 = "userId";
    private static String TOKEN_KEY_2 = "role";

    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);
    public static String getToken(Integer userId, String roleLabel){
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId.toString());
        map.put("role", roleLabel);
        return getToken(map);
    }

    /**
     * generate token.
     * @param map
     * @return return token.
     */
    public static String getToken(Map<String, String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,3600*24);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SIGNATURE)).toString();
    }

    /**
     * verify token
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * get payload in the token.
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    public static Integer getUserIdInToken(String token){
        Integer result = null;
        try{
            DecodedJWT decode = JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
            result = Integer.parseInt(decode.getClaim(TOKEN_KEY_1).asString());
        }catch (Exception ex){
            //token is invalidate, no need log.
        }
        return result;
    }

    public static String getRoleIdInToken(String token){
        DecodedJWT decode = JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
        return decode.getClaim(TOKEN_KEY_2).asString();
    }
}

