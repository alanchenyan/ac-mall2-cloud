package com.ac.common.util.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RdsCommonTool rdsCommonTool;

    @Resource
    private RdsStringTool rdsStringTool;

    @Resource
    private RdsHashTool rdsHashTool;

    @Resource
    private RdsSetTool rdsSetTool;

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

    public Long sSize(String key) {
        return rdsSetTool.sSize(key);
    }

    public Set<Object> sGet(String key) {
        return rdsSetTool.sGet(key);
    }

    public Object sPop(String key) {
        return rdsSetTool.sPop(key);
    }

    public boolean sHasKey(String key, Object value) {
        return rdsSetTool.sHasKey(key, value);
    }

    public long sSet(String key, Object... values) {
        return rdsSetTool.sSet(key, values);
    }

    public boolean sSetPipe(String key, List<Object> datas) {
        return rdsSetTool.sSetPipe(key, datas);
    }

    public boolean sIsMember(String key, Object value) {
        return rdsSetTool.sIsMember(key, value);
    }

    public long sSetAndTime(String key, long time, TimeUnit timeUnit, Object... values) {
        return rdsSetTool.sSetAndTime(key, time, timeUnit, values);
    }

    public long sSetAndTime(String key, long time, Object... values) {
        return rdsSetTool.sSetAndTime(key, time, values);
    }

    public long sGetSetSize(String key) {
        return rdsSetTool.sGetSetSize(key);
    }

    public Set<Object> sInter(String key1, String key2) {
        return rdsSetTool.sInter(key1, key2);
    }

    public Long sInterAndStore(String key1, String key2, String storeKey) {
        return rdsSetTool.sInterAndStore(key1, key2, storeKey);
    }

    public long sSetRemove(String key, Long value) {
        return sSetRemove(key, value);
    }

    public long sSetRemove(String key, Object... values) {
        return rdsSetTool.sSetRemove(key, values);
    }

    public List<Object> lGet(String key, long start, long end) {
        return rdsSetTool.lGet(key, start, end);
    }

    public List<Object> lRightPopLeftPush(String sourceKey, String destinationKey, int limit) {
        return rdsSetTool.lRightPopLeftPush(sourceKey, destinationKey, limit);
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

    //===============================sorted set=================================

    /**
     * 将数据放入sorted set缓存
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return 成功个数
     */
    public boolean zAdd(String key, Object value, double score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 将数据放入sorted set缓存
     *
     * @param key 键
     * @param set 值集合
     * @return 成功个数
     */
    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> set) {
        try {
            return redisTemplate.opsForZSet().add(key, set);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 将数据放入sorted set缓存
     *
     * @param key      键
     * @param valueMap 值集合
     * @return 成功个数
     */
    public Long zAdd(String key, Map<Object, Double> valueMap) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typles = new HashSet<>();
            for (Object value : valueMap.keySet()) {
                typles.add(new DefaultTypedTuple<>(value, valueMap.get(value)));
            }
            return redisTemplate.opsForZSet().add(key, typles);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 将多个数据放入sorted set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @param score  分数
     * @return 成功个数
     */
    public boolean zAddAll(String key, List<Object> values, double score) {
        try {
            //TODO 多值插入
            for (Object value : values) {
                redisTemplate.opsForZSet().add(key, value, score);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 对比两个有序集合的交集并将结果集存储在新的有序集合dest中
     *
     * @param key2 键1
     * @param key2 键2
     * @return 成功个数
     */
    public long zInterAndStore(String key1, String key2, String destKey) {
        try {
            return redisTemplate.opsForZSet().intersectAndStore(key1, key2, destKey);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 获取sorted set缓存集合长度
     *
     * @param key 键
     * @return 成功个数
     */
    public long zCard(String key) {
        try {
            return redisTemplate.opsForZSet().zCard(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 获取sorted set缓存集合指定区间分数的成员数
     *
     * @param key 键
     * @return 成功个数
     */
    public long zCount(String key, Double min, Double max) {
        try {
            return redisTemplate.opsForZSet().count(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 获取sorted set缓存集合中成员分数值
     *
     * @param key   键
     * @param value 值
     * @return 分数值
     */
    public Double zScore(String key, Object value) {
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0D;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 返回有序集合中指定成员的索引
     *
     * @param key 键
     * @param o   成员对象
     * @return 缓存对象
     */
    public Long zRank(String key, Object o) {
        try {
            Long index = redisTemplate.opsForZSet().rank(key, o);
            if (index == null) {
                return -1L;
            }
            return index;
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 查看有序集合中是否存在指定成员
     *
     * @param key 键
     * @param o   成员对象
     * @return true存在 false不存在
     */
    public Boolean zHasKey(String key, Object o) {
        return this.zRank(key, o) >= 0;
    }

    /**
     * 根据索引获取一个sorted set缓存对象
     *
     * @param index 键
     * @return 缓存对象
     */
    public Object zRange(String key, long index) {
        try {
            LinkedHashSet<Object> set = zRange(key, index, index);
            Iterator<Object> iterator = set.iterator();
            return iterator.hasNext() ? iterator.next() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 根据索引区间获取一个sorted set缓存对象
     *
     * @param start 开始索引
     * @param end   结束索引
     * @return 缓存对象
     */
    public LinkedHashSet<Object> zRange(String key, long start, long end) {
        try {
            Set<Object> set = redisTemplate.opsForZSet().range(key, start, end);
            if (set == null || set.size() == 0) {
                return (LinkedHashSet<Object>) set;
            }
            return (LinkedHashSet<Object>) set;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedHashSet<Object>();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    public LinkedHashSet<Object> zrevRange(String key, long start, long end) {
        try {
            Set<Object> set = redisTemplate.opsForZSet().reverseRange(key, start, end);
            if (set == null || set.size() == 0) {
                return null;
            }
            return (LinkedHashSet<Object>) set;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedHashSet<Object>();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 判断value在zset中的排名  zrank
     *
     * @param key
     * @param value
     * @return
     */
    public Long rank(String key, String value) {
        try {
            return redisTemplate.opsForZSet().reverseRank(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }


    /**
     * 根据分数区间获取一个sorted set缓存对象
     *
     * @param start 开始分数
     * @param end   结束分数
     * @return 缓存对象
     */
    public LinkedHashSet<Object> zRangeByScore(String key, long start, long end) {
        try {
            Set<Object> set = redisTemplate.opsForZSet().rangeByScore(key, start, end);
            if (set == null || set.size() == 0) {
                return null;
            }
            return (LinkedHashSet<Object>) set;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedHashSet<Object>();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double scoreMin, double scoreMax) {
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key, scoreMin, scoreMax);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double scoreMin, double scoreMax, long offset, long count) {
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key, scoreMin, scoreMax, offset, count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeByScoreWithScores(String key, double scoreMin, double scoreMax, long offset, long count) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, scoreMin, scoreMax, offset, count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * sorted set缓存对象的score值增量+1
     *
     * @param key   键
     * @param value 值
     * @param incr  增量值
     * @return 是否成功
     */
    public Double zIncr(String key, Object value, long incr) {
        try {
            Double aDouble = redisTemplate.opsForZSet().incrementScore(key, value, incr);
            return aDouble;
        } catch (Exception e) {
            e.printStackTrace();
            return -1D;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 移除成员
     *
     * @param key    键
     * @param object 对象
     * @return 移除数量
     */
    public Long zRemove(String key, Object object) {
        try {
            return redisTemplate.opsForZSet().remove(key, object);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 移除成员
     *
     * @param key     键
     * @param objects 对象数组
     * @return 移除数量
     */
    public Long zRemove(String key, Object... objects) {
        try {
            return redisTemplate.opsForZSet().remove(key, objects);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * geo-添加
     *
     * @param key    键
     * @param member 成员
     * @param lng    经度
     * @param lat    纬度
     * @return 成功数量
     */
    public Long geoAdd(String key, String member, BigDecimal lng, BigDecimal lat) {
        try {
            return redisTemplate.opsForGeo().add(key, new Point(lng.doubleValue(), lat.doubleValue()), member);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * geo-获取坐标
     *
     * @param key    键
     * @param member 成员
     * @return 成功数量
     */
    public Point geoGet(String key, String member) {
        try {
            List<Point> pointList = redisTemplate.opsForGeo().position(key, member);
            if (pointList.size() > 0) {
                return pointList.get(0);
            }
            return new Point(0.0, 0.0);
        } catch (Exception e) {
            e.printStackTrace();
            return new Point(0.0, 0.0);
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * geo-获取坐标
     *
     * @param key     键
     * @param members 成员数组
     * @return 成功数量
     */
    public List<Point> geoGet(String key, String... members) {
        try {
            return redisTemplate.opsForGeo().position(key, members);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * geo-计算距离
     *
     * @param key     键
     * @param member1 成员1
     * @param member2 成员2
     * @return 成功数量
     */
    public Distance geoDist(String key, String member1, String member2) {
        try {
            Distance distance = redisTemplate.opsForGeo().distance(key, member1, member2);
            return distance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * geo-计算距离
     *
     * @param key     键
     * @param member1 成员1
     * @param member2 成员2
     * @param metrics 度规（枚举）（km、m）
     * @return 成功数量
     */
    public Distance geoDist(String key, String member1, String member2, Metrics metrics) {
        try {
            Distance distance = redisTemplate.opsForGeo().distance(key, member1, member2, metrics);
            return distance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * geo-范围成员
     *
     * @param key    键
     * @param member 成员
     * @return 成功数量
     */
    public List<String> geoRadius(String key, String member, BigDecimal v, Metrics metrics) {
        try {
            GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = redisTemplate.opsForGeo().radius(key, member, new Distance(v.doubleValue(), metrics));
            List<String> result = new ArrayList<>();
            for (GeoResult<RedisGeoCommands.GeoLocation<Object>> geoResult : geoResults.getContent()) {
                result.add(geoResult.getContent().getName().toString());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 获取批量keys对应的列表中，指定的hash键值对列表
     *
     * @param keySet redis 键
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Object> getHashObjectsByKeys(Collection<String> keySet) {
        try {
            return redisTemplate.executePipelined(new SessionCallback<Object>() {
                @Override
                public <K, V> Map execute(RedisOperations<K, V> operations) throws DataAccessException {
                    HashOperations hashOperations = operations.opsForHash();
                    for (String key : keySet) {
                        hashOperations.entries(key);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }
}
