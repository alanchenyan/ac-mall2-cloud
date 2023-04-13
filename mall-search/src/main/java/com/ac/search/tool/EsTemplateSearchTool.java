package com.ac.search.tool;

import com.ac.search.qry.MultiSearchQry;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
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

    public <T> List<T> termSearch(Class<T> clazz, MultiSearchQry qry) {
        QueryBuilder queryBuilder = new TermsQueryBuilder(qry.getFieldList().get(0), qry.getKeyword());
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
