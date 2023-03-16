package com.ac.common.util.redis;

import com.ac.common.util.redis.tool.*;
import org.springframework.data.geo.*;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Alan Chen
 * @description Redis工具类
 * @date 2023/02/24
 */
@Component
public class RdsComponent {

    @Resource
    private RdsCommonTool rdsCommonTool;

    @Resource
    private RdsStringTool rdsStringTool;

    @Resource
    private RdsHashTool rdsHashTool;

    @Resource
    private RdsListTool rdsListTool;

    @Resource
    private RdsSetTool rdsSetTool;

    @Resource
    private RdsZSetTool rdsZSetTool;

    @Resource
    private RdsGeoTool rdsGeoTool;

    //============================第1部分：common start=============================

    /**
     * 是否有key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return rdsCommonTool.hasKey(key);
    }

    /**
     * 返回key集合
     *
     * @param key 键
     * @return
     */
    public Set<String> keys(String key) {
        return rdsCommonTool.keys(key);
    }

    /**
     * 设置key的过期时间(默认单位：秒)
     *
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        return rdsCommonTool.expire(key, time);
    }

    /**
     * 设置key的过期时间
     *
     * @param key
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        return rdsCommonTool.expire(key, time, timeUnit);
    }

    /**
     * 获取key的过期时间(默认单位：秒)
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return rdsCommonTool.getExpire(key);
    }

    /**
     * 获取key的过期时间
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getExpire(String key, TimeUnit timeUnit) {
        return rdsCommonTool.getExpire(key, timeUnit);
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public Boolean del(String... key) {
        return rdsCommonTool.del(key);
    }

    /**
     * 批量删除key
     *
     * @param keys
     * @return
     */
    public Long del(Collection<String> keys) {
        return rdsCommonTool.del(keys);
    }

    //============================第1部分：common end=============================

    //============================第2部分：String start=============================

    /**
     * String-获取String
     *
     * @param key
     * @return
     */
    public String getStr(String key) {
        return rdsStringTool.getStr(key);
    }

    /**
     * String-获取基本数据类型对象
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return rdsStringTool.get(key);
    }

    /**
     * String-设置基本数据类型对象(不过期)
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        return rdsStringTool.set(key, value);
    }

    /**
     * String-设置基本数据类型对象(过期时间，默认单位：秒)
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value, long time) {
        return rdsStringTool.set(key, value, time);
    }

    /**
     * String-设置基本数据类型对象(过期时间，过期单位)
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        return rdsStringTool.set(key, value, time, timeUnit);
    }

    /**
     * String-设置基本数据类型对象-不过期(只有在key不存在时设置key的值)
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, Object value) {
        return rdsStringTool.setIfAbsent(key, value);
    }

    /**
     * String-设置基本数据类型对象(只有在key不存在时设置key的值)
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, Object value, long time) {
        return rdsStringTool.setIfAbsent(key, value, time);
    }

    /**
     * String-设置基本数据类型对象(只有在key不存在时设置key的值)
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit) {
        return rdsStringTool.setIfAbsent(key, value, time, timeUnit);
    }

    /**
     * String-递增(默认按1递增)
     *
     * @param key
     * @return
     */
    public long incr(String key) {
        return rdsStringTool.incr(key);
    }

    /**
     * String-递增(递增幅度)
     *
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        return rdsStringTool.incr(key, delta);
    }

    /**
     * String-递减(默认按1递减)
     *
     * @param key
     * @return
     */
    public long decr(String key) {
        return rdsStringTool.decr(key);
    }

    /**
     * String-递减(递减幅度)
     *
     * @param key
     * @param delta
     * @return
     */
    public long decr(String key, long delta) {
        return rdsStringTool.decr(key, delta);
    }

    //============================第2部分：String end=============================

    //================================第3部分：Hash start=================================

    /**
     * Hash-取对象字段
     *
     * @param key
     * @param item
     * @return
     */
    public Object hGet(String key, String item) {
        return rdsHashTool.hGet(key, item);
    }

    /**
     * Hash-取对象
     *
     * @param key
     * @param target
     * @param <T>
     * @return
     */
    public <T> T hmGetObj(String key, Class<T> target) {
        return rdsHashTool.hmGetObj(key, target);
    }

    /**
     * Hash-存对象
     *
     * @param key
     * @param object
     * @return
     */
    public boolean hmSetObj(String key, Object object) {
        return rdsHashTool.hmSetObj(key, object);
    }

    /**
     * Hash-存对象(设置过期时间，单位默认秒)
     *
     * @param key
     * @param object
     * @param time
     * @return
     */
    public boolean hmSetObj(String key, Object object, long time) {
        return rdsHashTool.hmSetObj(key, object, time);
    }

