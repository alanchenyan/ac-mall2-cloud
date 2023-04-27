package com.ac.core.redis.properties;

import lombok.Data;

@Data
public class RedissonSingle {

    private String address;

    private int database;
}
