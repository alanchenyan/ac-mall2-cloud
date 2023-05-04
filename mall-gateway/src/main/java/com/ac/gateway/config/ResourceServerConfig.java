package com.ac.gateway.config;

import cn.hutool.core.util.ArrayUtil;
import com.ac.gateway.authentication.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;

import javax.annotation.Resource;

@EnableWebFluxSecurity
@Slf4j
@Configuration
public class ResourceServerConfig {

    @Resource
    private TokenStore tokenStore;

    @Resource
    private PermissionAuthManager permissionAuthManager;

    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        log.info("ignoreUrlsConfig={}", ignoreUrlsConfig.getUrls());

        //认证处理器
        ReactiveAuthenticationManager customAuthenticationManager = new CustomAuthenticationManager(tokenStore);

        //token转换器
        ServerBearerTokenAuthenticationConverter tokenAuthenticationConverter = new ServerBearerTokenAuthenticationConverter();
        tokenAuthenticationConverter.setAllowUriQueryParameter(true);

        //oauth2认证过滤器
        AuthenticationWebFilter oauth2Filter = new AuthenticationWebFilter(customAuthenticationManager);
        oauth2Filter.setServerAuthenticationConverter(tokenAuthenticationConverter);
        oauth2Filter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(restAuthenticationEntryPoint));
        oauth2Filter.setAuthenticationSuccessHandler(new Oauth2AuthSuccessHandler());
        http.addFilterAt(oauth2Filter, SecurityWebFiltersOrder.AUTHENTICATION);

        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchange = http.authorizeExchange();

        if (ignoreUrlsConfig.getUrls().size() > 0) {
            authorizeExchange.pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class)).permitAll();
        }
        authorizeExchange
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/").permitAll()
                .pathMatchers(HttpMethod.POST, "/oauth/**").permitAll()
                // swagger
                .pathMatchers("/swagger-ui.html").permitAll()
                .pathMatchers("/swagger-resources/**").permitAll()
                .pathMatchers("/images/**").permitAll()
                .pathMatchers("/webjars/**").permitAll()
                .pathMatchers("/**/v2/api-docs").permitAll()
                .anyExchange()
                .access(permissionAuthManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .httpBasic().disable()
                .csrf().disable();
        return http.build();
    }
}
