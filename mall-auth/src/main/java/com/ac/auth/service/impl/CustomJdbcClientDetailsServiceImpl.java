package com.ac.auth.service.impl;

import com.ac.core.util.redis.RdsComponent;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * 类说明
 * 将oauth_client_details表数据缓存到redis，这里做个缓存优化
 * 注意同步redis的数据
 * 注意对oauth_client_details清楚redis db部分数据的清空
 */
@Slf4j
@Service
public class CustomJdbcClientDetailsServiceImpl extends JdbcClientDetailsService {

    @Resource
    private RdsComponent rdsComponent;

    public CustomJdbcClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        // 先从redis获取
        Object client = rdsComponent.get(clientRedisKey(clientId));
        ClientDetails clientDetails = null;
        if (clientDetails == null) {
            clientDetails = cacheAndGetClient(clientId);
        }
        return clientDetails;
    }

    /**
     * 缓存client并返回client
     *
     * @param clientId
     * @return
     */
    private ClientDetails cacheAndGetClient(String clientId) {
        // 从数据库读取
        ClientDetails clientDetails = null;
        try {
            clientDetails = super.loadClientByClientId(clientId);
            if (clientDetails != null) {
                // 写入redis缓存
                rdsComponent.set(clientRedisKey(clientId), JSONObject.toJSONString(clientDetails), -1);
                log.info("缓存clientId:{},{}", clientId, clientDetails);
            }
        } catch (NoSuchClientException e) {
            log.error("clientId:{},{}", clientId, clientId);
        } catch (InvalidClientException e) {
            log.error("cacheAndGetClient-invalidClient:{}", clientId, e);
        }
        return clientDetails;
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) {
        super.updateClientDetails(clientDetails);
        cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientSecret(String clientId, String secret) {
        super.updateClientSecret(clientId, secret);
        cacheAndGetClient(clientId);
    }

    @Override
    public void removeClientDetails(String clientId) {
        super.removeClientDetails(clientId);
        removeRedisCache(clientId);
    }

    /**
     * 删除redis缓存
     *
     * @param clientId
     */
    private void removeRedisCache(String clientId) {
        rdsComponent.del(clientRedisKey(clientId));
    }

    /**
     * 将oauth_client_details全表刷入redis
     */
    public void loadAllClientToCache() {
        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }

        list.parallelStream().forEach(client -> rdsComponent.set(clientRedisKey(client.getClientId()), JSONObject.toJSONString(client), -1));
    }

    private String clientRedisKey(String clientId) {
        return "oauth_client_details" + ":" + clientId;
    }
}
