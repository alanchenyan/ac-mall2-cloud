package com.ac.core.mongo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class MongoComponent {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 分页查询
     *
     * @param mongoPage
     * @param query
     * @param entityClass
     * @param collectionName
     * @param <T>
     * @return
     */
    public <T> IPage<T> pageSearch(MongoPage mongoPage, Query query, Class<T> entityClass, String collectionName) {
        //分页参数
        PageRequest pageable = this.pageRequest(mongoPage);

        //总记录数(不用带分页参数)
        long total = this.getTotal(query, collectionName);

        //当前页记录(带分页参数)
        List list = mongoTemplate.find(query.with(pageable), entityClass, collectionName);

        //分页
        return fillPage(mongoPage.getCurrent(), mongoPage.getSize(), total, list);
    }

    public <T> IPage<T> pageSearch(MongoPage mongoPage, List<AggregationOperation> operations, Class<T> entityClass, String collectionName) {
        //分页参数
        PageRequest pageable = this.pageRequest(mongoPage);

        //总记录数(不用带分页参数)
        long total = this.getTotal(operations, "product_comment", entityClass);

        //当前页记录(带分页参数)
        Aggregation aggregation = Aggregation.newAggregation(operations);

        AggregationResults results = mongoTemplate.aggregate(aggregation, "product_comment", entityClass);
        List list = results.getMappedResults();

        //分页
        return fillPage(mongoPage.getCurrent(), mongoPage.getSize(), total, list);
    }

    /**
     * 查总记录数
     *
     * @param operations
     * @param collectionName
     * @param outputType
     * @return
     */
    public long getTotal(List<AggregationOperation> operations, String collectionName, Class outputType) {
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults result = mongoTemplate.aggregate(aggregation, collectionName, outputType);
        return result.getMappedResults().size();
    }

    /**
     * 查总记录数
     *
     * @param query
     * @param collectionName
     * @return
     */
    public long getTotal(Query query, String collectionName) {
        return mongoTemplate.count(query, collectionName);
    }

    /**
     * 组装分页参数
     *
     * @param mongoPage
     * @return
     */
    public PageRequest pageRequest(MongoPage mongoPage) {
        if (StringUtils.isNotBlank(mongoPage.getSort())) {
            Sort sort = Sort.by(mongoPage.getSortType(), mongoPage.getSort());
            return PageRequest.of(mongoPage.getCurrentMinusOne(), mongoPage.getSize(), sort);
        }
        return PageRequest.of(mongoPage.getCurrentMinusOne(), mongoPage.getSize());
    }

    /**
     * 分页对象
     *
     * @param current
     * @param size
     * @param total
     * @param records
     * @return
     */
    private IPage fillPage(long current, long size, long total, List records) {
        return new Page(current, size, total).setRecords(records);
    }
}
