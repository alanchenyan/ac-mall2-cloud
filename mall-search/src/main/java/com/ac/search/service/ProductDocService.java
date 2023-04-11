package com.ac.search.service;

import com.ac.search.dto.ProductHighlightDTO;
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
     * 保存文档
     *
     * @param doc
     */
    void saveDoc(ProductDoc doc);

    /**
     * 修改文档
     *
     * @param doc
     */
    void updateDoc(ProductDoc doc);

    /**
     * 删除文档
     *
     * @param docId
     */
    void deleteDoc(String docId);

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
     * 多字段分词查询
     *
     * @param keyword
     * @return
     */
    List<ProductDoc> listByMultiMatch(String keyword);

    /**
     * 分词查询-高亮显示
     *
     * @param keyword
     * @return
     */
    List<ProductHighlightDTO> listByMatchHighlight(String keyword);
}
