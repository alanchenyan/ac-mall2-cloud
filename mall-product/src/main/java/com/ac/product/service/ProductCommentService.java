package com.ac.product.service;

import com.ac.product.dto.ProductCommentDTO;
import com.ac.product.mongo.ProductComment;
import com.ac.product.qry.CommentQry;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface ProductCommentService {

    /**
     * 新增
     *
     * @param doc
     */
    String save(ProductComment doc);

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    IPage<ProductCommentDTO> page(CommentQry qry);

    /**
     * 分页查询2
     *
     * @param qry
     * @return
     */
    IPage<ProductCommentDTO> page2(CommentQry qry);
}
