package com.ac.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "Redis测试")
@RestController
@RequestMapping("redis")
public class RedisTestController {

    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "存-取-字符串")
    @GetMapping("testString")
    public String testString(@RequestParam String value) {
        String key = "memberName";
        redisTemplate.opsForValue().set(key, value);
        Object result = redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result.toString();
        }
        return "";
    }
}
