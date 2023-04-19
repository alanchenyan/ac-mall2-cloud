package com.ac.auth.config;

import com.ac.auth.provider.*;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource
    private MobilePasswordAuthenticationProvider mobilePasswordAuthenticationProvider;

    @Resource
    private MobileSmsAuthenticationProvider mobileSmsAuthenticationProvider;

    @Resource
    private VisitorAuthenticationProvider visitorAuthenticationProvider;

    @Resource
    private QRCodeAuthenticationProvider qrCodeAuthenticationProvider;

    @Resource
    private SocialAuthenticationProvider socialAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) {
        http.authenticationProvider(mobilePasswordAuthenticationProvider);

        http.authenticationProvider(mobileSmsAuthenticationProvider);

        http.authenticationProvider(visitorAuthenticationProvider);

        http.authenticationProvider(qrCodeAuthenticationProvider);

        http.authenticationProvider(socialAuthenticationProvider);
    }
}