    /**
     * Hash-存对象(设置过期时间，单位)
     *
     * @param key
     * @param object
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean hmSetObj(String key, Object object, long time, TimeUnit timeUnit) {
        return rdsHashTool.hmSetObj(key, object, time, timeUnit);
    }

    /**
     * Hash-设置对象字段值
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hSet(String key, String item, Object value) {
        return rdsHashTool.hSet(key, item, value);
    }

    /**
     * Hash-设置对象字段值（设置过期时间,单位默认秒）
     *
     * @param key
     * @param item
     * @param value
     * @param time
     * @return
     */
    public boolean hSet(String key, String item, Object value, long time) {
        return rdsHashTool.hSet(key, item, value, time);
    }

    /**
     * Hash-设置对象字段值（设置过期时间,单位）
     *
     * @param key
     * @param item
     * @param value
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean hSet(String key, String item, Object value, long time, TimeUnit timeUnit) {
        return rdsHashTool.hSet(key, item, value, time, timeUnit);
    }

    /**
     * Hash-获取Map
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hmGet(String key) {
        return rdsHashTool.hmGet(key);
    }

    /**
     * Hash-存Map(不过期)
     *
     * @param key
     * @param map
     * @return
     */
    public boolean hmSet(String key, Map<String, Object> map) {
        return rdsHashTool.hmSet(key, map);
    }

    /**
     * Hash-存Map-设置过期时间(单位默认秒)
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean hmSet(String key, Map<String, Object> map, long time) {
        return rdsHashTool.hmSet(key, map, time);
    }

    /**
     * Hash-存Map(设置过期时间，单位)
     *
     * @param key
     * @param map
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean hmSet(String key, Map<String, Object> map, long time, TimeUnit timeUnit) {
        return rdsHashTool.hmSet(key, map, time, timeUnit);
    }

    /**
     * Hash-删除对象字段
     *
     * @param key
     * @param item
     * @return
     */
    public Long hDel(String key, Object... item) {
        return rdsHashTool.hDel(key, item);
    }

    /**
     * Hash-判断对象字段是否存在
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hHasKey(String key, String item) {
        return rdsHashTool.hHasKey(key, item);
    }

    /**
     * Hash-获取对象指定字段长度
     *
     * @param key
     * @param item
     * @return
     */
    public Long hLen(String key, String item) {
        return rdsHashTool.hLen(key, item);
    }

    /**
     * Hash-对象字段递增
     *
     * @param key
     * @param item
     * @param by
     * @return
     */
    public long hIncr(String key, String item, long by) {
        return rdsHashTool.hIncr(key, item, by);
    }

    /**
     * Hash-对象字段递减
     *
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hDecr(String key, String item, double by) {
        return rdsHashTool.hDecr(key, item, by);
    }

    //================================第3部分：Hash end=================================


    //================================第4部分：List start=================================

    /**
     * List-通过start-end获取元素集合
     *
     * @param key
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        return rdsListTool.lGet(key, start, end);
    }

    /**
     * List-右边弹出（倒着取），左边压入
     *
     * @param sourceKey
     * @param destinationKey
     * @param limit
     * @return 弹出的元素
     */
    public List<Object> rightPopAndLeftPush(String sourceKey, String destinationKey, int limit) {
        return rdsListTool.rightPopAndLeftPush(sourceKey, destinationKey, limit);
    }

    /**
     * List-从左边弹出多个元素
     *
     * @param key
     * @param limit
     * @return 弹出的元素
     */
    public List<Object> lLeftMultiPop(String key, int limit) {
        return rdsListTool.lLeftMultiPop(key, limit);
    }

    /**
     * List-从左边弹出一个元素
     *
     * @param key
     * @return 弹出的元素
     */
    public Object lLeftPop(String key) {
        return rdsListTool.lLeftPop(key);
    }

    /**
     * List-获取List长度
     *
     * @param key
     * @return
     */
    public long lGetListSize(String key) {
        return rdsListTool.lGetListSize(key);
    }

    /**
     * List-获取指定下标的元素
     *
     * @param key
     * @param index
     * @return
     */
    public Object lGetByIndex(String key, long index) {
        return rdsListTool.lGetByIndex(key, index);
    }

