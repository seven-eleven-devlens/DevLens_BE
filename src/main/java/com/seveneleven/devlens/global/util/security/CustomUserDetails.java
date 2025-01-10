package com.seveneleven.devlens.global.util.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
    // 사용자 ID(PK)를 반환하는 메서드
    /**
     * 추후에 삭제 예정
     * - 임시로 생성한 CustomUserDetails
     */

    @Getter
    private Long userId; // 사용자 ID(PK)
    private Collection<? extends GrantedAuthority> authorities; // 사용자 권한

    // 생성자
    public CustomUserDetails(Long userId,
                             Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }

    // UserDetails 인터페이스의 메서드 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
