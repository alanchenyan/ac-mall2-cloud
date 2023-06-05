package com.ac.core.mongo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class MongoComponent {

    @Resource
    private MongoTemplate mongoTemplate;

    public long getTotal(Query query, String collectionName) {
        return mongoTemplate.count(query, collectionName);
    }

    public PageRequest pageRequest(MongoPage mongoPage) {
        if (StringUtils.isNotBlank(mongoPage.getSort())) {
            Sort sort = Sort.by(mongoPage.getSortType(), mongoPage.getSort());
            return PageRequest.of(mongoPage.getCurrentMinusOne(), mongoPage.getSize(), sort);
        }
        return PageRequest.of(mongoPage.getCurrentMinusOne(), mongoPage.getSize());
    }

    public <T> IPage<T> pageSearch(MongoPage mongoPage, Query query, Class<T> entityClass, String collectionName) {
        //分页参数
        PageRequest pageable = this.pageRequest(mongoPage);

        //总记录数
        long total = this.getTotal(query, collectionName);

        //当前页记录
        List list = mongoTemplate.find(query.with(pageable), entityClass, collectionName);

        IPage page = new Page(mongoPage.getCurrent(), mongoPage.getSize(), total);
        page.setRecords(list);
        return page;
    }
}
