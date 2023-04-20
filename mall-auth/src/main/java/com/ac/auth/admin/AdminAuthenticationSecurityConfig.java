package com.ac.auth.admin;

import com.ac.auth.provider.AdminAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * admin登录的相关处理配置
 *
 * @author AlanChen
 */
@Component
public class AdminAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Resource
    private AdminAuthenticationProvider adminAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) {
        http.authenticationProvider(adminAuthenticationProvider);
    }
}
