package com.ac.common.properties;

import lombok.Data;

@Data
public class RedissonSingle {

    private String address;

    private int database;
}
