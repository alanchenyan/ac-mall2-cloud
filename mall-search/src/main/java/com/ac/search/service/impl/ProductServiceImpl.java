package com.ac.search.service.impl;

import com.ac.search.dao.ProductDocDao;
import com.ac.search.entity.ProductDoc;
import com.ac.search.service.ProductService;
import com.ac.search.tool.EsTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDocDao productDocDao;

    @Resource
    private EsTool esTool;

    @Override
    public boolean deleteIndex() {
        //return esTool.deleteIndex("product_doc");
        return esTool.deleteIndex(ProductDoc.class);
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
