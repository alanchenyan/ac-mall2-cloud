package com.ac.search.service.impl;

import com.ac.search.dao.OrderDocDao;
import com.ac.search.entity.OrderDoc;
import com.ac.search.entity.ProductDoc;
import com.ac.search.service.OrderDocService;
import com.ac.search.tool.EsTemplateTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderDocServiceImpl implements OrderDocService {

    @Resource
    private OrderDocDao orderDocDao;

    @Resource
    private EsTemplateTool esTemplateTool;

    @Override
    public boolean deleteIndex() {
        return esTemplateTool.deleteIndex(ProductDoc.class);
    }

    @Override
    public void save(OrderDoc doc) {
        orderDocDao.save(doc);
    }

    @Override
    public List<OrderDoc> listAll() {
        Iterable<OrderDoc> items = orderDocDao.findAll();
        List<OrderDoc> list = new ArrayList<>();
        items.forEach(item -> list.add(item));
        return list;
    }
}
