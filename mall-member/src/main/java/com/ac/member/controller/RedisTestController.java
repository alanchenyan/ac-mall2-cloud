package com.ac.member.controller;

import com.ac.common.util.redis.RedisComponent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "Redis测试")
@RestController
@RequestMapping("redis")
public class RedisTestController {

    @Resource
    private RedisComponent redisComponent;

    @ApiOperation(value = "存-取-字符串")
    @GetMapping("testString")
    public String testString(@RequestParam String key, String value) {
        redisComponent.set(key, value);
        return redisComponent.getStr(key);
    }
}
