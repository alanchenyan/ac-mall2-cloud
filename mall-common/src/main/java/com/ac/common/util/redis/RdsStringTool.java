package com.ac.common.util.redis;

import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Alan Chen
 * @description Redis-String类型
 * 备注：该类主要是为了辅助RdsComponent完成功能，只希望RedisComponent能访问，因此该类没有设置为public
 * @date 2023/02/24
 */
@Component
class RdsStringTool {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 取字符串
     *
     * @param key
     * @return
     */
    public String getStr(String key) {
        Object value = get(key);
        return value == null ? null : String.valueOf(value);
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 普通缓存放入-不过期
     *
     * @param key   键
     * @param value 值
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value) {
        return set(key, value, 0, TimeUnit.SECONDS);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        return set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(指定的TimeUnit) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, timeUnit);
            } else {
                redisTemplate.opsForValue().set(key, value);
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
     * 普通缓存放入-不过期(只有在key不存在时设置key的值)
     *
     * @param key   键
     * @param value 值
     * @return true成功 false 失败
     */
    public boolean setIfAbsent(String key, Object value) {
        return setIfAbsent(key, value, 0, TimeUnit.SECONDS);
    }

    /**
     * 普通缓存放入并设置时间(只有在key不存在时设置key的值)
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean setIfAbsent(String key, Object value, long time) {
        return setIfAbsent(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 普通缓存放入并设置时间(只有在key不存在时设置key的值)
     *
     * @param key   键
     * @param value 值
     * @param time  时间(指定的TimeUnit) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
            } else {
                redisTemplate.opsForValue().setIfAbsent(key, value);
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
     * 递增 默认按 1 递增
     *
     * @param key 键
     * @return
     */
    public long incr(String key) {
        return incr(key, 1);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        try {
            if (delta < 0) {
                throw new RuntimeException("递增因子必须大于0");
            }
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 递减 按 1 递减
     *
     * @param key 键
     * @return
     */
    public long decr(String key) {
        return decr(key, 1);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        try {
            if (delta < 0) {
                throw new RuntimeException("递减因子必须大于0");
            }
            return redisTemplate.opsForValue().increment(key, -delta);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }
}
