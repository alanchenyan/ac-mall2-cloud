package com.ac.core.util.redis.tool;

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
     * GEO-添加成员经纬度
     *
     * @param key    键
     * @param member 成员
     * @param lng    经度
     * @param lat    纬度
     * @return 成功数量
     */
    public Long geoAdd(String key, Object member, double lng, double lat) {
        try {
            return redisTemplate.opsForGeo().add(key, new Point(lng, lat), member);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * GEO-获取成员经纬度
     *
     * @param key    键
     * @param member 成员
     * @return 经纬度
     */
    public Point geoPosition(String key, Object member) {
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
     * GEO-获取一批成员的经纬度
     *
     * @param key  键
     * @param list 成员列表
     * @return 经纬度列表
     */
    public List<Point> geoPositions(String key, List<Object> list) {
        try {
            return redisTemplate.opsForGeo().position(key, list.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * GEO-计算两个成员间的距离
     *
     * @param key     键
     * @param member1 成员1
     * @param member2 成员2
     * @return 距离
     */
    public Distance geoDistance(String key, Object member1, Object member2) {
        try {
            return redisTemplate.opsForGeo().distance(key, member1, member2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * GEO-计算两个成员间的距离
     *
     * @param key
     * @param member1
     * @param member2
     * @param metrics
     * @return
     */
    public Distance geoDistance(String key, Object member1, Object member2, Metrics metrics) {
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
     * GEO-获取指定成员周围的成员列表
     *
     * @param key
     * @param member
     * @param value
     * @param metrics
     * @return
     */
    public List<Object> geoRadius(String key, Object member, double value, Metrics metrics) {
        try {
            GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = redisTemplate.opsForGeo().radius(key, member, new Distance(value, metrics));
            List<Object> result = new ArrayList<>();
            for (GeoResult<RedisGeoCommands.GeoLocation<Object>> geoResult : geoResults.getContent()) {
                result.add(geoResult.getContent().getName());
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
