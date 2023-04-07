package com.ac.search.service;

import com.ac.search.entity.ProductDoc;

import java.util.List;

public interface ProductDocService {

    /**
     * 初始化index
     * @return
     */
    boolean initIndex();

    /**
     * 删除index
     */
    boolean deleteIndex();

    /**
     * 创建index
     *
     * @return
     */
    boolean createIndex();

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
