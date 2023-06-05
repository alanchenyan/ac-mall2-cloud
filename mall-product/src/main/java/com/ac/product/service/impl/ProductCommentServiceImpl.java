package com.ac.product.service.impl;

import com.ac.product.dto.ProductCommentDTO;
import com.ac.product.mongo.ProductComment;
import com.ac.product.qry.CommentQry;
import com.ac.product.service.ProductCommentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public String save(ProductComment doc) {
        mongoTemplate.save(doc);
        return doc.getId().toString();
    }

    @Override
    public IPage<ProductCommentDTO> page(CommentQry qry) {
        PageRequest pageable = PageRequest.of(qry.getCurrentMinusOne(), qry.getSize());
        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(qry.getProductId()));

        long total = getTotal(query, "product_comment");
        List<ProductCommentDTO> list = mongoTemplate.find(query.with(pageable), ProductCommentDTO.class, "product_comment");

        IPage<ProductCommentDTO> page = new Page<>(qry.getCurrent(), qry.getSize(), total);
        page.setRecords(list);
        return page;
    }

    private long getTotal(Query query, String collectionName) {
        return mongoTemplate.count(query, collectionName);
    }
}
