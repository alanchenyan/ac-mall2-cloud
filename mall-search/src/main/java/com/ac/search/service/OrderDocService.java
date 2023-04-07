package com.ac.search.service;

import com.ac.search.entity.OrderDoc;

import java.util.List;

public interface OrderDocService {

    /**
     * 删除index
     */
    boolean deleteIndex();

    /**
     * 保存
     *
     * @param doc
     */
    void save(OrderDoc doc);

    /**
     * 列表
     *
     * @return
     */
    List<OrderDoc> listAll();
}
