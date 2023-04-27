package com.ac.core.redis.util.tool;

import cn.hutool.core.collection.CollUtil;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Alan Chen
 * @description Redis-通用工具类
 * @date 2023/02/24
 */
@Component
public class RdsCommonTool {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 返回key集合
     *
     * @param key 键
     * @return
     */
    public Set<String> keys(String key) {
        try {
            return redisTemplate.keys(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 指定缓存失效时间(默认秒)
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        return expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time <= 0) {
                throw new RuntimeException("过期时间必须大于0");
            }
            return redisTemplate.expire(key, time, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 根据key 获取过期时间(TimeUnit.SECONDS)
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key, TimeUnit timeUnit) {
        try {
            return redisTemplate.getExpire(key, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }


    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public Boolean del(String... key) {
        try {
            if (key == null || key.length == 0) {
                throw new RuntimeException("key不能为空");
            }
            return redisTemplate.delete(CollectionUtils.arrayToList(key)) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 删除缓存
     *
     * @param keys key集合
     */
    public Long del(Collection<String> keys) {
        try {
            if (CollUtil.isEmpty(keys)) {
                throw new RuntimeException("key不能为空");
            }
            return redisTemplate.delete(keys);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
        return 0L;
    }
}
