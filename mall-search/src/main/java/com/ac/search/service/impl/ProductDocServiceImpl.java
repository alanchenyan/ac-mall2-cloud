package com.ac.search.service.impl;

import com.ac.search.constant.IndexNameConstants;
import com.ac.search.dto.ProductHighlightDTO;
import com.ac.search.entity.ProductDoc;
import com.ac.search.mapping.ProductDocMapping;
import com.ac.search.qry.MultiMatchSearchQry;
import com.ac.search.service.ProductDocService;
import com.ac.search.tool.EsClientDdlTool;
import com.ac.search.tool.EsClientSearchTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ProductDocServiceImpl implements ProductDocService {

    @Resource
    private EsClientDdlTool esClientDdlTool;

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
        return esClientDdlTool.deleteIndex(IndexNameConstants.PRODUCT_DOC);
    }

    @Override
    public boolean createIndex() {
        return esClientDdlTool.createIndex(IndexNameConstants.PRODUCT_DOC, productDocMapping.packageMapping());
    }

    @Override
    public void saveDoc(ProductDoc doc) {
        esClientDdlTool.insertDoc(IndexNameConstants.PRODUCT_DOC, doc.getId(), doc);
    }

    @Override
    public void updateDoc(ProductDoc doc) {
        esClientDdlTool.updateDoc(IndexNameConstants.PRODUCT_DOC, doc.getId(), doc);
    }

    @Override
    public void deleteDoc(String docId) {
        esClientDdlTool.deleteDoc(IndexNameConstants.PRODUCT_DOC, docId);
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
    public List<ProductDoc> listByMultiMatch(String keyword) {
        MultiMatchSearchQry qry = MultiMatchSearchQry.builder()
                .indexName(IndexNameConstants.PRODUCT_DOC)
                .keyword(keyword)
                .fieldList(Arrays.asList("productName", "remark"))
                .build();
        return esClientSearchTool.multiMatchSearch(ProductDoc.class, qry);
    }

    @Override
    public List<ProductHighlightDTO> listByMatchHighlight(String keyword) {
        return esClientSearchTool.matchSearchHighlight(ProductHighlightDTO.class, IndexNameConstants.PRODUCT_DOC, "remark", keyword);
    }
}
