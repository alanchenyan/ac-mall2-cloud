package com.ac.common.properties;

import lombok.Data;

import java.util.List;

@Data
public class RedissonCluster {

    private List<String> nodeAddresses;
}