    /**
     * List-从左边压入元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lLeftPush(String key, Object value) {
        return rdsListTool.lLeftPush(key, value);
    }

    /**
     * List-从左边压入元素
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lLeftPush(String key, Object value, long time) {
        return rdsListTool.lLeftPush(key, value, time);
    }

    /**
     * List-从左边压入元素
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean lLeftPush(String key, Object value, long time, TimeUnit timeUnit) {
        return rdsListTool.lLeftPush(key, value, time, timeUnit);
    }

    /**
     * List-从右边压入元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lRightPush(String key, Object value) {
        return rdsListTool.lRightPush(key, value);
    }

    /**
     * List-从右边压入元素
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lRightPush(String key, Object value, long time) {
        return rdsListTool.lRightPush(key, value, time);
    }

    /**
     * List-从右边压入元素
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lRightPush(String key, Object value, long time, TimeUnit timeUnit) {
        return rdsListTool.lRightPush(key, value, time, timeUnit);
    }

    /**
     * List-从右边压入多个元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean rightPushAll(String key, List<Object> value) {
        return rdsListTool.rightPushAll(key, value);
    }

    /**
     * List-从右边压入多个元素
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean rightPushAll(String key, List<Object> value, long time) {
        return rdsListTool.rightPushAll(key, value, time);
    }

    /**
     * List-从右边压入多个元素
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean rightPushAll(String key, List<Object> value, long time, TimeUnit timeUnit) {
        return rdsListTool.rightPushAll(key, value, time, timeUnit);
    }

    /**
     * List-根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateByIndex(String key, long index, Object value) {
        return rdsListTool.lUpdateByIndex(key, index, value);
    }

    /**
     * List-移除N个值为value的元素
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        return rdsListTool.lRemove(key, count, value);
    }

    //================================第4部分：List end=================================

    //================================第5部分：Set start=================================

    /**
     * Set-获取Set中的元素数
     *
     * @param key
     * @return
     */
    public Long sSize(String key) {
        return rdsSetTool.sSize(key);
    }

    /**
     * Set-获取Set中的所有元素
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        return rdsSetTool.sGet(key);
    }

    /**
     * Set-随机弹出一个元素
     *
     * @param key 键
     * @return 弹出的元素
     */
    public Object sPop(String key) {
        return rdsSetTool.sPop(key);
    }

    /**
     * Set-存入多个元素
     *
     * @param key  键
     * @param list
     * @return 成功个数
     */
    public long sSetList(String key, List<Object> list) {
        return rdsSetTool.sSetList(key, list);
    }

    /**
     * Set-存入多个元素
     *
     * @param key
     * @param list
     * @param time
     * @return
     */
    public long sSetList(String key, List<Object> list, long time) {
        return rdsSetTool.sSetList(key, list, time);
    }

    /**
     * Set-存入多个元素
     *
     * @param key
     * @param list
     * @param time
     * @param timeUnit
     * @return
     */
    public long sSetList(String key, List<Object> list, long time, TimeUnit timeUnit) {
        return rdsSetTool.sSetList(key, list, time, timeUnit);
    }

    /**
     * Set-批量存入多个元素
     *
     * @param key
     * @param list
     * @return
     */
    public boolean sPipeSetList(String key, List<Object> list) {
        return rdsSetTool.sPipeSetList(key, list);
    }

    /**
     * Set-判断成员元素是否是集合的成员
     *
     * @param key
     * @param value
     * @return
     */
    public boolean sIsMember(String key, Object value) {
        return rdsSetTool.sIsMember(key, value);
    }

    /**
     * Set-查询两个集合的交集
     *
     * @param key1
     * @param key2
     * @return
     */
    public Set<Object> sInter(String key1, String key2) {
        return rdsSetTool.sInter(key1, key2);
    }

    /**
     * Set-查询两个集合的交集, 并存储于其他key上
     *
     * @param key1
     * @param key2
     * @param storeKey
     * @return
     */
    public Long sInterAndStore(String key1, String key2, String storeKey) {
        return rdsSetTool.sInterAndStore(key1, key2, storeKey);
    }

    /**
     * Set-移除值为value的元素
     *
     * @param key
     * @param value
     * @return 移除的个数
     */
    public long sSetRemove(String key, Object value) {
        return rdsSetTool.sSetRemove(key, value);
    }

    /**
     * Set-移除多个元素
     *
     * @param key
     * @param list
     * @return 移除的个数
     */
    public long sSetRemove(String key, List<Object> list) {
        return rdsSetTool.sSetRemove(key, list);
    }

    //================================第5部分：Set end=================================

    //================================第6部分：zSet start=================================

    /**
     * ZSet-存入一个元素
     *
     * @param key   键
     * @param value 值
     * @param score 分数(排序序号，asc排序)
     * @return
     */
    public boolean zAdd(String key, Object value, double score) {
        return rdsZSetTool.zAdd(key, value, score);
    }

