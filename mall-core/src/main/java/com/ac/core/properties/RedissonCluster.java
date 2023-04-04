package com.ac.core.properties;

import lombok.Data;

import java.util.List;

@Data
public class RedissonCluster {

    private List<String> nodeAddresses;
}
