package com.seveneleven.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@Slf4j
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class RedisConfig {

    @Value("${SPRING_REDIS_HOST}")
    private String host;
    @Value("${SPRING_REDIS_PORT}")
    private int port;


    /**
     * 함수명 : redisConnectionFactory
     * Redis 연결을 위한 RedisConnectionFactory를 Bean으로 등록합니다.
     *
     * Lettuce 클라이언트 설정을 통해 연결 타임아웃과 종료 타임아웃을 지정합니다.
     *
     * @return RedisConnectionFactory 객체
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);

        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder()
                        .commandTimeout(Duration.ofSeconds(1)) //No longer than 1 second
                        .shutdownTimeout(Duration.ZERO) //Immediately shutdown after application shutdown
                        .build();

        log.info("Connected to Redis at {}:{}", host, port);
        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    /**
     * RedisTemplate을 Bean으로 등록합니다.
     *
     * <p>키는 문자열로, 값은 JSON 포맷으로 직렬화하여 Redis에 저장할 수 있습니다.</p>
     *
     * @return RedisTemplate 객체
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // 키와 값을 직렬화 설정
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }
}
