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

    @ApiOperation(value = "删除key")
    @GetMapping("del")
    public Boolean del(@RequestParam String key) {
        redisComponent.del(key);
        return true;
    }

    @ApiOperation(value = "存-取-字符串")
    @GetMapping("testString")
    public String testString(@RequestParam String key, String value) {
        redisComponent.set(key, value);
        return redisComponent.getStr(key);
    }

    @ApiOperation(value = "存-取-字符串（只有在key不存在时设置key的值）")
    @GetMapping("testStringIfAbsent")
    public String testStringIfAbsent(@RequestParam String key, String value) {
        redisComponent.setIfAbsent(key, value);
        return redisComponent.getStr(key);
    }

    @ApiOperation(value = "存-取-Long")
    @GetMapping("testLong")
    public Long testLong(@RequestParam String key, Long value) {
        redisComponent.set(key, value);
        Object obj = redisComponent.get(key);
        return Long.valueOf(obj.toString());
    }

    @ApiOperation(value = "Long-自增")
    @GetMapping("testIncr")
    public Long testIncr(@RequestParam String key) {
        redisComponent.incr(key);
        Object obj = redisComponent.get(key);
        return Long.valueOf(obj.toString());
    }
}
