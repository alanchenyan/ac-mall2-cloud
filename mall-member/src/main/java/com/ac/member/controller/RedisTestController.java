package com.ac.member.controller;

import com.ac.common.util.RedisComponent;
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
    private RedisComponent redisComponent;

    @ApiOperation(value = "存-取-字符串")
    @GetMapping("testString")
    public String testString2(@RequestParam String value) {
        String key = "memberName";
        redisComponent.set(key,value,0);
        Object result = redisComponent.get(key);
        if (result != null) {
            return result.toString();
        }
        return "";
    }
}
