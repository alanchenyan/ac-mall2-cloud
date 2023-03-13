package com.ac.common.util.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Alan Chen
 * @description Redis-Set类型
 * 备注：该类主要是为了辅助RedisComponent完成功能，只希望RedisComponent能访问，因此该类没有设置为public
 * @date 2023/02/24
 */
@Component
class RdsSetTool {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RdsCommonTool rdsCommonTool;

    /**
     * Set-获取Set中的元素数
     *
     * @param key
     * @return
     */
    public Long sSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * Set-获取Set中的所有元素
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Set-随机弹出一个元素
     *
     * @param key 键
     * @return 弹出的元素
     */
    public Object sPop(String key) {
        try {
            return redisTemplate.opsForSet().pop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Set-存入多个元素
     *
     * @param key  键
     * @param list
     * @return 成功个数
     */
    public long sSetList(String key, List<Object> list) {
        return sSetList(key, list, 0);
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
        return sSetList(key, list, time, TimeUnit.SECONDS);
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
        try {
            Long count = redisTemplate.opsForSet().add(key, list.toArray());
            if (time > 0) {
                rdsCommonTool.expire(key, time, timeUnit);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * Set-批量存入多个元素
     *
     * @param key
     * @param list
     * @return
     */
    public boolean sPipeSetList(String key, List<Object> list) {
        try {
            redisTemplate.executePipelined(new SessionCallback<Object>() {
                @Override
                public <K, V> Map execute(RedisOperations<K, V> operations) throws DataAccessException {
                    if (CollectionUtils.isEmpty(list) || StringUtils.isEmpty(key)) {
                        return null;
                    }

                    SetOperations setOperations = operations.opsForSet();
                    for (Object value : list) {
                        setOperations.add(key, value);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
        return true;
    }

    /**
     * 命令判断成员元素是否是集合的成员。
     *
     * @param key   键
     * @param value 值
     * @return true or false
     */
    public boolean sIsMember(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 查询两个集合的交集
     *
     * @param key1 键1
     * @param key2 键2
     * @return 交集集合
     */
    public Set<Object> sInter(String key1, String key2) {
        try {
            return redisTemplate.opsForSet().intersect(key1, key2);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 查询两个集合的交集, 并存储于其他key上
     *
     * @param key1     键1
     * @param key2     键2
     * @param storeKey 存储key
     * @return 交集集合
     */
    public Long sInterAndStore(String key1, String key2, String storeKey) {
        try {
            return redisTemplate.opsForSet().intersectAndStore(key1, key2, storeKey);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * Set-移除值为value的元素
     *
     * @param key
     * @param value
     * @return 移除的个数
     */
    public long sSetRemove(String key, Object value) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, value);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * Set-移除多个元素
     *
     * @param key
     * @param list
     * @return 移除的个数
     */
    public long sSetRemove(String key, List<Object> list) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, list.toArray());
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }
}
