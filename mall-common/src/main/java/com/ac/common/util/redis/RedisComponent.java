package com.ac.common.util.redis;

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
public class RedisComponent {

    @Resource
    private RdsCommonTool rdsCommonTool;

    @Resource
    private RdsStringTool rdsStringTool;

    @Resource
    private RdsHashTool rdsHashTool;

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


    //================================第4部分：Set start=================================

    /**
     * Set-集合size
     *
     * @param key
     * @return
     */
    public Long sSize(String key) {
        return rdsSetTool.sSize(key);
    }

    /**
     * Set-获取集合
     *
     * @param key
     * @return
     */
    public Set<Object> sGet(String key) {
        return rdsSetTool.sGet(key);
    }

    /**
     * Set-根据key随机弹出t中的值
     *
     * @param key
     * @return
     */
    public Object sPop(String key) {
        return rdsSetTool.sPop(key);
    }

    /**
     * Set-存数据(不过期)
     *
     * @param key
     * @param values
     * @return
     */
    public long sSet(String key, Object... values) {
        return rdsSetTool.sSet(key, values);
    }

    /**
     * Set-存集合(不过期)
     *
     * @param key
     * @param list
     * @return
     */
    public boolean sSetPipe(String key, List<Object> list) {
        return rdsSetTool.sSetPipe(key, list);
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
     * Set-存数据(设置过期时间，单位)
     *
     * @param key
     * @param time
     * @param timeUnit
     * @param values
     * @return
     */
    public long sSetAndTime(String key, long time, TimeUnit timeUnit, Object... values) {
        return rdsSetTool.sSetAndTime(key, time, timeUnit, values);
    }

    /**
     * Set-存数据(设置过期时间)
     *
     * @param key
     * @param time
     * @param values
     * @return
     */
    public long sSetAndTime(String key, long time, Object... values) {
        return rdsSetTool.sSetAndTime(key, time, values);
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
     * @return
     */
    public long sSetRemove(String key, Long value) {
        return rdsSetTool.sSetRemove(key, value);
    }

    /**
     * Set-移除多个元素
     *
     * @param key
     * @param values
     * @return
     */
    public long sSetRemove(String key, Object... values) {
        return rdsSetTool.sSetRemove(key, values);
    }

    /**
     * Set-通过start-end获取元素集合
     *
     * @param key
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        return rdsSetTool.lGet(key, start, end);
    }

    /**
     * Set-右边弹出，左边压入
     *
     * @param sourceKey
     * @param destinationKey
     * @param limit
     * @return
     */
    public List<Object> rightPopAndLeftPush(String sourceKey, String destinationKey, int limit) {
        return rdsSetTool.rightPopAndLeftPush(sourceKey, destinationKey, limit);
    }

    public List<Object> lMultiPop(String key, int limit) {
        return rdsSetTool.lMultiPop(key, limit);
    }

    public Object lPop(String key) {
        return rdsSetTool.lPop(key);
    }

    public long lGetListSize(String key) {
        return rdsSetTool.lGetListSize(key);
    }

    public Object lGetIndex(String key, long index) {
        return rdsSetTool.lGetIndex(key, index);
    }

    public boolean lSet(String key, Object value) {
        return rdsSetTool.lSet(key, value);
    }

    public boolean lSet(String key, Object value, long time) {
        return rdsSetTool.lSet(key, value, time);
    }

    public boolean lSetAll(String key, List<Object> value) {
        return rdsSetTool.lSetAll(key, value);
    }

    public boolean lSet(String key, List<Object> value, long time) {
        return rdsSetTool.lSet(key, value, time);
    }

    public boolean lUpdateIndex(String key, long index, Object value) {
        return rdsSetTool.lUpdateIndex(key, index, value);
    }

    public long lRemove(String key, long count, Object value) {
        return rdsSetTool.lRemove(key, count, value);
    }

    //================================第4部分：Set end=================================

    //================================第5部分：zSet start=================================

    public boolean zAdd(String key, Object value, double score) {
        return rdsZSetTool.zAdd(key, value, score);
    }

    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> set) {
        return rdsZSetTool.zAdd(key, set);
    }

    public Long zAdd(String key, Map<Object, Double> valueMap) {
        return rdsZSetTool.zAdd(key, valueMap);
    }

    public boolean zAddAll(String key, List<Object> values, double score) {
        return rdsZSetTool.zAddAll(key, values, score);
    }

    public long zInterAndStore(String key1, String key2, String destKey) {
        return rdsZSetTool.zInterAndStore(key1, key2, destKey);
    }

    public long zCard(String key) {
        return rdsZSetTool.zCard(key);
    }

    public long zCount(String key, Double min, Double max) {
        return rdsZSetTool.zCount(key, min, max);
    }

    public Double zScore(String key, Object value) {
        return rdsZSetTool.zScore(key, value);
    }

    public Long zRank(String key, Object obj) {
        return rdsZSetTool.zRank(key, obj);
    }

    public boolean zHasKey(String key, Object obj) {
        return rdsZSetTool.zHasKey(key, obj);
    }

    public Object zRange(String key, long index) {
        return rdsZSetTool.zRange(key, index);
    }

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

    //================================第5部分：zSet end=================================

    //================================第6部分：GEO start=================================

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

    //================================第6部分：GEO end=================================
}
