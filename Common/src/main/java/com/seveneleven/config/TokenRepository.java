package com.seveneleven.config;

import com.seveneleven.entity.member.Token;
import com.seveneleven.entity.member.constant.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    boolean existsByTokenAndStatus(String token, TokenStatus status);
}
