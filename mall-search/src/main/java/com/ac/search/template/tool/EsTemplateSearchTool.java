package com.ac.search.template.tool;

import com.ac.search.common.qry.OneFieldSearchQry;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Alan Chen
 * @description ElasticsearchRestTemplate 工具类
 * @date 2023/4/8
 */
@Slf4j
@Component
public class EsTemplateSearchTool {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 精确匹配查询
     * 类比SQL:select * from indexName where field = 'keyword'
     *
     * @param clazz
     * @param qry
     * @param <T>
     * @return
     */
    public <T> List<T> termSearch(Class<T> clazz, OneFieldSearchQry qry) {
        QueryBuilder queryBuilder = new TermsQueryBuilder(qry.getField(), qry.getKeyword());
        log.info("DSL={}", queryBuilder.toString());

        NativeSearchQuery searchQuery = new NativeSearchQuery(queryBuilder);
        SearchHits searchHits = elasticsearchRestTemplate.search(searchQuery, clazz);

        List<T> records = Lists.newArrayList();
        List<SearchHit<T>> hitList = searchHits.getSearchHits();
        for (SearchHit<T> hit : hitList) {
            records.add(hit.getContent());
        }
        return records;
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
    public <T> List<T> matchSearch(Class<T> clazz, OneFieldSearchQry qry) {
        QueryBuilder queryBuilder = new MatchQueryBuilder(qry.getField(), qry.getKeyword());
        log.info("DSL={}", queryBuilder.toString());

        NativeSearchQuery searchQuery = new NativeSearchQuery(queryBuilder);
        SearchHits searchHits = elasticsearchRestTemplate.search(searchQuery, clazz);

        List<T> records = Lists.newArrayList();
        List<SearchHit<T>> hitList = searchHits.getSearchHits();
        for (SearchHit<T> hit : hitList) {
            records.add(hit.getContent());
        }
        return records;
    }
}
