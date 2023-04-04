package com.ac.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis.redisson")
@ConditionalOnProperty("spring.redis.redisson.password")
@Data
public class RedissonRepository {

    private String password;

    private RedissonCluster cluster;

    private RedissonSingle single;
}
