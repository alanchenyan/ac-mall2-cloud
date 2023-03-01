package com.ac.member.controller;

import com.ac.common.util.redis.RedisComponent;
import com.ac.member.dto.MemberDTO;
import com.ac.member.enums.MemberSexEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Api(tags = "Redis测试")
@RestController
@RequestMapping("redis")
public class RedisTestController {

    @Resource
    private RedisComponent redisComponent;

    //============================第1部分：common start=============================
    @ApiOperation(value = "删除key")
    @GetMapping("del")
    public Boolean del(@RequestParam String key) {
        redisComponent.del(key);
        return true;
    }

    @ApiOperation(value = "是否有key")
    @GetMapping("hasKey")
    public Boolean hasKey(@RequestParam String key) {
        return redisComponent.hasKey(key);
    }

    @ApiOperation(value = "设置过期时间")
    @GetMapping("expire")
    public Boolean expire(@RequestParam String key, long time) {
        return redisComponent.expire(key, time);
    }

    @ApiOperation(value = "获取过期时间")
    @GetMapping("getExpire")
    public Long getExpire(@RequestParam String key) {
        return redisComponent.getExpire(key);
    }


    //============================第2部分：String start=============================
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

    //================================第3部分：Hash start=================================
    @ApiOperation(value = "Hash-对象存储")
    @GetMapping("hmsetObj")
    public MemberDTO hmsetObj(@RequestParam String key) {
        MemberDTO member = new MemberDTO();
        member.setId(1L);
        member.setMemberName("AC");
        member.setBirthday(LocalDate.now());
        member.setMobile("134178191xx");
        member.setSex(MemberSexEnum.MEN);

        //存
        redisComponent.hmSetObj(key, member);
        redisComponent.hmSetObj(key, member,10);
        redisComponent.hmSetObj(key, member,10, TimeUnit.MINUTES);

        //取
        return redisComponent.hmGetObj(key, MemberDTO.class);
    }
}
