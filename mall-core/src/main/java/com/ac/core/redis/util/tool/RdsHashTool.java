package com.ac.core.redis.util.tool;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Alan Chen
 * @description Redis-Hash类型
 * @date 2023/02/24
 */
@Component
public class RdsHashTool {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RdsCommonTool rdsCommonTool;

    /**
     * Hash-取对象字段
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hGet(String key, String item) {

        try {
            return redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 获取hashKey对应的对象
     *
     * @param key 键
     * @return 对象
     */
    public <T> T hmGetObj(String key, Class<T> target) {
        try {
            Map<Object, Object> hashMap = redisTemplate.opsForHash().entries(key);
            if (hashMap.size() == 0) {
                return null;
            }
            return BeanUtil.toBean(hashMap, target);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 获取hashKey对应的对象
     *
     * @param key 键
     * @return 对象
     */
    public Boolean hmSetObj(String key, Object object) {
        Map<String, Object> map = BeanUtil.beanToMap(object);
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 存值
     *
     * @param key
     * @param object
     * @param time
     * @param timeUnit
     * @return
     */
    public Boolean hmSetObj(String key, Object object, long time, TimeUnit timeUnit) {
        Map<String, Object> map = BeanUtil.beanToMap(object);
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                rdsCommonTool.expire(key, time, timeUnit);
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
     * 获取hashKey对应的对象
     *
     * @param key 键
     * @return 对象
     */
    public Boolean hmSetObj(String key, Object object, long time) {
        return hmSetObj(key, object, time, TimeUnit.SECONDS);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmGet(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                rdsCommonTool.expire(key, time);
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
     * HashSet 并设置时间
     *
     * @param key
     * @param map
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean hmSet(String key, Map<String, Object> map, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                rdsCommonTool.expire(key, time, timeUnit);
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
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                rdsCommonTool.expire(key, time, timeUnit);
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
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hSet(String key, String item, Object value, long time) {
        return hSet(key, item, value, time, TimeUnit.SECONDS);

    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public Long hDel(String key, Object... item) {
        try {
            redisTemplate.opsForHash().delete(key, item);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
        return 0L;
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        try {
            return redisTemplate.opsForHash().hasKey(key, item);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 返回hash表字段长度
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     */
    public Long hLen(String key, String item) {
        try {
            return redisTemplate.opsForHash().lengthOfValue(key, item);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public long hIncr(String key, String item, long by) {
        try {
            return redisTemplate.opsForHash().increment(key, item, by);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hDecr(String key, String item, double by) {
        try {
            return redisTemplate.opsForHash().increment(key, item, -by);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }
}
