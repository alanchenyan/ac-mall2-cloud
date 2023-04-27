package com.ac.core.redis.properties;

import lombok.Data;

import java.util.List;

@Data
public class RedissonCluster {

    private List<String> nodeAddresses;
}
