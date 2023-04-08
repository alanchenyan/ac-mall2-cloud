package com.ac.search.service.impl;

import com.ac.search.constant.IndexNameConstants;
import com.ac.search.dao.ProductDocDao;
import com.ac.search.entity.ProductDoc;
import com.ac.search.mapping.ProductDocMapping;
import com.ac.search.service.ProductDocService;
import com.ac.search.tool.EsTemplateTool;
import com.ac.search.tool.EsClientTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductDocServiceImpl implements ProductDocService {

    @Resource
    private ProductDocDao productDocDao;

    @Resource
    private EsTemplateTool esTemplateTool;

    @Resource
    private EsClientTool esClientTool;

    @Resource
    private ProductDocMapping productDocMapping;

    @Override
    public boolean initIndex() {
        deleteIndex();
        return createIndex();
    }

    @Override
    public boolean deleteIndex() {
        boolean result;
        try {
            result = esTemplateTool.deleteIndex(IndexNameConstants.PRODUCT_DOC);
        } catch (Exception e) {
            log.info("删除index失败,indexName={}", IndexNameConstants.PRODUCT_DOC);
            result = false;
        }
        return result;
    }

    @Override
    public boolean createIndex() {
        return esClientTool.createIndex(IndexNameConstants.PRODUCT_DOC, productDocMapping.packageMapping());
    }

    @Override
    public void save(ProductDoc doc) {
        esClientTool.insertDoc(IndexNameConstants.PRODUCT_DOC, doc.getId(), doc);
    }

    @Override
    public List<ProductDoc> listAll() {
        Iterable<ProductDoc> items = productDocDao.findAll();
        List<ProductDoc> list = new ArrayList<>();
        items.forEach(item -> list.add(item));
        return list;
    }
}
