package com.ac.search.client.tool;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Alan Chen
 * @description RestHighLevelClient DDL
 * @date 2023/4/8
 */
@Slf4j
@Component
public class EsClientDdlTool {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private EsClientSetting esClientSetting;

    /**
     * 创建index
     *
     * @param indexName
     * @return
     */
    public boolean createIndexByJson(String indexName, String mappingPath) {
        CreateIndexRequest request = buildCreateIndexRequest(indexName);
        request.settings("/json/common-setting.json", XContentType.JSON);
        request.mapping(mappingPath, XContentType.JSON);
        return doCreateIndex(request);
    }

    /**
     * 创建index
     *
     * @param indexName
     * @param mapping
     * @return
     */
    public boolean createIndex(String indexName, XContentBuilder mapping) {
        CreateIndexRequest request = buildCreateIndexRequest(indexName);
        request.settings(esClientSetting.packageSetting());
        request.mapping(mapping);
        return doCreateIndex(request);
    }


    /**
     * 删除index
     *
     * @param indexName 索引名
     * @return
     */
    public boolean deleteIndex(String indexName) {
        try {
            //创建索引请求
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            //获取索引客户端
            IndicesClient indices = restHighLevelClient.indices();
            AcknowledgedResponse response = indices.delete(request, EsClientOptions.OPTIONS);
            log.info("是否所有节点都已确认请求: " + response.isAcknowledged());
            return true;
        } catch (Exception e) {
            log.error("删除index失败,indexName={}", indexName);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加文档
     *
     * @param indexName 索引名
     * @param docId     文档ID
     * @param docObj    文档对象
     * @return
     */
    public IndexResponse insertDoc(String indexName, String docId, Object docObj) {
        IndexRequest indexRequest = buildInsertDocRequest(indexName, docId, docObj);
        try {
            return restHighLevelClient.index(indexRequest, EsClientOptions.OPTIONS);
        } catch (Exception e) {
            log.error("插入文档失败,indexName={},docId={},docObj={}", indexName, docId, docObj);
            e.printStackTrace();
            throw new RuntimeException("插入文档失败");
        }
    }

    /**
     * 修改文档
     *
     * @param indexName
     * @param docId
     * @param docObj
     * @return
     */
    public UpdateResponse updateDoc(String indexName, String docId, Object docObj) {
        UpdateRequest request = buildUpdateDocRequest(indexName, docId, docObj);
        try {
            request.docAsUpsert(true);
            return restHighLevelClient.update(request, EsClientOptions.OPTIONS);
        } catch (Exception e) {
            log.error("修改文档失败,indexName={},docId={},docObj={}", indexName, docId, docObj);
            e.printStackTrace();
            throw new RuntimeException("修改文档失败");
        }
    }

    /**
     * 删除文档
     *
     * @param indexName
     * @param docId
     * @return
     */
    public DeleteResponse deleteDoc(String indexName, String docId) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, docId);
        try {
            return restHighLevelClient.delete(deleteRequest, EsClientOptions.OPTIONS);
        } catch (Exception e) {
            log.error("删除文档失败,indexName={},docId={}", indexName, docId);
            e.printStackTrace();
            throw new RuntimeException("删除文档失败");
        }
    }

    private boolean doCreateIndex(CreateIndexRequest request) {
        boolean is = false;
        try {
            IndicesClient indices = restHighLevelClient.indices();
            CreateIndexResponse response = indices.create(request, EsClientOptions.OPTIONS);
            log.info("是否所有节点都已确认请求: " + response.isAcknowledged());
            log.info("指示是否在超时之前为索引中的每个分片启动了必要数量的分片副本: " + response.isShardsAcknowledged());
            is = response.isAcknowledged();
        } catch (Exception e) {
            log.error("创建index失败");
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 构建CreateIndexRequest
     *
     * @param indexName
     * @return
     */
    private CreateIndexRequest buildCreateIndexRequest(String indexName) {
        //创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        //设置索引
        Settings.Builder builder = Settings.builder();
        builder.put("index.number_of_shards", 1);
        builder.put("index.number_of_replicas", 0);
        request.settings(builder);
        return request;
    }

    /**
     * 构建InsertDocRequest
     *
     * @param indexName 索引名
     * @param docId     文档ID
     * @param docObj    文档对象
     * @return
     */
    private IndexRequest buildInsertDocRequest(String indexName, String docId, Object docObj) {
        String jsonStr = JSON.toJSONString(docObj);
        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.id(docId);
        indexRequest.source(jsonStr, XContentType.JSON);
        return indexRequest;
    }

    /**
     * 构建UpdateDocRequest
     *
     * @param indexName
     * @param docId
     * @param docObj
     * @return
     */
    private UpdateRequest buildUpdateDocRequest(String indexName, String docId, Object docObj) {
        String jsonStr = JSON.toJSONString(docObj);
        UpdateRequest request = new UpdateRequest(indexName, docId);
        request.doc(jsonStr, XContentType.JSON);
        request.docAsUpsert(true);
        return request;
    }
}
