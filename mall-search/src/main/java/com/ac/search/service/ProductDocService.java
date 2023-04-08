package com.ac.search.service;

import com.ac.search.dto.ProductHighlight;
import com.ac.search.entity.ProductDoc;

import java.util.List;

public interface ProductDocService {

    /**
     * 初始化index
     *
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
     * 精确查询
     *
     * @param keyword
     * @return
     */
    List<ProductDoc> listByTerm(String keyword);

    /**
     * 分词查询
     *
     * @param keyword
     * @return
     */
    List<ProductDoc> listByMatch(String keyword);

    /**
     * 分词查询-高亮显示
     *
     * @param keyword
     * @return
     */
    List<ProductHighlight> listByMatchHighlight(String keyword);
}
