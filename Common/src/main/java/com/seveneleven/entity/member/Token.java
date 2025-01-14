package com.seveneleven.entity.member;

import com.seveneleven.entity.member.constant.TokenStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenStatus status;

    // 생성 메서드
    public static Token create(String token, LocalDateTime expiresAt) {
        Token newToken = new Token();
        newToken.token = token;
        newToken.createdAt = LocalDateTime.now();
        newToken.expiresAt = expiresAt;
        newToken.status = TokenStatus.ACTIVE;
        return newToken;
    }

    public void setStatus() {
        this.status = TokenStatus.BLACKLISTED;
    }
}
