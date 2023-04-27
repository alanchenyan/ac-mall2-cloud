package com.ac.gateway.controller;

import com.ac.gateway.config.GateWaySwaggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
public class SwaggerController {

    @Resource
    private GateWaySwaggerConfig gateWaySwaggerConfig;

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;

    @Autowired(required = false)
    private UiConfiguration uiConfiguration;

    @GetMapping("/swagger-resources/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping("/swagger-resources/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping("/swagger-resources")
    public Mono<ResponseEntity> swaggerResources() {
        return Mono.just((new ResponseEntity<>(gateWaySwaggerConfig.get(), HttpStatus.OK)));
    }

    @GetMapping("/")
    public Mono<ResponseEntity> swaggerResourcesN() {
        return Mono.just((new ResponseEntity<>(gateWaySwaggerConfig.get(), HttpStatus.OK)));
    }

    @GetMapping("/csrf")
    public Mono<ResponseEntity> swaggerResourcesCsrf() {
        return Mono.just((new ResponseEntity<>(gateWaySwaggerConfig.get(), HttpStatus.OK)));
    }
}