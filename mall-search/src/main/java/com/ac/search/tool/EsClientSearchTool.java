package com.ac.search.tool;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.ac.common.enums.OrderTypeEnum;
import com.ac.common.page.EsPage;
import com.ac.search.factory.ElasticsearchFactory;
import com.ac.search.highlight.BaseHighlight;
import com.ac.search.highlight.HighlightFieldInfo;
import com.ac.search.qry.GeoSearchQry;
import com.ac.search.qry.ListSearchQry;
import com.ac.search.qry.PageSearchQry;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sun.deploy.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
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

        return doSearchList(clazz, request);
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

        return doSearchList(clazz, request);
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

        return doSearchList(clazz, request);
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

        return doSearchList(clazz, request);
    }

    /**
     * GEO地理位置查询
     *
     * @param clazz
     * @param qry
     * @param <T>
     * @return
     */
    public <T> List<T> geoSearch(Class<T> clazz, GeoSearchQry qry) {
        String indexName = qry.getIndexName();
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        GeoDistanceQueryBuilder builder = QueryBuilders.geoDistanceQuery(qry.getGeoPointField())//查询字段
                .point(qry.getLat(), qry.getLon())//设置经纬度
                .distance(qry.getDistance(), qry.getDistanceUnit())//设置距离和单位
                .geoDistance(GeoDistance.ARC);

        searchSourceBuilder.query(builder);

        GeoDistanceSortBuilder distanceSortBuilder = new GeoDistanceSortBuilder(qry.getGeoPointField(), qry.getLat(), qry.getLon());
        distanceSortBuilder.unit(qry.getDistanceUnit());
        distanceSortBuilder.order(SortOrder.ASC);
        searchSourceBuilder.sort(distanceSortBuilder);
        request.source(searchSourceBuilder);
        log.info("DSL:" + searchSourceBuilder.toString());

        return doSearchList(clazz, request);
    }

    /**
     * 分页-搜索
     *
     * @param clazz
     * @param qry
     * @param <T>
     * @return
     */
    public <T> EsPage<T> pageSearch(Class<T> clazz, PageSearchQry qry) {
        Integer pageNo = qry.getCurrent();
        Integer pageSize = qry.getSize();

        SearchRequest request = new SearchRequest(qry.getIndexName());
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //分页参数
        Integer from = (pageNo - 1) * pageSize;
        builder.from(from);
        builder.size(pageSize);

        if (StrUtil.isBlank(qry.getKeyword()) && StrUtil.isBlank(qry.getTermField())) {
            //查询所有
            searchAll(builder);
        } else {
            //按搜索条件搜索
            pageSearchByCondition(builder, qry);
        }

        //排序
        dealSort(builder, qry.getOrderField(), qry.getOrderType());

        request.source(builder);
        log.info("DSL:" + builder.toString());

        return doSearchPage(clazz, request, qry);
    }


    /**
     * 搜索-分页
     *
     * @param clazz
     * @param request
     * @param <T>
     * @return
     */
    private <T> EsPage<T> doSearchPage(Class<T> clazz, SearchRequest request, PageSearchQry qry) {
        EsPage<T> esPage = new EsPage();

        SearchResponse response = doSearch(request);
        List<T> records = fillList(clazz, response);
        esPage.setRecords(records);

        // 处理分页结果
        long total = response.getHits().getTotalHits().value;

        //其中Math.ceil()函数用于向上取整，确保总页数能够覆盖所有记录
        long pages = (int) Math.ceil((double) total / qry.getSize());
        esPage.setTotal(total);
        esPage.setPages(pages);
        esPage.setCurrent(qry.getCurrent());
        esPage.setSize(qry.getSize());

        return esPage;
    }

    /**
     * 搜索-列表
     *
     * @param clazz
     * @param request
     * @param <T>
     * @return
     */
    private <T> List<T> doSearchList(Class<T> clazz, SearchRequest request) {
        SearchResponse response = doSearch(request);
        return fillList(clazz, response);
    }

    /**
     * 执行搜索
     *
     * @param request
     * @return
     */
    private SearchResponse doSearch(SearchRequest request) {
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, EsClientOptions.OPTIONS);
        } catch (IOException e) {
            log.error("查询失败");
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 排序
     *
     * @param builder
     * @param orderField
     * @param orderType
     */
    private void dealSort(SearchSourceBuilder builder, String orderField, OrderTypeEnum orderType) {
        if (StrUtil.isNotBlank(orderField) && orderType != null) {
            FieldSortBuilder order = new FieldSortBuilder(orderField);
            if (OrderTypeEnum.ASC == orderType) {
                order.order(SortOrder.ASC);
            } else {
                order.order(SortOrder.DESC);
            }
            builder.sort(order);
        }
    }

    /**
     * 查询所有
     *
     * @param builder
     */
    private void searchAll(SearchSourceBuilder builder) {
        QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
        builder.query(matchAllQuery);
    }


    /**
     * 按搜索条件搜索
     *
     * @param builder
     * @param qry
     */
    private void pageSearchByCondition(SearchSourceBuilder builder, PageSearchQry qry) {
        String keyword = qry.getKeyword();
        List<String> fieldList = qry.getFieldList();
        List<String> fieldUnSplitList = qry.getFieldUnSplitList();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //精确匹配查询
        if (StrUtil.isNotBlank(qry.getTermField())) {
            if (StrUtil.isBlank(qry.getTermValue())) {
                throw new RuntimeException("termValue值不能为空");
            }
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(qry.getTermField(), qry.getTermValue());
            boolQueryBuilder.must(termQueryBuilder);
        }

        BoolQueryBuilder boolShouldQueryBuilder = QueryBuilders.boolQuery();
        //检索字段-不分词
        if (CollectionUtil.isNotEmpty(fieldUnSplitList)) {
            MultiMatchQueryBuilder unSplitMultiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, ArrayUtil.toArray(fieldUnSplitList, String.class));
            unSplitMultiMatchQueryBuilder.operator(Operator.AND);
            boolShouldQueryBuilder.should(unSplitMultiMatchQueryBuilder);
        }

        //检索字段-分词
        if (CollectionUtil.isNotEmpty(fieldList)) {
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, ArrayUtil.toArray(fieldList, String.class));
            multiMatchQueryBuilder.operator(Operator.OR);
            boolShouldQueryBuilder.should(multiMatchQueryBuilder);
        }
        boolQueryBuilder.must(boolShouldQueryBuilder);
        builder.query(boolQueryBuilder);
    }

    /**
     * 组装list
     *
     * @param clazz
     * @param response
     * @param <T>
     * @return
     */
    private <T> List<T> fillList(Class<T> clazz, SearchResponse response) {
        List<T> records = Lists.newArrayList();

        boolean isHighlight = false;
        //判断clazz是否为BaseHighlight的子类
        if (BaseHighlight.class.isAssignableFrom(clazz)) {
            isHighlight = true;
        }

        for (SearchHit hit : response.getHits()) {
            String resultString = hit.getSourceAsString();
            T obj = JSONObject.parseObject(resultString, clazz);
            this.dealHighlightField(isHighlight, obj, hit);
            records.add(obj);
        }
        return records;
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
