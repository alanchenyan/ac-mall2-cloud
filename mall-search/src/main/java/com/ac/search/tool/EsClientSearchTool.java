package com.ac.search.tool;

import com.ac.search.factory.ElasticsearchFactory;
import com.ac.search.highlight.BaseHighlight;
import com.ac.search.highlight.HighlightFieldInfo;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EsClientSearchTool {

    @Resource
    protected RestHighLevelClient restHighLevelClient;

    /**
     * 精确匹配查询
     * 类比SQL:select * from indexName where field = 'keyword'
     *
     * @param clazz
     * @param indexName
     * @param field
     * @param keyword
     * @return
     */
    public <T> List<T> termSearch(Class<T> clazz, String indexName, String field, String keyword) {
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(field, keyword);
        searchSourceBuilder.query(termQueryBuilder);
        request.source(searchSourceBuilder);
        log.info("DSL={}", searchSourceBuilder.toString());

        return doSearch(clazz, request);
    }

    /**
     * 分词匹配查询
     * 类比SQL:select * from indexName where field in (被搜索字段的分词)
     *
     * @param clazz
     * @param index
     * @param field
     * @param keyword
     * @param <T>
     * @return
     */
    public <T> List<T> matchSearch(Class<T> clazz, String index, String field, String keyword) {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(field, keyword);
        searchSourceBuilder.query(matchQueryBuilder);
        request.source(searchSourceBuilder);
        log.info("DSL:" + searchSourceBuilder.toString());

        return doSearch(clazz, request);
    }

    /**
     * 分词匹配查询-高亮显示
     * 类比SQL:select * from indexName where field in (被搜索字段的分词)
     *
     * @param clazz
     * @param index
     * @param field
     * @param keyword
     * @param <T>
     * @return
     */
    public <T> List<T> matchSearchHighlight(Class<T> clazz, String index, String field, String keyword) {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(field, keyword);
        searchSourceBuilder.query(matchQueryBuilder);
        HighlightBuilder highlightBuilder = ElasticsearchFactory.builderHighlight();
        searchSourceBuilder.highlighter(highlightBuilder);
        request.source(searchSourceBuilder);
        log.info("DSL:" + searchSourceBuilder.toString());

        return doSearch(clazz, request);
    }

    /**
     * 执行搜索
     *
     * @param clazz
     * @param request
     * @param <T>
     * @return
     */
    private <T> List<T> doSearch(Class<T> clazz, SearchRequest request) {
        List<T> resultList = Lists.newArrayList();
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean isHighlight = false;
        //判断clazz是否为BaseHighlight的子类
        if (BaseHighlight.class.isAssignableFrom(clazz)) {
            isHighlight = true;
        }

        for (SearchHit hit : response.getHits()) {
            String resultString = hit.getSourceAsString();
            T obj = JSONObject.parseObject(resultString, clazz);
            this.dealHighlightField(isHighlight, obj, hit);
            resultList.add(obj);
        }
        return resultList;
    }

    /**
     * 处理高亮字段
     *
     * @param isHighlight
     * @param obj
     * @param hit
     */
    private void dealHighlightField(boolean isHighlight, Object obj, SearchHit hit) {
        if (isHighlight == false) {
            return;
        }
        BaseHighlight highlightObj = (BaseHighlight) obj;

        Map<String, HighlightField> fieldsMap = hit.getHighlightFields();
        Map<String, HighlightFieldInfo> highlightFields = new HashMap<>(fieldsMap.size());
        for (Map.Entry<String, HighlightField> entry : fieldsMap.entrySet()) {
            HighlightField filed = entry.getValue();
            HighlightFieldInfo info = HighlightFieldInfo.builder()
                    .name(filed.getName())
                    .fragment(filed.isFragment())
                    .text(filed.toString())
                    .build();
            highlightFields.put(entry.getKey(), info);
        }
        highlightObj.setHighlightFields(highlightFields);
    }
}
