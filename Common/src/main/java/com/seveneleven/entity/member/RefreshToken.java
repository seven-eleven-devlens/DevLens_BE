package com.seveneleven.entity.member;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Indexed;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken {

    @Id
    private String refreshToken;

    private String userId;

    @TimeToLive
    private Long ttl;

    @Builder
    public RefreshToken(String refreshToken, String userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.ttl = 1000L * 60 * 60 * 24 * 14;
    }

}
