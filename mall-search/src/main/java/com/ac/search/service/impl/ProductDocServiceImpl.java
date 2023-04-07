package com.ac.search.service.impl;

import com.ac.search.constant.IndexNameConstants;
import com.ac.search.dao.ProductDocDao;
import com.ac.search.entity.ProductDoc;
import com.ac.search.service.ProductDocService;
import com.ac.search.tool.EsTool;
import com.ac.search.tool.ProductDocCreateByClientTool;
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
    private EsTool esTool;

    @Resource
    private ProductDocCreateByClientTool productDocCreateByClientTool;

    @Override
    public boolean deleteIndex() {
        return esTool.deleteIndex(ProductDoc.class);
    }

    @Override
    public boolean createIndex() {
        return productDocCreateByClientTool.createIndex(IndexNameConstants.PRODUCT_DOC);
    }

    @Override
    public void save(ProductDoc doc) {
        productDocDao.save(doc);
    }

    @Override
    public List<ProductDoc> listAll() {
        Iterable<ProductDoc> items = productDocDao.findAll();
        List<ProductDoc> list = new ArrayList<>();
        items.forEach(item -> list.add(item));
        return list;
    }
}
