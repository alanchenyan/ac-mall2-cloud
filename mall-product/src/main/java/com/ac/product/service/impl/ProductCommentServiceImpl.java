package com.ac.product.service.impl;

import com.ac.product.mongo.ProductComment;
import com.ac.product.service.ProductCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
