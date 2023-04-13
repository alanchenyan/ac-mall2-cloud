package com.ac.search.service.impl;

import com.ac.search.dao.OrderDocDao;
import com.ac.search.entity.OrderDoc;
import com.ac.search.qry.MultiSearchQry;
import com.ac.search.service.OrderDocService;
import com.ac.search.tool.EsTemplateTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OrderDocServiceImpl implements OrderDocService {

    @Resource
    private OrderDocDao orderDocDao;

    @Resource
    private EsTemplateTool esTemplateTool;

    @Override
    public boolean initIndex() {
        deleteIndex();
        return createIndex();
    }

    @Override
    public boolean createIndex() {
        return esTemplateTool.createIndex(OrderDoc.class);
    }

    @Override
    public boolean deleteIndex() {
        return esTemplateTool.deleteIndex(OrderDoc.class);
    }

    @Override
    public void saveDoc(OrderDoc doc) {
        orderDocDao.save(doc);
    }

    @Override
    public void updateDoc(OrderDoc doc) {
        esTemplateTool.updateDoc(OrderDoc.class, doc.getId(), doc);
    }

    @Override
    public void deleteDoc(String docId) {
        orderDocDao.deleteById(docId);
    }

    @Override
    public List<OrderDoc> listByTerm(String keyword) {
        MultiSearchQry qry = MultiSearchQry.builder()
                .keyword(keyword)
                .fieldList(Arrays.asList("orderNo"))
                .build();
        return esTemplateTool.termSearch(OrderDoc.class, qry);
    }

    @Override
    public List<OrderDoc> listByMatch(String keyword) {
        return null;
    }

    @Override
    public List<OrderDoc> listAll() {
        Iterable<OrderDoc> items = orderDocDao.findAll();
        List<OrderDoc> list = new ArrayList<>();
        items.forEach(item -> list.add(item));
        return list;
    }
}
