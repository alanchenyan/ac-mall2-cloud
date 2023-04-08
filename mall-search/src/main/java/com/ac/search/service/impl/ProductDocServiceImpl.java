package com.ac.search.service.impl;

import com.ac.search.constant.IndexNameConstants;
import com.ac.search.dto.ProductHighlight;
import com.ac.search.entity.ProductDoc;
import com.ac.search.mapping.ProductDocMapping;
import com.ac.search.service.ProductDocService;
import com.ac.search.tool.EsClientSearchTool;
import com.ac.search.tool.EsClientTool;
import com.ac.search.tool.EsTemplateTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ProductDocServiceImpl implements ProductDocService {

    @Resource
    private EsTemplateTool esTemplateTool;

    @Resource
    private EsClientTool esClientTool;

    @Resource
    private EsClientSearchTool esClientSearchTool;

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
    public List<ProductDoc> listByTerm(String keyword) {
        return esClientSearchTool.termSearch(ProductDoc.class, IndexNameConstants.PRODUCT_DOC, "category", keyword);
    }

    @Override
    public List<ProductDoc> listByMatch(String keyword) {
        return esClientSearchTool.matchSearch(ProductDoc.class, IndexNameConstants.PRODUCT_DOC, "remark", keyword);
    }

    @Override
    public List<ProductHighlight> listByMatchHighlight(String keyword) {
        return esClientSearchTool.matchSearchHighlight(ProductHighlight.class, IndexNameConstants.PRODUCT_DOC, "remark", keyword);
    }
}
