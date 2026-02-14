package com.content.framework.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * 提供JWT令牌的生成、解析、验证等功能
 */
@Component
public class JwtUtils {

    /**
     * JWT密钥
     */
    @Value("${jwt.secret:abcdefghijklmnopqrstuvwxyz1234567890}")
    private String secret;

    /**
     * JWT过期时间（秒）
     */
    @Value("${jwt.expiration:7200}")
    private Long expiration;

    /**
     * 获取密钥
     * @return 密钥
     */
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成JWT令牌
     * @param username 用户名
     * @param userId 用户ID
     * @return JWT令牌
     */
    public String generateToken(String username, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        return generateToken(claims, username);
    }

    /**
     * 生成JWT令牌
     * @param claims 自定义声明
     * @param subject 主题
     * @return JWT令牌
     */
    private String generateToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 从令牌中获取声明
     * @param token JWT令牌
     * @return 声明
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从令牌中获取用户名
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * 从令牌中获取用户ID
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 从令牌中获取过期时间
     * @param token JWT令牌
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 获取令牌过期时间戳（秒）
     * @param token JWT令牌
     * @return 过期时间戳（秒）
     */
    public long getExpireTime(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.getTime() / 1000;
    }

    /**
     * 检查令牌是否过期
     * @param token JWT令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 验证令牌
     * @param token JWT令牌
     * @param username 用户名
     * @return 是否有效
     */
    public Boolean validateToken(String token, String username) {
        String tokenUsername = getUsernameFromToken(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }
}
