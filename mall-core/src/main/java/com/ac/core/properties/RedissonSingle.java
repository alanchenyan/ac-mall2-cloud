package com.ac.core.properties;

import lombok.Data;

@Data
public class RedissonSingle {

    private String address;

    private int database;
}
