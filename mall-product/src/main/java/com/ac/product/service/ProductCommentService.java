package com.ac.product.service;

import com.ac.product.mongo.ProductComment;

public interface ProductCommentService {

    /**
     * 新增
     *
     * @param doc
     */
    String save(ProductComment doc);
}
