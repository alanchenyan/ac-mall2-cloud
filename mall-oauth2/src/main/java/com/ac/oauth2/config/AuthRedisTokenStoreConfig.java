package com.ac.oauth2.config;

import com.ac.oauth2.stroe.CustomRedisTokenStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class AuthRedisTokenStoreConfig {

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory, RedisSerializer<Object> redisValueSerializer) {
        return new CustomRedisTokenStore(redisConnectionFactory, redisValueSerializer);
    }
}
