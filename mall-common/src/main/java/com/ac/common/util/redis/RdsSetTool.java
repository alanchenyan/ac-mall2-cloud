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
     * 根据key获取Set中的成员数
     *
     * @param key 键
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
     * 根据key获取Set中的所有值
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
     * 根据key随机弹出t中的值
     *
     * @param key 键
     * @return
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
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 管道批量插入数据
     *
     * @param key
     * @param list
     * @return
     */
    public boolean sSetPipe(String key, List<Object> list) {
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
     * 将set数据放入缓存
     *
     * @param key      键
     * @param time     时间
     * @param timeUnit 时间单位
     * @param values   值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, TimeUnit timeUnit, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
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
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        return sSetAndTime(key, time, TimeUnit.SECONDS, values);
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
     * 移除值为value的元素
     *
     * @param key   键
     * @param value 值 可以是多个
     * @return 移除的个数
     */
    public long sSetRemove(String key, Long value) {
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
     * 移除多个元素
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long sSetRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    public List<Object> rightPopAndLeftPush(String sourceKey, String destinationKey, int limit) {
        try {
            return redisTemplate.executePipelined(new SessionCallback<Object>() {
                @Override
                public <K, V> Map execute(RedisOperations<K, V> operations) throws DataAccessException {

                    ListOperations listOperations = operations.opsForList();
                    for (long i = 0; i < limit; i++) {
                        listOperations.rightPopAndLeftPush(sourceKey, destinationKey);
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

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param limit 长度
     * @return
     */

    public List<Object> lMultiPop(String key, int limit) {
        try {
            return redisTemplate.executePipelined(new SessionCallback<Object>() {
                @Override
                public <K, V> Map execute(RedisOperations<K, V> operations) throws DataAccessException {

                    ListOperations listOperations = operations.opsForList();
                    for (long i = 0; i < limit; i++) {
                        listOperations.leftPop(key);
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

    public Object lPop(String key) {
        try {
            if (lGetListSize(key) == 0) {
                return null;
            }
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }


    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
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
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSetAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
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
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }
}
