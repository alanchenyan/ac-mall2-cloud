package com.ac.common.util.redis;

import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Alan Chen
 * @description Redis-ZSet类型
 * 备注：该类主要是为了辅助RedisComponent完成功能，只希望RedisComponent能访问，因此该类没有设置为public
 * @date 2023/02/24
 */
@Component
class RdsZSetTool {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RdsCommonTool rdsCommonTool;

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
     * @param obj 成员对象
     * @return 缓存对象
     */
    public Long zRank(String key, Object obj) {
        try {
            Long index = redisTemplate.opsForZSet().rank(key, obj);
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
     * @param obj 成员对象
     * @return true存在 false不存在
     */
    public boolean zHasKey(String key, Object obj) {
        return this.zRank(key, obj) >= 0;
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
            return new LinkedHashSet();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    public LinkedHashSet<Object> zRevRange(String key, long start, long end) {
        try {
            Set<Object> set = redisTemplate.opsForZSet().reverseRange(key, start, end);
            if (set == null || set.size() == 0) {
                return null;
            }
            return (LinkedHashSet<Object>) set;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedHashSet();
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
            return new LinkedHashSet();
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
}
