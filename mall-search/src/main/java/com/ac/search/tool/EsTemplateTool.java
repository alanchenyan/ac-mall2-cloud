package com.ac.search.tool;

import cn.hutool.core.bean.BeanUtil;
import com.ac.search.qry.MultiSearchQry;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Alan Chen
 * @description ElasticsearchRestTemplate 工具类
 * @date 2023/4/8
 */
@Slf4j
@Component
public class EsTemplateTool {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public boolean createIndex(Class<?> clazz) {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(clazz);
        indexOperations.create();
        Document document = indexOperations.createMapping(clazz);
        indexOperations.putMapping(document);
        indexOperations.getSettings();
        return true;
    }

    public boolean deleteIndex(Class<?> clazz) {
        try {
            IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(clazz);
            if (indexOperations.exists()) {
                return indexOperations.delete();
            }
        } catch (Exception e) {
            log.error("删除index失败,clazzName={}", clazz.getSimpleName());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteIndex(String... indexNames) {
        try {
            IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexNames));
            if (indexOperations.exists()) {
                return indexOperations.delete();
            }
        } catch (Exception e) {
            log.error("删除index失败,clazzName={}", indexNames);
            e.printStackTrace();
        }
        return false;
    }

    public void updateDoc(Class<?> clazz, String docId, Object docObj) {
        Document document = Document.create();
        Map<String, Object> params = BeanUtil.beanToMap(docObj);
        document.putAll(params);

        UpdateQuery query = UpdateQuery.builder(docId).withDocument(document).build();
        elasticsearchRestTemplate.update(query, elasticsearchRestTemplate.getIndexCoordinatesFor(clazz));
    }

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
