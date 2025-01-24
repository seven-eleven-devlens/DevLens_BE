package com.seveneleven.util.security.repository;

import com.seveneleven.entity.member.RefreshToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RefreshTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public RefreshTokenRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String findRefreshTokenByRefreshToken(String refreshToken) {
        return (String) redisTemplate.opsForValue().get(refreshToken);
    }

    public void save(RefreshToken refreshToken, long expirationTime) {
        redisTemplate.opsForValue().set(
                refreshToken.getUserId(),
                refreshToken.getRefreshToken(),
                expirationTime,
                TimeUnit.MILLISECONDS
                        );
    }

    public String findByUsername(String userId) {
        return (String) redisTemplate.opsForValue().get(userId);
    }

    public void delete(String username) {
        redisTemplate.delete(username);
    }
}
