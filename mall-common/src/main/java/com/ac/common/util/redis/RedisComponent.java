package com.ac.common.util.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
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
    private RdsStringTool rdsStringTool;

    //============================第一部分：String start=============================

    public String getStr(String key) {
        return rdsStringTool.getStr(key);
    }

    public Object get(String key) {
        return rdsStringTool.get(key);
    }

    public boolean set(String key, Object value) {
        return rdsStringTool.set(key, value);
    }

    public boolean set(String key, Object value, long time) {
        return rdsStringTool.set(key, value, time);
    }

    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        return rdsStringTool.set(key, value, time, timeUnit);
    }

    public boolean setIfAbsent(String key, Object value) {
        return rdsStringTool.setIfAbsent(key, value);
    }

    public boolean setIfAbsent(String key, Object value, long time) {
        return rdsStringTool.setIfAbsent(key, value, time);
    }

    public boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit) {
        return rdsStringTool.setIfAbsent(key, value, time, timeUnit);
    }

    public long incr(String key) {
        return rdsStringTool.incr(key);
    }

    public long incr(String key, long delta) {
        return rdsStringTool.incr(key, delta);
    }

    public long decr(String key) {
        return rdsStringTool.decr(key);
    }

    public long decr(String key, long delta) {
        return rdsStringTool.decr(key, delta);
    }

    //============================第一部分：String end=============================






    /**
     * 返回key集合
     *
     * @param key 键
     * @return
     */
    public boolean hasKeys(String key) {
        return keys(key).size() > 0;
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
     * 指定缓存失效时间(默认分钟)
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
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
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
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

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
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public Boolean del(String... key) {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    return redisTemplate.delete(key[0]);
                } else {
                    return redisTemplate.delete(CollectionUtils.arrayToList(key)) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
        return false;
    }

    /**
     * 删除缓存
     *
     * @param keys key集合
     */
    @SuppressWarnings("unchecked")
    public Long del(Collection<String> keys) {
        try {
            if (CollUtil.isNotEmpty(keys)) {
                return redisTemplate.delete(keys);
            }
            return 0L;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
        return 0L;
    }


    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {

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
    public <T> T hmgetObj(String key, Class<T> target) {
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
    public Boolean hmsetObj(String key, Object object) {
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
    public Boolean hmsetObj(String key, Object object, long time, TimeUnit timeUnit) {
        Map<String, Object> map = BeanUtil.beanToMap(object);
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time, timeUnit);
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
    public Boolean hmsetObj(String key, Object object, long time) {
        return hmsetObj(key, object, time, TimeUnit.SECONDS);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
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
    public boolean hmset(String key, Map<String, Object> map) {
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
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
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
    public boolean hset(String key, String item, Object value) {
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
    public boolean hset(String key, String item, Object value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time, timeUnit);
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
    public boolean hset(String key, String item, Object value, long time) {
        return hset(key, item, value, time, TimeUnit.SECONDS);

    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public Long hdel(String key, Object... item) {
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
     * 返回hash表字段数量
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
    public long hincr(String key, String item, long by) {
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
    public double hdecr(String key, String item, double by) {
        try {
            return redisTemplate.opsForHash().increment(key, item, -by);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    //============================set=============================

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
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
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
     * @param datas
     * @return
     */
    public Boolean sSetPipe(String key, List<Object> datas) {
        try {
            redisTemplate.executePipelined(new SessionCallback<Object>() {
                @Override
                public <K, V> Map execute(RedisOperations<K, V> operations) throws DataAccessException {
                    if (CollectionUtils.isEmpty(datas) || StringUtils.isEmpty(key)) {
                        return null;
                    }

                    SetOperations setOperations = operations.opsForSet();
                    for (Object value : datas) {
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
    public Boolean sIsMember(String key, Object value) {
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
                expire(key, time, timeUnit);
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
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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
     * 移除值为value的
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
     * 移除值为value的
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

    public List<Object> lrightPopLeftPush(String sourceKey, String destinationKey, int limit) {
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
                expire(key, time);
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
                expire(key, time);
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
