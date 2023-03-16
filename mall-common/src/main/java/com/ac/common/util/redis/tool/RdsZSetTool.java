package com.ac.common.util.redis.tool;

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
 * @date 2023/02/24
 */
@Component
public class RdsZSetTool {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * ZSet-存入一个元素
     *
     * @param key   键
     * @param value 值
     * @param score 分数(排序序号，asc排序)
     * @return
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
     * ZSet-批量存入元素
     *
     * @param key
     * @param set
     * @return
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
     * ZSet-批量存入元素
     *
     * @param key
     * @param valueMap Object=元素值;Double=分数
     * @return
     */
    public Long zAdd(String key, Map<Object, Double> valueMap) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> zSet = new HashSet<>();
            for (Object value : valueMap.keySet()) {
                zSet.add(new DefaultTypedTuple<>(value, valueMap.get(value)));
            }
            return redisTemplate.opsForZSet().add(key, zSet);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * ZSet-对比两个有序集合的交集并将结果集存储在新的有序集合dest中
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
     * ZSet-获取zSet长度
     *
     * @param key 键
     * @return 长度
     */
    public long zSize(String key) {
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
     * ZSet-获取zSet指定区间分数的成员数
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
     * ZSet-获取元素分数值
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
     * ZSet-返回指定成员的下标值
     *
     * @param key 键
     * @param obj 元素
     * @return 下标值
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
     * ZSet-返回指定成员的下标值(从后往前取)
     *
     * @param key 键
     * @param obj 元素
     * @return 下标值
     */
    public Long zReverseRank(String key, Object obj) {
        try {
            return redisTemplate.opsForZSet().reverseRank(key, obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * ZSet-判断是否存在指定元素
     *
     * @param key 键
     * @param obj 元素
     * @return true存在 false不存在
     */
    public boolean zHasElement(String key, Object obj) {
        return this.zRank(key, obj) >= 0;
    }

    /**
     * ZSet-获取指定下标的元素
     *
     * @param key
     * @param index
     * @return
     */
    public Object zGetByIndex(String key, long index) {
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
     * ZSet-根据索引区间获取zSet列表
     *
     * @param start 开始索引
     * @param end   结束索引 -1查询全部
     * @return zSet列表
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

    /**
     * ZSet-根据索引区间获取ZSet列表(从后往前取)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
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
     * ZSet-根据分数区间获取Set列表
     *
     * @param min 开始分数
     * @param max 结束分数
     * @return
     */
    public LinkedHashSet<Object> zRangeByScore(String key, double min, double max) {
        try {
            Set<Object> set = redisTemplate.opsForZSet().rangeByScore(key, min, max);
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
     * ZSet-根据下标区间获取Set列表(返回元素、分数值)
     *
     * @param start 开始下标
     * @param end   结束下标
     * @return
     */
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

    /**
     * ZSet-根据分数区间获取Set列表(返回元素、分数值)
     *
     * @param min 开始分数
     * @param max 结束分数
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
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
