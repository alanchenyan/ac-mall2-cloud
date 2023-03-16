package com.ac.common.util.redis.tool;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Chen
 * @description Redis-GEO类型
 * @date 2023/02/24
 */
@Component
public class RdsGeoTool {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
}
