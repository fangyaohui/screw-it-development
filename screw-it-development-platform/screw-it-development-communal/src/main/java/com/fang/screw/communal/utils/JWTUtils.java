package com.fang.screw.communal.utils;

import com.fang.screw.communal.constant.DynamicParameter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @FileName JWTUtils
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
public class JWTUtils {

    /**
    * @Description 生成Token
    * @param uuid
    * @param roleId 用户角色ID
    * @return {@link String }
    * @Author yaoHui
    * @Date 2024/7/18
    */
    public static String generateToken(String uuid,Long roleId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("uuid",uuid);
        claims.put("roleId",roleId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(uuid)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + DynamicParameter.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, DynamicParameter.JWT_SECURITY)
                .compact();

    }

    /**
    * @Description 读取Token中的UUID
    * @param token
    * @return {@link String }
    * @Author yaoHui
    * @Date 2024/7/18
    */
    public static String getUUID(String token){
        return getClaim(token,Claims::getSubject);
    }

    /**
    * @Description 读取Token中的RoleID
    * @param token
    * @return {@link Long }
    * @Author yaoHui
    * @Date 2024/7/18
    */
    public static Long getRoleId(String token){
        String roleId =  getClaim(token,claims -> claims.get("roleId").toString());
        return Long.parseLong(roleId);
    }

    /**
    * @Description 验证该Token是否有效
    * @param token
    * @return {@link boolean }
    * @Author yaoHui
    * @Date 2024/7/18
    */
    public static boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(DynamicParameter.JWT_SECURITY).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
    * @Description 读取Token中的参数
    * @param token
    * @param claimsResolver
    * @return {@link T }
    * @Author yaoHui
    * @Date 2024/7/18
    */
    private static <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(DynamicParameter.JWT_SECURITY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

}
