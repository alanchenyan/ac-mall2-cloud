package com.ac.search.service;

import com.ac.search.entity.ProductDoc;

import java.util.List;

public interface ProductService {

    /**
     * 删除index
     */
    boolean deleteIndex();

    /**
     * 保存
     *
     * @param doc
     */
    void save(ProductDoc doc);

    /**
     * 列表
     *
     * @return
     */
    List<ProductDoc> listAll();
}
