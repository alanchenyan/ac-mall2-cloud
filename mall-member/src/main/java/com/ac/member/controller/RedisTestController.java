package com.ac.member.controller;

import com.ac.common.util.redis.RdsComponent;
import com.ac.member.dto.MemberDTO;
import com.ac.member.enums.MemberSexEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
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
        System.out.println("根据索引区间获取zSet列表");
        rdsComponent.zRange(key, 0, 2).stream().forEach(System.out::print);
        System.out.println();

        //DCB
        System.out.println("根据索引区间获取zSet列表(倒取)");
        rdsComponent.zRevRange(key, 0, 2).stream().forEach(System.out::print);
        System.out.println();

        //ABCD
        System.out.println("根据索引区间获取zSet列表（取全部）");
        rdsComponent.zRange(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        //ABC
        System.out.println("根据分数区间获取Set列表");
        rdsComponent.zRangeByScore(key, 0, 3).forEach(System.out::print);
        System.out.println();

        /**
         * A:1.0
         * B:2.0
         * C:3.0
         * D:4.0
         */
        System.out.println("根据下标区间获取Set列表(返回元素、分数值)");
        Set<ZSetOperations.TypedTuple<Object>> setResult = rdsComponent.zRangeWithScores(key, 0, 3);
        for (ZSetOperations.TypedTuple<Object> item : setResult) {
            System.out.println(item.getValue() + ":" + item.getScore());
        }
        System.out.println();


        /**
         * C:3.0
         * D:4.0
         */
        System.out.println("根据分数区间获取Set列表(返回元素、分数值)，再从下标offset开始，取count个元素");
        Set<ZSetOperations.TypedTuple<Object>> setResult2 = rdsComponent.zRangeByScoreWithScores(key, 2, 4, 1, 2);
        for (ZSetOperations.TypedTuple<Object> item : setResult2) {
            System.out.println(item.getValue() + ":" + item.getScore());
        }
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

        //2
        Long index2 = rdsComponent.zReverseRank(key, "B");
        System.out.println("返回指定成员的下标值(倒取):" + index2);

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

        /**
         * A:1.0
         * B:2.0
         * C:3.0
         * D:6.0
         */
        System.out.println("增加元素分数值");
        rdsComponent.zIncrScore(key, "D", 2);
        Set<ZSetOperations.TypedTuple<Object>> setResult3 = rdsComponent.zRangeWithScores(key, 0, -1);
        for (ZSetOperations.TypedTuple<Object> item : setResult3) {
            System.out.println(item.getValue() + ":" + item.getScore());
        }
        System.out.println();

        //B
        System.out.println("删除元素");
        rdsComponent.zRemove(key, "A");
        rdsComponent.zRemove(key, Arrays.asList("C", "D"));
        rdsComponent.zRange(key, 0, -1).stream().forEach(System.out::print);
        System.out.println();

        return true;
    }


    //================================第7部分：GEO start=================================
    @ApiOperation(value = "zSet-存取数据")
    @GetMapping("geo")
    public boolean geo() {
        String key = "geo";
        String member1 = "A001";
        String member2 = "A002";
        String member3 = "A003";

        rdsComponent.del(key);

        System.out.println("添加元素经纬度");
        rdsComponent.geoAdd(key, member1, 120, 60);
        rdsComponent.geoAdd(key, member2, 121, 61);
        rdsComponent.geoAdd(key, member3, 122, 62);

        //获取成员经纬度,x=120.00000089406967,y=59.99999923259345
        Point point = rdsComponent.geoPosition(key, member1);
        System.out.println("获取成员经纬度,x=" + point.getX() + ",y=" + point.getY());

        /**
         * 获取成员经纬度,x=120.00000089406967,y=59.99999923259345
         * 获取成员经纬度,x=120.99999815225601,y=60.99999995909701
         * 获取成员经纬度,x=122.00000077486038,y=62.00000068560058
         */
        System.out.println("批量获取成员经纬度");
        List<Point> pointList = rdsComponent.geoPositions(key, Arrays.asList(member1, member2, member3));
        for (Point item : pointList) {
            System.out.println("获取成员经纬度,x=" + item.getX() + ",y=" + item.getY());
        }

        //计算两个成员间的距离,Unit=m,Value=123976.7968,Metric=METERS
        Distance distance = rdsComponent.geoDistance(key, member1, member2);
        System.out.println("计算两个成员间的距离,Unit=" + distance.getUnit() + ",Value=" + distance.getValue() + ",Metric=" + distance.getMetric());

        //计算两个成员间的距离,指定单位千米,Unit=km,Value=123.9768,Metric=KILOMETERS
        Distance distance2 = rdsComponent.geoDistance(key, member1, member2, Metrics.KILOMETERS);
        System.out.println("计算两个成员间的距离,指定单位千米,Unit=" + distance2.getUnit() + ",Value=" + distance2.getValue() + ",Metric=" + distance2.getMetric());

        /**
         * A001
         * A002
         * A003
         */
        List<Object> list = rdsComponent.geoRadius(key, member2, 1000, Metrics.KILOMETERS);
        System.out.println("获取指定成员周围的成员列表");
        for (Object obj : list) {
            System.out.println(obj);
        }
        return true;
    }
}
