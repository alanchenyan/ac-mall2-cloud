package com.ac.search.template.service;

import com.ac.search.template.entity.OrderDoc;

import java.util.List;

public interface OrderDocService {

    /**
     * 初始化index
     *
     * @return
     */
    boolean initIndex();

    /**
     * 创建index
     *
     * @return
     */
    boolean createIndex();

    /**
     * 删除index
     */
    boolean deleteIndex();

    /**
     * 保存
     *
     * @param doc
     */
    void saveDoc(OrderDoc doc);

    /**
     * 修改文档
     *
     * @param doc
     */
    void updateDoc(OrderDoc doc);

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
    List<OrderDoc> listByTerm(String keyword);

    /**
     * 分词查询
     *
     * @param keyword
     * @return
     */
    List<OrderDoc> listByMatch(String keyword);

    /**
     * 列表
     *
     * @return
     */
    List<OrderDoc> listAll();
}
