package com.ac.product.service.impl;

import com.ac.core.mongo.MongoComponent;
import com.ac.product.dto.ProductCommentDTO;
import com.ac.product.mongo.ProductComment;
import com.ac.product.qry.CommentQry;
import com.ac.product.service.ProductCommentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    @Resource
    private MongoComponent mongoComponent;

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public String save(ProductComment doc) {
        mongoTemplate.save(doc);
        return doc.getId().toString();
    }

    @Override
    public IPage<ProductCommentDTO> page(CommentQry qry) {
        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(qry.getProductId()));
        if (qry.getMemberId() != null) {
            query.addCriteria(Criteria.where("memberId").is(qry.getMemberId()));
        }

        return mongoComponent.pageSearch(qry, query, ProductCommentDTO.class, "product_comment");
    }

    @Override
    public IPage<ProductCommentDTO> page2(CommentQry qry) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(Criteria.where("productId").is(qry.getProductId())));
        if (qry.getMemberId() != null) {
            operations.add(Aggregation.match(Criteria.where("memberId").is(qry.getMemberId())));
        }
        return mongoComponent.pageSearch(qry, operations, ProductCommentDTO.class, "product_comment");
    }
}
