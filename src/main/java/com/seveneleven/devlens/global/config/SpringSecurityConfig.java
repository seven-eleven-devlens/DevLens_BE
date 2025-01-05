package com.seveneleven.devlens.global.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers(
                                "/status",
                                "/images/**",
                                "/view/login",
                                "/view/login-process",
                                "/swagger-ui/**",           // Swagger UI 허용
                                "/v3/api-docs/**",          // OpenAPI 문서 허용
                                "/swagger-resources/**",    // Swagger 리소스 허용
                                "/webjars/**"               // Swagger WebJars 허용
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/view/login")	// [A] 커스텀 로그인 페이지 지정
                        .loginProcessingUrl("/view/login-process")	// [B] submit 받을 url
                        .usernameParameter("userid")	// [C] submit할 아이디
                        .passwordParameter("pw")	// [D] submit할 비밀번호
                        .defaultSuccessUrl("/view/dashboard", true)
                        .permitAll()
                )
                .logout(withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/swagger-ui/**",       // Swagger 관련 경로에서 CSRF 비활성화
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/view/**"
                        )
                );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // 모든 Origin 허용
        configuration.addAllowedMethod("*");       // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*");       // 모든 헤더 허용
        configuration.setAllowCredentials(true);   // 자격 증명 허용 (예: 쿠키)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 적용
        return source;
    }

}
