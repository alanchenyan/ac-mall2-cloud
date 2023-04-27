package com.ac.gateway.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Chen
 * @description 网关白名单配置
 * @date 2023/04/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@RefreshScope
@ConfigurationProperties(prefix = "mall.security.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
