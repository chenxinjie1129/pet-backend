package com.petshome.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类，用于生成和验证JWT令牌
 */
@Component
@Slf4j
public class JwtTokenUtil {

    @Value("${petshome.jwt.secret}")
    private String secret;

    @Value("${petshome.jwt.expiration}")
    private Long expiration;

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        log.info("从令牌中获取用户名: {}", token);
        try {
            String username = getClaimFromToken(token, Claims::getSubject);
            log.info("获取到的用户名: {}", username);
            return username;
        } catch (Exception e) {
            log.error("从令牌中获取用户名失败", e);
            throw e;
        }
    }

    /**
     * 从令牌中获取过期时间
     *
     * @param token 令牌
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从令牌中获取声明
     *
     * @param token          令牌
     * @param claimsResolver 声明解析器
     * @return 声明
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从令牌中获取所有声明
     *
     * @param token 令牌
     * @return 所有声明
     */
    private Claims getAllClaimsFromToken(String token) {
        log.info("从令牌中获取所有声明: {}", token);
        log.info("使用的密钥: {}", secret);
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            log.info("获取到的声明: {}", claims);
            return claims;
        } catch (Exception e) {
            log.error("从令牌中获取所有声明失败", e);
            throw e;
        }
    }

    /**
     * 检查令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    private Boolean isTokenExpired(String token) {
        log.info("检查令牌是否过期: {}", token);
        try {
            final Date expiration = getExpirationDateFromToken(token);
            Date now = new Date();
            log.info("令牌过期时间: {}, 当前时间: {}", expiration, now);
            boolean isExpired = expiration.before(now);
            log.info("令牌是否过期: {}", isExpired);
            return isExpired;
        } catch (Exception e) {
            log.error("检查令牌是否过期时发生错误", e);
            return true;
        }
    }

    /**
     * 生成令牌
     *
     * @param userDetails 用户详情
     * @return 令牌
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * 生成令牌
     *
     * @param claims  声明
     * @param subject 主题
     * @return 令牌
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户详情
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        log.info("验证令牌: {}", token);
        log.info("用户详情: {}", userDetails);
        try {
            final String username = getUsernameFromToken(token);
            log.info("令牌中的用户名: {}, 用户详情中的用户名: {}", username, userDetails.getUsername());

            boolean usernameEquals = username.equals(userDetails.getUsername());
            log.info("用户名匹配: {}", usernameEquals);

            boolean tokenNotExpired = !isTokenExpired(token);
            log.info("令牌未过期: {}", tokenNotExpired);

            boolean isValid = usernameEquals && tokenNotExpired;
            log.info("令牌验证结果: {}", isValid);

            return isValid;
        } catch (Exception e) {
            log.error("验证令牌时发生错误", e);
            return false;
        }
    }
}
