package com.ac.member.controller;

import com.ac.common.util.redis.RdsComponent;
import com.ac.member.dto.MemberDTO;
import com.ac.member.enums.MemberSexEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
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
    private RdsComponent rdsComponent;

    //============================第1部分：common start=============================
    @ApiOperation(value = "删除key")
    @GetMapping("del")
    public Boolean del(@RequestParam String key) {
        rdsComponent.del(key);
        return true;
    }

    @ApiOperation(value = "是否有key")
    @GetMapping("hasKey")
    public Boolean hasKey(@RequestParam String key) {
        return rdsComponent.hasKey(key);
    }

    @ApiOperation(value = "设置过期时间")
    @GetMapping("expire")
    public Boolean expire(@RequestParam String key, long time) {
        return rdsComponent.expire(key, time);
    }

    @ApiOperation(value = "获取过期时间")
    @GetMapping("getExpire")
    public Long getExpire(@RequestParam String key) {
        return rdsComponent.getExpire(key);
    }


    //============================第2部分：String start=============================
    @ApiOperation(value = "存-取-字符串")
    @GetMapping("testString")
    public String testString() {
        String key = "userName";
        String value = "AlanChen";

        rdsComponent.set(key, value);
        return rdsComponent.getStr(key);
    }

    @ApiOperation(value = "存-取-字符串（只有在key不存在时设置key的值）")
    @GetMapping("testStringIfAbsent")
    public String testStringIfAbsent() {
        String key = "userName";
        String value = "AlanChen";

        rdsComponent.setIfAbsent(key, value);
        return rdsComponent.getStr(key);
    }

    @ApiOperation(value = "存-取-Long")
    @GetMapping("testLong")
    public Long testLong() {
        String key = "likeNum";
        Long value = 1L;

        rdsComponent.set(key, value);
        Object obj = rdsComponent.get(key);
        return Long.valueOf(obj.toString());
    }

    @ApiOperation(value = "Long-自增")
    @GetMapping("testIncr")
    public Long testIncr() {
        String key = "likeNum";

        rdsComponent.incr(key);
        Object obj = rdsComponent.get(key);
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
        rdsComponent.hmSetObj(key, member);
        rdsComponent.hmSetObj(key, member, 10);
        rdsComponent.hmSetObj(key, member, 10, TimeUnit.MINUTES);

        //取
        return rdsComponent.hmGetObj(key, MemberDTO.class);
    }

    @ApiOperation(value = "Hash-取对象字段")
    @GetMapping("hGet")
    public Object hGet() {
        String key = "member";
        String item = "memberName";

        Object obj = rdsComponent.hGet(key, item);
        return obj;
    }

    @ApiOperation(value = "Hash-设置对象字段")
    @GetMapping("hSet")
    public Object hSet() {
        String key = "member";
        String item = "memberName";
        Object value = "AC";

        rdsComponent.hSet(key, item, value);
        return rdsComponent.hGet(key, item);
    }

    @ApiOperation(value = "Hash-删除对象字段")
    @GetMapping("hDel")
    public MemberDTO hDel() {
        String key = "member";
        String item = "memberName";

        //删除字段
        rdsComponent.hDel(key, item);
        //取
        return rdsComponent.hmGetObj(key, MemberDTO.class);
    }

    @ApiOperation(value = "Hash-判断对象字段是否存在")
    @GetMapping("hHasKey")
    public boolean hHasKey() {
        String key = "member";
        String item = "memberName";

        return rdsComponent.hHasKey(key, item);
    }

    @ApiOperation(value = "Hash-获取对象指定字段长度")
    @GetMapping("hLen")
    public Long hLen() {
        String key = "member";
        String item = "mobile";

        return rdsComponent.hLen(key, item);
    }

    @ApiOperation(value = "Hash-对象字段递增")
    @GetMapping("hIncr")
    public MemberDTO hIncr() {
        String key = "member";
        String item = "id";

        rdsComponent.hIncr(key, item, 2);
        return rdsComponent.hmGetObj(key, MemberDTO.class);

    }

    @ApiOperation(value = "Hash-对象字段递减")
    @GetMapping("hDecr")
    public MemberDTO hDecr() {
        String key = "member";
        String item = "id";

        rdsComponent.hDecr(key, item, 1);
        return rdsComponent.hmGetObj(key, MemberDTO.class);
    }

    @ApiOperation(value = "Hash-Map存储")
    @GetMapping("hmSet")
    public Map<Object, Object> hmSet() {
        String key = "loginMap";

        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("name", "AC");
        loginMap.put("pwd", "123abc");

        //存
        rdsComponent.hmSet(key, loginMap);
        rdsComponent.hmSet(key, loginMap, 10);
        rdsComponent.hmSet(key, loginMap, 10, TimeUnit.MINUTES);

        //取
        return rdsComponent.hmGet(key);
    }

    //================================第4部分：List start=================================

    @ApiOperation(value = "List-存取数据")
    @GetMapping("list")
    public boolean list() {
        String key = "list";
        rdsComponent.del(key);

        rdsComponent.rightPushAll(key, Arrays.asList("B", "C", "D", "E", "F", "G"));

        //BCDEFG
        System.out.println("list...................");
        rdsComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //ABCDEFG
        System.out.println("从左边压入元素...................");
        rdsComponent.lLeftPush(key, "A");
        rdsComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //ABCDEFGH
        System.out.println("从右边压入元素...................");
        rdsComponent.lRightPush(key, "H");
        rdsComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //ABC
        System.out.println("取前三个list...................");
        rdsComponent.lGet(key, 0, 2).stream().forEach(System.out::print);
        System.out.println();

        //HG
        System.out.println("从右边(倒着取)开始取2个-右边弹出，左边压入...................");
        List<Object> list2 = rdsComponent.rightPopAndLeftPush(key, key, 2);
        list2.stream().forEach(System.out::print);
        System.out.println();

        //GHABCDEF
        System.out.println("显示全部list...................");
        rdsComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //GH
        System.out.println("弹出多个元素list...................");
        rdsComponent.lLeftMultiPop(key, 2).stream().forEach(System.out::print);
        System.out.println();

        //A
        System.out.println("从左边弹出一个元素...................");
        System.out.println(rdsComponent.lLeftPop(key));

        //BCDEF
        System.out.println("显示全部list...................");
        rdsComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //5
        System.out.println("获取List长度...................");
        System.out.println(rdsComponent.lGetListSize(key));

        //C
        System.out.println("获取指定下标的元素...................");
        System.out.println(rdsComponent.lGetByIndex(key, 1));

        //bCDEF
        System.out.println("根据索引修改list中的某条数据...................");
        rdsComponent.lUpdateByIndex(key, 0, "b");
        rdsComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //bDEF
        System.out.println("移除N个值为value的元素...................");
        rdsComponent.lRemove(key, 1, "C");
        rdsComponent.lGet(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        return true;
    }

    //================================第5部分：Set start=================================
    @ApiOperation(value = "Set-存取数据")
    @GetMapping("sSet")
    public boolean sSet() {
        String key = "setKey";
        String key2 = "setKey2";
        String key3 = "setKey3";

        rdsComponent.del(key);
        rdsComponent.del(key2);
        rdsComponent.del(key3);

        rdsComponent.sSetList(key, Arrays.asList("A", "B", "C"));

        //CBA
        System.out.println("显示全部setKey...................");
        rdsComponent.sGet(key).stream().forEach(System.out::print);
        System.out.println();

        //size:3
        Long size = rdsComponent.sSize(key);
        System.out.println("size:" + size);

        //ACBDGEF
        System.out.println("存集合...................");
        rdsComponent.sPipeSetList(key, Arrays.asList("D", "E", "F", "G"));
        rdsComponent.sGet(key).stream().forEach(System.out::print);
        System.out.println();

        //hasC:true
        boolean hasC = rdsComponent.sIsMember(key, "C");
        System.out.println("hasC:" + hasC);

        //DACGEHF
        System.out.println("存集合setKey2...................");
        rdsComponent.sSetList(key2, Arrays.asList("A", "C", "D", "E", "F", "G", "H"), 100);
        rdsComponent.sGet(key2).stream().forEach(System.out::print);
        System.out.println();

        //CDAGEF
        System.out.println("查询两个集合的交集, 并存储于其他setKey3上...................");

        rdsComponent.sInterAndStore(key, key2, key3);
        rdsComponent.sGet(key3).stream().forEach(System.out::print);
        System.out.println();

        //DAGEF
        System.out.println("Set-移除值为value的元素...................");
        rdsComponent.sSetRemove(key3, "C");
        rdsComponent.sGet(key3).stream().forEach(System.out::print);
        System.out.println();

        //GAF
        System.out.println("Set-移除多个元素...................");
        rdsComponent.sSetRemove(key3, Arrays.asList("D", "E"));
        rdsComponent.sGet(key3).stream().forEach(System.out::print);
        System.out.println();

        //GA
        System.out.println("Set-随机弹出一个元素...................");
        rdsComponent.sPop(key3);
        rdsComponent.sGet(key3).stream().forEach(System.out::print);
        System.out.println();

        return true;
    }

    //================================第6部分：zSet start=================================
    @ApiOperation(value = "zSet-存取数据")
    @GetMapping("zSet")
    public boolean zSet() {
        String key = "zSet";
        String key2 = "zSet2";
        String key3 = "zSet3";
        rdsComponent.del(key);
        rdsComponent.del(key2);
        rdsComponent.del(key3);

        System.out.println("存入元素");
        rdsComponent.zAdd(key, "A", 1);
        rdsComponent.zAdd(key, "C", 3);
        rdsComponent.zAdd(key, "B", 2);
        rdsComponent.zAdd(key, "D", 4);

        //ABC
        rdsComponent.zRange(key, 0, 2).stream().forEach(System.out::print);
        System.out.println();

        //ABCD
        rdsComponent.zRange(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //B
        System.out.println("获取指定下标的元素");
        Object obj = rdsComponent.zGetByIndex(key, 1);
        System.out.println(obj);

        //4
        long size = rdsComponent.zSize(key);
        System.out.println("获取zSet长度:" + size);

        //2
        long count = rdsComponent.zCount(key, 1D, 2D);
        System.out.println("获取zSet指定区间分数的成员数:" + count);

        //2.0
        Double score = rdsComponent.zScore(key, "B");
        System.out.println("获取元素分数值:" + score);

        //1
        Long index = rdsComponent.zRank(key, "B");
        System.out.println("返回指定成员的下标值:" + index);

        //true
        boolean hasB = rdsComponent.zHasElement(key, "B");
        System.out.println("判断是否存在指定元素B:" + hasB);

        //ACE
        System.out.println("批量存入元素key2");
        Set<ZSetOperations.TypedTuple<Object>> set = new HashSet<>();
        set.add(new DefaultTypedTuple("A", 1D));
        set.add(new DefaultTypedTuple("E", 5D));
        set.add(new DefaultTypedTuple("C", 3D));

        rdsComponent.zAdd(key2, set);
        rdsComponent.zRange(key2, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //AC
        System.out.println("对比两个有序集合的交集并将结果集存储在新的有序集合dest中");
        rdsComponent.zInterAndStore(key, key2, key3);
        rdsComponent.zRange(key3, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        return true;
    }

}
