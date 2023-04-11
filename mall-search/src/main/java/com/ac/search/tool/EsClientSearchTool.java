package com.ac.search.tool;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.ac.search.factory.ElasticsearchFactory;
import com.ac.search.highlight.BaseHighlight;
import com.ac.search.highlight.HighlightFieldInfo;
import com.ac.search.qry.ListSearchQry;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sun.deploy.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alan Chen
 * @description RestHighLevelClient 搜索
 * @date 2023/4/8
 */
@Slf4j
@Component
public class EsClientSearchTool {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 精确匹配查询
     * 类比SQL:select * from indexName where field = 'keyword'
     *
     * @param clazz
     * @param qry
     * @param <T>
     * @return
     */
    public <T> List<T> termSearch(Class<T> clazz, ListSearchQry qry) {
        //参数校验
        checkParam(qry);

        SearchRequest request = new SearchRequest(qry.getIndexName());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(qry.getFieldList().get(0), qry.getKeyword());
        searchSourceBuilder.query(termQueryBuilder);
        request.source(searchSourceBuilder);
        log.info("DSL={}", searchSourceBuilder.toString());

        return doSearch(clazz, request);
    }

    /**
     * 词匹配查询
     * 类比SQL:select * from indexName where field in (被搜索字段的分词)
     *
     * @param clazz
     * @param qry
     * @param <T>
     * @return
     */
    public <T> List<T> matchSearch(Class<T> clazz, ListSearchQry qry) {
        SearchRequest request = new SearchRequest(qry.getIndexName());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(qry.getFieldList().get(0), qry.getKeyword());
        searchSourceBuilder.query(matchQueryBuilder);
        request.source(searchSourceBuilder);
        log.info("DSL:" + searchSourceBuilder.toString());

        return doSearch(clazz, request);
    }

    /**
     * 多字段分词匹配查询
     * 类比SQL:select * from indexName where field_1 in (被搜索字段的分词) or field_2 in (被搜索字段的分词)
     *
     * @param clazz
     * @param qry
     * @param <T>
     * @return
     */
    public <T> List<T> multiMatchSearch(Class<T> clazz, ListSearchQry qry) {
        String indexName = qry.getIndexName();
        List<String> fieldList = qry.getFieldList();
        String keyword = qry.getKeyword();
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, ArrayUtil.toArray(fieldList, String.class));
        searchSourceBuilder.query(multiMatchQueryBuilder);
        request.source(searchSourceBuilder);
        log.info("DSL:" + searchSourceBuilder.toString());

        return doSearch(clazz, request);
    }

    /**
     * 分词匹配查询-高亮显示
     * 类比SQL:select * from indexName where field in (被搜索字段的分词)
     *
     * @param clazz
     * @param qry
     * @param <T>
     * @return
     */
    public <T> List<T> matchSearchHighlight(Class<T> clazz, ListSearchQry qry) {
        SearchRequest request = new SearchRequest(qry.getIndexName());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(qry.getFieldList().get(0), qry.getKeyword());
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
            response = restHighLevelClient.search(request, EsClientOptions.OPTIONS);
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

            String text = "";
            Text[] textArray = filed.getFragments();
            if (textArray != null && textArray.length > 0) {
                List<String> textList = Arrays.asList(textArray).stream().map(Text::toString).collect(Collectors.toList());
                text = StringUtils.join(textList, ",");
            }

            HighlightFieldInfo info = HighlightFieldInfo.builder()
                    .name(filed.getName())
                    .fragment(filed.isFragment())
                    .text(text)
                    .build();
            highlightFields.put(entry.getKey(), info);
        }
        highlightObj.setHighlightFields(highlightFields);
    }

    /**
     * 参数校验
     *
     * @param qry
     */
    private void checkParam(ListSearchQry qry) {
        if (StrUtil.isEmpty(qry.getIndexName())) {
            throw new RuntimeException("索引名称不能为空");
        }

        if (StrUtil.isEmpty(qry.getKeyword())) {
            throw new RuntimeException("查询关键字不能为空");
        }

        if (CollectionUtil.isEmpty(qry.getFieldList())) {
            throw new RuntimeException("检索字段不能为空");
        }
    }
}
