package com.ac.search.template.service.impl;

import com.ac.search.template.dao.OrderDocDao;
import com.ac.search.template.entity.OrderDoc;
import com.ac.search.common.qry.OneFieldSearchQry;
import com.ac.search.template.service.OrderDocService;
import com.ac.search.template.tool.EsTemplateDdlTool;
import com.ac.search.template.tool.EsTemplateSearchTool;
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
    private EsTemplateSearchTool esTemplateSearchTool;

    @Resource
    private EsTemplateDdlTool esTemplateDdlTool;

    @Override
    public boolean initIndex() {
        deleteIndex();
        return createIndex();
    }

    @Override
    public boolean createIndex() {
        return esTemplateDdlTool.createIndex(OrderDoc.class);
    }

    @Override
    public boolean deleteIndex() {
        return esTemplateDdlTool.deleteIndex(OrderDoc.class);
    }

    @Override
    public void saveDoc(OrderDoc doc) {
        orderDocDao.save(doc);
    }

    @Override
    public void updateDoc(OrderDoc doc) {
        esTemplateDdlTool.updateDoc(OrderDoc.class, doc.getId(), doc);
    }

    @Override
    public void deleteDoc(String docId) {
        orderDocDao.deleteById(docId);
    }

    @Override
    public List<OrderDoc> listByTerm(String keyword) {
        OneFieldSearchQry qry = OneFieldSearchQry.builder()
                .keyword(keyword)
                .field("orderNo")
                .build();
        return esTemplateSearchTool.termSearch(OrderDoc.class, qry);
    }

    @Override
    public List<OrderDoc> listByMatch(String keyword) {
        OneFieldSearchQry qry = OneFieldSearchQry.builder()
                .keyword(keyword)
                .field("productName")
                .build();
        return esTemplateSearchTool.matchSearch(OrderDoc.class, qry);
    }

    @Override
    public List<OrderDoc> listAll() {
        Iterable<OrderDoc> items = orderDocDao.findAll();
        List<OrderDoc> list = new ArrayList<>();
        items.forEach(item -> list.add(item));
        return list;
    }
}
