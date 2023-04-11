package com.ac.search.tool;

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
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

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

    /**
     * 创建index
     * @param index
     * @param mapping
     * @return
     */
    public boolean createIndex(String index,XContentBuilder mapping) {
        XContentBuilder setting = packageSetting();
        return doCreateIndex(index, setting, mapping);
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
            DeleteIndexRequest request = buildDeleteIndexRequest(indexName);
            //获取索引客户端
            IndicesClient indices = restHighLevelClient.indices();
            AcknowledgedResponse response = indices.delete(request, EsClientOptions.OPTIONS);
            log.info("是否所有节点都已确认请求: " + response.isAcknowledged());
            return true;
        } catch (Exception e) {
            log.error("删除index失败,indexName={}",indexName);
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
            log.error("插入文档失败,indexName={},docId={},docObj={}",indexName,docId,docObj);
            e.printStackTrace();
            throw new RuntimeException("插入文档失败");
        }
    }

    /**
     * 修改文档
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
            log.error("修改文档失败,indexName={},docId={},docObj={}",indexName,docId,docObj);
            e.printStackTrace();
            throw new RuntimeException("修改文档失败");
        }
    }

    /**
     * 删除文档
     * @param indexName
     * @param docId
     * @return
     */
    public DeleteResponse deleteDoc(String indexName, String docId) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, docId);
        try {
            return restHighLevelClient.delete(deleteRequest, EsClientOptions.OPTIONS);
        } catch (Exception e) {
            log.error("删除文档失败,indexName={},docId={}",indexName,docId);
            e.printStackTrace();
            throw new RuntimeException("删除文档失败");
        }
    }

    private boolean doCreateIndex(String indexName, XContentBuilder settings, XContentBuilder mapping) {
        boolean is = false;
        try {
            CreateIndexRequest request = buildCreateIndexRequest(indexName);
            if (settings != null) {
                request.settings(settings);
            }
            if (mapping != null) {
                request.mapping(mapping);
            }
            IndicesClient indices = restHighLevelClient.indices();
            CreateIndexResponse response = indices.create(request, EsClientOptions.OPTIONS);
            log.info("是否所有节点都已确认请求: " + response.isAcknowledged());
            log.info("指示是否在超时之前为索引中的每个分片启动了必要数量的分片副本: " + response.isShardsAcknowledged());
            is = response.isAcknowledged();
        } catch (Exception e) {
            log.error("创建index失败,indexName={}",indexName);
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 构建CreateIndexRequest
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
     * 构建DeleteIndexRequest
     * @param indexName
     * @return
     */
    private DeleteIndexRequest buildDeleteIndexRequest(String indexName) {
        return new DeleteIndexRequest(indexName);
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

    /**
     * 创建索引setting
     * <p>
     * ngram分词器配置
     * ngram：英文单词按字母分词
     * field("filter","lowercase")：大小写兼容搜索
     * <p>
     * index.max_ngram_diff: 允许min_gram、max_gram的差值
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.8/analysis-ngram-tokenizer.html
     * <p>
     * normalizer:解决keyword区分大小写
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.0/normalizer.html
     * <p>
     * 拼音搜索
     * https://github.com/medcl/elasticsearch-analysis-pinyin
     * <p>
     * 简体繁华转换
     * https://github.com/medcl/elasticsearch-analysis-stconvert
     * <p>
     * 样例
     * https://blog.csdn.net/qq_39211866/article/details/85178707
     *
     * @return
     */
    private XContentBuilder packageSetting() {
        XContentBuilder setting = null;
        try {
            setting = XContentFactory.jsonBuilder()
                    .startObject()
                        .field("index.max_ngram_diff", "6")
                        .startObject("analysis")
                            .startObject("filter")
                                .startObject("edge_ngram_filter")
                                    .field("type", "edge_ngram")
                                    .field("min_gram", "1")
                                    .field("max_gram", "50")
                                .endObject()

                                .startObject("pinyin_edge_ngram_filter")
                                    .field("type", "edge_ngram")
                                    .field("min_gram", 1)
                                    .field("max_gram", 50)
                                .endObject()

                                //简拼
                                .startObject("pinyin_simple_filter")
                                    .field("type", "pinyin")
                                    .field("keep_first_letter", true)
                                    .field("keep_separate_first_letter", false)
                                    .field("keep_full_pinyin", false)
                                    .field("keep_original", false)
                                    .field("limit_first_letter_length", 50)
                                    .field("lowercase", true)
                                .endObject()

                                //全拼
                                .startObject("pinyin_full_filter")
                                    .field("type", "pinyin")
                                    .field("keep_first_letter", false)
                                    .field("keep_separate_first_letter", false)
                                    .field("keep_full_pinyin", true)
                                    .field("keep_original", false)
                                    .field("limit_first_letter_length", 50)
                                    .field("lowercase", true)
                                .endObject()

                            .endObject()

                            //简2繁
                            .startObject("char_filter")
                                .startObject("tsconvert")
                                    .field("type", "stconvert")
                                    .field("convert_type", "t2s")
                                .endObject()
                            .endObject()

                            .startObject("analyzer")
                                //模糊搜索、忽略大小写(按字符切分)
                                .startObject("ngram")
                                    .field("tokenizer", "my_tokenizer")
                                    .field("filter", "lowercase")
                                .endObject()

                                //ik+简体、繁体转换(ik最小切分)-用于查询关键字分词
                                .startObject("ikSmartAnalyzer")
                                    .field("type", "custom")
                                    .field("tokenizer", "ik_smart")
                                    .field("char_filter", "tsconvert")
                                .endObject()

                                //ik+简体、繁体转换(ik最大切分)-用于文档存储
                                .startObject("ikMaxAnalyzer")
                                    .field("type", "custom")
                                    .field("tokenizer", "ik_max_word")
                                    .field("char_filter", "tsconvert")
                                .endObject()

                                //简拼搜索
                                .startObject("pinyinSimpleIndexAnalyzer")
                                    .field("type", "custom")
                                    .field("tokenizer", "keyword")
                                    .array("filter", "pinyin_simple_filter", "pinyin_edge_ngram_filter", "lowercase")
                                .endObject()

                                //全拼搜索
                                .startObject("pinyinFullIndexAnalyzer")
                                    .field("type", "custom")
                                    .field("tokenizer", "keyword")
                                    .array("filter", "pinyin_full_filter", "lowercase")
                                .endObject()
                            .endObject()

                            .startObject("tokenizer")
                                .startObject("my_tokenizer")
                                    .field("type", "ngram")
                                    .field("min_gram", "1")
                                    .field("max_gram", "6")
                                .endObject()
                            .endObject()

                            .startObject("normalizer")
                                .startObject("lowercase")
                                    .field("type", "custom")
                                    .field("filter", "lowercase")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
        } catch (Exception e) {
            log.error("setting构建失败");
            e.printStackTrace();
        }
        return setting;
    }
}
