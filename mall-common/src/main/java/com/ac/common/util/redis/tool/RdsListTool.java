package com.ac.common.util.redis.tool;

import com.ac.common.util.redis.tool.RdsCommonTool;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Alan Chen
 * @description Redis-List类型
 * @date 2023/02/24
 */
@Component
public class RdsListTool {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RdsCommonTool rdsCommonTool;

    /**
     * List-从左边压入元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lLeftPush(String key, Object value) {
        return lLeftPush(key, value, 0);
    }

    /**
     * List-从左边压入元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lLeftPush(String key, Object value, long time) {
        return lLeftPush(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * List-从左边压入元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lLeftPush(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
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
     * List-从右边压入元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lRightPush(String key, Object value) {
        return lRightPush(key, value, 0);
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
        return lRightPush(key, value, time, TimeUnit.SECONDS);
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
        try {
            redisTemplate.opsForList().rightPush(key, value);
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
     * List-从右边压入多个元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean rightPushAll(String key, List<Object> value) {
        return rightPushAll(key, value, 0);
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
        return rightPushAll(key, value, time, TimeUnit.SECONDS);
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
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
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
     * List-右边弹出（倒着取），左边压入
     *
     * @param sourceKey
     * @param destinationKey
     * @param limit
     * @return 弹出的元素
     */
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
     * List-从左边弹出元素
     *
     * @param key
     * @return
     */
    public Object lLeftPop(String key) {
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
     * List-从左边弹出多个元素
     *
     * @param key
     * @param limit
     * @return 弹出的元素
     */
    public List<Object> lLeftMultiPop(String key, int limit) {
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

    /**
     * List-获取List长度
     *
     * @param key
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
     * List-通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetByIndex(String key, long index) {
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
     * List-根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateByIndex(String key, long index, Object value) {
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
     * List-移除N个值为value的元素
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
