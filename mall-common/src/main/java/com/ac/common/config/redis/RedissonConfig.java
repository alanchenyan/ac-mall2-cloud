package com.ac.common.config.redis;

import com.ac.common.properties.RedissonRepository;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class RedissonConfig {

    @Resource
    private RedissonRepository redissonRepository;

    /**
     * Redisson单机配置
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient singleRedisson() {
        log.info("redisSonRepository={}", redissonRepository);
        Config config = new Config();
        config.setCodec(StringCodec.INSTANCE);
        config.setTransportMode(TransportMode.NIO);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setPassword(redissonRepository.getPassword());
        singleServerConfig.setAddress(redissonRepository.getSingle().getAddress());
        singleServerConfig.setDatabase(redissonRepository.getSingle().getDatabase());
        return Redisson.create(config);
    }
}
