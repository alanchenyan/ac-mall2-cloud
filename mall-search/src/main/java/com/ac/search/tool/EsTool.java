package com.ac.search.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class EsTool {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public boolean createIndex(String indexName, Object settings) {
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).create();
    }

    public boolean deleteIndex(Class<?> clazz) {
        return elasticsearchRestTemplate.indexOps(clazz).delete();
    }

    public boolean deleteIndex(String... indexNames) {
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexNames)).delete();
    }
}
