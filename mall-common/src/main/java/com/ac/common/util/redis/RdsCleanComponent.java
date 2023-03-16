package com.ac.common.util.redis;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RdsCleanComponent {

    @Resource
    private RdsComponent rdsComponent;

    /**
     * 星号模糊匹配(星号替代后面所有字符,范围大)
     * key集合(多个用\",\"隔开, 支持末尾通配符*)
     *
     * @param keys
     * @return
     */
    public Set<String> clean(String keys) {
        /**
         * 测试样例：
         *  {qm}mem:base:1
         *  {qm}mem:base:count:1
         *
         * 参数：
         *  keys={qm}mem:base:*
         *
         *  删除数据：
         *  {qm}mem:base:1
         *  {qm}mem:base:count:1
         */
        Set<String> delKeySet = new LinkedHashSet<>();
        try {
            ArrayList<String> keyList = CollUtil.toList(keys.split(","));

            for (String key : keyList) {
                if (key.endsWith("*")) {
                    Set<String> keySet = rdsComponent.keys(key);
                    delKeySet.addAll(keySet);
                } else {
                    delKeySet.add(key);
                }
            }
            rdsComponent.del(delKeySet);
            delKeySet.forEach(key -> log.info("删除key: " + key));
            return delKeySet;
        } catch (Exception e) {
            log.info("RdsCleanUtil,删除Redis缓存失败");
            e.printStackTrace();
        }
        return delKeySet;
    }

    /**
     * 星号精确删除(星号匹配结尾字符,范围小)
     * key集合(多个用\",\"隔开, 支持末尾通配符*)
     *
     * @param keys
     * @return
     */
    public Set<String> cleanByMatch(String keys) {
        /**
         * 测试样例：
         *  {qm}mem:base:1
         *  {qm}mem:base:count:1
         *
         * 参数：
         *  keys={qm}mem:base:*
         *
         *  删除数据：
         *  {qm}mem:base:1
         */
        ArrayList<String> keyList = CollUtil.toList(keys.split(","));
        Set<String> delKeySet = new LinkedHashSet<>();
        for (String key : keyList) {
            if (key.endsWith("*")) {
                String keyPrefix = key.substring(0, key.lastIndexOf("*"));
                Set<String> keySet = rdsComponent.keys(key);
                keySet = keySet.stream().filter(a -> {
                    String substring = a.substring(0, a.lastIndexOf(":") + 1);
                    if (keyPrefix.equals(substring)) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toSet());
                delKeySet.addAll(keySet);
            } else {
                delKeySet.add(key);
            }
        }
        rdsComponent.del(delKeySet);
        delKeySet.forEach(key -> log.info("删除key: " + key));
        return delKeySet;
    }
}
