package com.ac.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "授权")
@RestController
@RequestMapping("auth")
public class AuthController {

    @ApiOperation(value = "hello")
    @GetMapping("hello")
    public String hello() {
        return "Hello";
    }
}
