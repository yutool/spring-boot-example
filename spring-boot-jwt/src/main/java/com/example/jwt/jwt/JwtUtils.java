package com.example.jwt.jwt;

import com.example.jwt.entity.User;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtils {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final Long EXPIRATION = 60*60*24*3L;
    private static final Long REFRESH_EXPIRATION = 60*60*24*7L;

    /**
     * 生成token
     * @param user 用户
     * @return 返回一个集合包括token和refreshToken
     */
    public static Map<String, Object> generateToken(User user) {
        Map<String, Object> tokens = new HashMap<>();
        JwtBuilder builder = getBaseJwtBuilder(user);
        String token = builder.setExpiration(generateExpirationDate(EXPIRATION)).compact();
        String refreshToken = builder.setExpiration(generateExpirationDate(REFRESH_EXPIRATION)).compact();
        tokens.put("token", token);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    /**
     * 设置前简单判断下是否把refreshToken也刷新了
     * 主要看自己怎么设计的
     * @param token refreshToken
     */
    public static Map<String, Object> refreshToken(String token) {
        Map<String, Object> tokens = new HashMap<>();
        final Claims claims = getClaimsFromToken(token);
        long exp = claims.getExpiration().getTime();
        JwtBuilder builder = getBaseJwtBuilder(claims);
        tokens.put("token", builder.setExpiration(generateExpirationDate(EXPIRATION)).compact());
        if(exp - System.currentTimeMillis() < EXPIRATION * 1000) {
            tokens.put("refreshToken", builder.setExpiration(generateExpirationDate(REFRESH_EXPIRATION)).compact());
        } else {
            tokens.put("refreshToken", token);
        }
        return tokens;
    }

    /**
     * 验证token
     * @return true：表示验证通过
     */
    public static Boolean validateToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        if(claims == null) return false;
        // 这里就简单验证下
        final String userId = claims.get("userId").toString();
        final String username = claims.getSubject();
        if (username != null && userId != null) {
            return true;
        }
        return false;
    }

    // 从token中获取Claims
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    // 获取JwtBuilder，注意还未设置过期时间
    private static JwtBuilder getBaseJwtBuilder(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .compressWith(CompressionCodecs.DEFLATE)            // 压缩，可选GZIP
                .signWith(SignatureAlgorithm.HS256, generalKey());  // 加密
    }
    private static JwtBuilder getBaseJwtBuilder(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(claims.getSubject())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .compressWith(CompressionCodecs.DEFLATE)            // 压缩，可选GZIP
                .signWith(SignatureAlgorithm.HS256, generalKey());  // 加密
    }

    // expiration 单位秒
    private static Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    // 生成key
    private static SecretKey generalKey(){
        String stringKey = "7786df7fc3a34e26a61c034d5ec8245d";
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}
