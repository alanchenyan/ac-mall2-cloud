package com.ac.member.controller;

import com.ac.common.util.redis.RedisComponent;
import com.ac.member.dto.MemberDTO;
import com.ac.member.enums.MemberSexEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
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
    public String testString() {
        String key = "userName";
        String value = "AlanChen";

        redisComponent.set(key, value);
        return redisComponent.getStr(key);
    }

    @ApiOperation(value = "存-取-字符串（只有在key不存在时设置key的值）")
    @GetMapping("testStringIfAbsent")
    public String testStringIfAbsent() {
        String key = "userName";
        String value = "AlanChen";

        redisComponent.setIfAbsent(key, value);
        return redisComponent.getStr(key);
    }

    @ApiOperation(value = "存-取-Long")
    @GetMapping("testLong")
    public Long testLong() {
        String key = "likeNum";
        Long value = 1L;

        redisComponent.set(key, value);
        Object obj = redisComponent.get(key);
        return Long.valueOf(obj.toString());
    }

    @ApiOperation(value = "Long-自增")
    @GetMapping("testIncr")
    public Long testIncr() {
        String key = "likeNum";

        redisComponent.incr(key);
        Object obj = redisComponent.get(key);
        return Long.valueOf(obj.toString());
    }

    //================================第3部分：Hash start=================================
    @ApiOperation(value = "Hash-对象存储")
    @GetMapping("hmGetObj")
    public MemberDTO hmGetObj() {
        String key = "member";

        MemberDTO member = new MemberDTO();
        member.setId(1L);
        member.setMemberName("AC");
        member.setBirthday(LocalDate.now());
        member.setMobile("134178191xx");
        member.setSex(MemberSexEnum.MEN);

        //存
        redisComponent.hmSetObj(key, member);
        redisComponent.hmSetObj(key, member, 10);
        redisComponent.hmSetObj(key, member, 10, TimeUnit.MINUTES);

        //取
        return redisComponent.hmGetObj(key, MemberDTO.class);
    }

    @ApiOperation(value = "Hash-取对象字段")
    @GetMapping("hGet")
    public Object hGet() {
        String key = "member";
        String item = "memberName";

        Object obj = redisComponent.hGet(key, item);
        return obj;
    }

    @ApiOperation(value = "Hash-设置对象字段")
    @GetMapping("hSet")
    public Object hSet() {
        String key = "member";
        String item = "memberName";
        Object value = "AC";

        redisComponent.hSet(key, item, value);
        return redisComponent.hGet(key, item);
    }

    @ApiOperation(value = "Hash-删除对象字段")
    @GetMapping("hDel")
    public MemberDTO hDel() {
        String key = "member";
        String item = "memberName";

        //删除字段
        redisComponent.hDel(key, item);
        //取
        return redisComponent.hmGetObj(key, MemberDTO.class);
    }

    @ApiOperation(value = "Hash-判断对象字段是否存在")
    @GetMapping("hHasKey")
    public boolean hHasKey() {
        String key = "member";
        String item = "memberName";

        return redisComponent.hHasKey(key, item);
    }

    @ApiOperation(value = "Hash-获取对象指定字段长度")
    @GetMapping("hLen")
    public Long hLen() {
        String key = "member";
        String item = "mobile";

        return redisComponent.hLen(key, item);
    }

    @ApiOperation(value = "Hash-对象字段递增")
    @GetMapping("hIncr")
    public MemberDTO hIncr() {
        String key = "member";
        String item = "id";

        redisComponent.hIncr(key, item, 2);
        return redisComponent.hmGetObj(key, MemberDTO.class);

    }

    @ApiOperation(value = "Hash-对象字段递减")
    @GetMapping("hDecr")
    public MemberDTO hDecr() {
        String key = "member";
        String item = "id";

        redisComponent.hDecr(key, item, 1);
        return redisComponent.hmGetObj(key, MemberDTO.class);
    }

    @ApiOperation(value = "Hash-Map存储")
    @GetMapping("hmSet")
    public Map<Object, Object> hmSet() {
        String key = "loginMap";

        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("name", "AC");
        loginMap.put("pwd", "123abc");

        //存
        redisComponent.hmSet(key, loginMap);
        redisComponent.hmSet(key, loginMap, 10);
        redisComponent.hmSet(key, loginMap, 10, TimeUnit.MINUTES);

        //取
        return redisComponent.hmGet(key);
    }

    //================================第3部分：Hash end=================================

    //================================第4部分：List start=================================

    @ApiOperation(value = "List-存取数据")
    @GetMapping("list")
    public boolean sSet() {
        String key = "list";
        redisComponent.rightPushAll(key, Arrays.asList("B", "C", "D", "E", "F", "G"));

        //BCDEFG
        System.out.println("list...................");
        redisComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //ABCDEFG
        System.out.println("从左边压入元素...................");
        redisComponent.lLeftPush(key, "A");
        redisComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //ABCDEFGH
        System.out.println("从右边压入元素...................");
        redisComponent.lRightPush(key, "H");
        redisComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //ABC
        System.out.println("取前三个list...................");
        redisComponent.lGet(key, 0, 2).stream().forEach(System.out::print);
        System.out.println();

        //HG
        System.out.println("从右边(倒着取)开始取2个-右边弹出，左边压入...................");
        List<Object> list2 = redisComponent.rightPopAndLeftPush(key, key, 2);
        list2.stream().forEach(System.out::print);
        System.out.println();

        //GHABCDEF
        System.out.println("显示全部list...................");
        redisComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //GH
        System.out.println("弹出多个元素list...................");
        redisComponent.lLeftMultiPop(key, 2).stream().forEach(System.out::print);
        System.out.println();

        //A
        System.out.println("从左边弹出一个元素...................");
        System.out.println(redisComponent.lLeftPop(key));

        //BCDEF
        System.out.println("显示全部list...................");
        redisComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //5
        System.out.println("获取List长度...................");
        System.out.println(redisComponent.lGetListSize(key));

        //C
        System.out.println("获取指定下标的元素...................");
        System.out.println(redisComponent.lGetIndex(key, 1));

        //bCDEF
        System.out.println("根据索引修改list中的某条数据...................");
        redisComponent.lUpdateIndex(key, 0, "b");
        redisComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //bDEF
        System.out.println("移除N个值为value的元素...................");
        redisComponent.lRemove(key, 1, "C");
        redisComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        return true;
    }

    //================================第5部分：List start=================================
    @ApiOperation(value = "Set-存取数据")
    @GetMapping("sSetBak")
    public boolean sSetBak() {
        String key = "fansList";
        redisComponent.sSet(key, "A", "B", "C", "D", "E");
        Long size = redisComponent.sSize(key);
        System.out.println("size:" + size);

        System.out.println("fansList...................");
        redisComponent.sGet(key).stream().forEach(System.out::print);
        System.out.println();

        System.out.println("Set-右边弹出，左边压入...................");
        List<Object> list2 = redisComponent.rightPopAndLeftPush(key, key, 2);
        list2.stream().forEach(System.out::print);
        System.out.println();

        System.out.println("Set-通过start-end获取元素集合...................");
        List<Object> list = redisComponent.lGet(key, 0, 3);
        list.stream().forEach(System.out::print);
        System.out.println();

        String key2 = "fansList2";
        redisComponent.sSetPipe(key2, Arrays.asList("B", "C", "E", "F", "G"));

        System.out.println("fansList3...................");
        String key3 = "fansList3";
        redisComponent.sInterAndStore(key, key2, key3);
        redisComponent.sGet(key3).stream().forEach(System.out::print);
        System.out.println();

        redisComponent.sSetRemove(key3, "B");
        redisComponent.sGet(key3).stream().forEach(System.out::print);
        System.out.println();

        boolean hasC = redisComponent.sIsMember(key, "C");
        System.out.println("hasC:" + hasC);

        Object obj = redisComponent.sPop(key);
        System.out.println("sPop obj:" + obj);

        return true;
    }

}
