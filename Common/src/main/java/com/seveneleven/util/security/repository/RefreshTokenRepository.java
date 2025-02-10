package com.seveneleven.util.security.repository;

import com.seveneleven.entity.member.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;

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

    public String findByUsername(Long userId) {
        return (String) redisTemplate.opsForValue().get(userId.toString());
    }

    public void delete(Long memberId) {
        redisTemplate.delete(memberId.toString());
    }

    public void setBlackList(String key, Object o, Long milliSeconds) {
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        redisBlackListTemplate.opsForValue().set(key, o, milliSeconds, TimeUnit.MILLISECONDS);
    }

    public boolean hasKeyBlackList(String key) {
        return redisBlackListTemplate.hasKey(key);
    }
}