    /**
     * ZSet-批量存入元素
     *
     * @param key
     * @param set
     * @return
     */
    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> set) {
        return rdsZSetTool.zAdd(key, set);
    }

    /**
     * ZSet-批量存入元素
     *
     * @param key
     * @param valueMap Object=元素值;Double=分数
     * @return
     */
    public Long zAdd(String key, Map<Object, Double> valueMap) {
        return rdsZSetTool.zAdd(key, valueMap);
    }

    /**
     * ZSet-对比两个有序集合的交集并将结果集存储在新的有序集合dest中
     *
     * @param key2 键1
     * @param key2 键2
     * @return 成功个数
     */
    public long zInterAndStore(String key1, String key2, String destKey) {
        return rdsZSetTool.zInterAndStore(key1, key2, destKey);
    }

    /**
     * ZSet-获取zSet长度
     *
     * @param key 键
     * @return 长度
     */
    public long zSize(String key) {
        return rdsZSetTool.zSize(key);
    }

    /**
     * ZSet-获取zSet指定区间分数的成员数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public long zCount(String key, Double min, Double max) {
        return rdsZSetTool.zCount(key, min, max);
    }

    /**
     * ZSet-获取元素分数值
     *
     * @param key   键
     * @param value 值
     * @return 分数值
     */
    public Double zScore(String key, Object value) {
        return rdsZSetTool.zScore(key, value);
    }

    /**
     * ZSet-返回指定成员的下标值
     *
     * @param key 键
     * @param obj 元素
     * @return 下标值
     */
    public Long zRank(String key, Object obj) {
        return rdsZSetTool.zRank(key, obj);
    }

    /**
     * ZSet-判断是否存在指定元素
     *
     * @param key 键
     * @param obj 元素
     * @return true存在 false不存在
     */
    public boolean zHasElement(String key, Object obj) {
        return rdsZSetTool.zHasElement(key, obj);
    }

    /**
     * ZSet-获取指定下标的元素
     *
     * @param key
     * @param index
     * @return
     */
    public Object zGetByIndex(String key, long index) {
        return rdsZSetTool.zGetByIndex(key, index);
    }

    /**
     * ZSet-根据索引区间获取zSet列表
     *
     * @param start 开始索引
     * @param end   结束索引 -1查询全部
     * @return zSet列表
     */
    public LinkedHashSet<Object> zRange(String key, long start, long end) {
        return rdsZSetTool.zRange(key, start, end);
    }

    public LinkedHashSet<Object> zRevRange(String key, long start, long end) {
        return rdsZSetTool.zRevRange(key, start, end);
    }

    public Long rank(String key, String value) {
        return rdsZSetTool.rank(key, value);
    }

    public LinkedHashSet<Object> zRangeByScore(String key, long start, long end) {
        return rdsZSetTool.zRangeByScore(key, start, end);
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long start, long end) {
        return rdsZSetTool.zRangeWithScores(key, start, end);
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double scoreMin, double scoreMax) {
        return rdsZSetTool.zRangeByScoreWithScores(key, scoreMin, scoreMax);
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double scoreMin, double scoreMax, long offset, long count) {
        return rdsZSetTool.zRangeByScoreWithScores(key, scoreMin, scoreMax, offset, count);
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeByScoreWithScores(String key, double scoreMin, double scoreMax, long offset, long count) {
        return rdsZSetTool.zRevRangeByScoreWithScores(key, scoreMin, scoreMax, offset, count);
    }

    public Double zIncr(String key, Object value, long incr) {
        return rdsZSetTool.zIncr(key, value, incr);
    }

    public Long zRemove(String key, Object object) {
        return rdsZSetTool.zRemove(key, object);
    }

    public Long zRemove(String key, Object... objects) {
        return rdsZSetTool.zRemove(key, objects);
    }

    //================================第6部分：zSet end=================================

    //================================第7部分：GEO start=================================

    public Long geoAdd(String key, String member, BigDecimal lng, BigDecimal lat) {
        return rdsGeoTool.geoAdd(key, member, lng, lat);
    }

    public Point geoGet(String key, String member) {
        return rdsGeoTool.geoGet(key, member);
    }

    public List<Point> geoGet(String key, String... members) {
        return rdsGeoTool.geoGet(key, members);
    }

    public Distance geoDist(String key, String member1, String member2) {
        return rdsGeoTool.geoDist(key, member1, member2);
    }

    public Distance geoDist(String key, String member1, String member2, Metrics metrics) {
        return rdsGeoTool.geoDist(key, member1, member2, metrics);
    }

    public List<String> geoRadius(String key, String member, BigDecimal v, Metrics metrics) {
        return rdsGeoTool.geoRadius(key, member, v, metrics);
    }

    //================================第7部分：GEO end=================================
}
