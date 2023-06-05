package com.ac.search.client.service.impl;

import com.ac.common.enums.OrderTypeEnum;
import com.ac.core.es.EsPage;
import com.ac.search.client.constant.IndexNameConstants;
import com.ac.search.client.dto.AggregationDTO;
import com.ac.search.client.dto.ProductHighlightDTO;
import com.ac.search.client.entity.ProductDoc;
import com.ac.search.client.mapping.ProductDocMapping;
import com.ac.search.common.qry.*;
import com.ac.search.client.service.ProductDocService;
import com.ac.search.client.tool.EsClientDdlTool;
import com.ac.search.client.tool.EsClientSearchTool;
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
    public boolean initIndexByJson() {
        deleteIndex();
        return createIndexByJson();
    }

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
    public boolean createIndexByJson() {
        return esClientDdlTool.createIndexByJson(IndexNameConstants.PRODUCT_DOC, "/json/product-mapping.json");
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
        OneFieldSearchQry qry = OneFieldSearchQry.builder()
                .indexName(IndexNameConstants.PRODUCT_DOC)
                .keyword(keyword)
                .field("category")
                .build();
        return esClientSearchTool.termSearch(ProductDoc.class, qry);
    }

    @Override
    public List<ProductDoc> listByMatch(String keyword) {
        OneFieldSearchQry qry = OneFieldSearchQry.builder()
                .indexName(IndexNameConstants.PRODUCT_DOC)
                .keyword(keyword)
                .field("remark")
                .build();
        return esClientSearchTool.matchSearch(ProductDoc.class, qry);
    }

    @Override
    public List<ProductDoc> listByMultiMatch(String keyword) {
        MultiSearchQry qry = MultiSearchQry.builder()
                .indexName(IndexNameConstants.PRODUCT_DOC)
                .keyword(keyword)
                .fieldList(Arrays.asList("productName", "remark"))
                .build();
        return esClientSearchTool.multiMatchSearch(ProductDoc.class, qry);
    }

    @Override
    public List<ProductDoc> listByGeo(GeoSearchQry qry) {
        qry.setIndexName(IndexNameConstants.PRODUCT_DOC);
        qry.setGeoPointField("location");
        return esClientSearchTool.geoSearch(ProductDoc.class, qry);
    }

    @Override
    public List<ProductHighlightDTO> listByMatchHighlight(String keyword) {
        OneFieldSearchQry qry = OneFieldSearchQry.builder()
                .indexName(IndexNameConstants.PRODUCT_DOC)
                .keyword(keyword)
                .field("remark")
                .build();
        return esClientSearchTool.matchSearchHighlight(ProductHighlightDTO.class, qry);
    }

    @Override
    public List<AggregationDTO> aggregationSearch() {
        AggregationSearchQry qry = AggregationSearchQry.builder()
                .indexName(IndexNameConstants.PRODUCT_DOC)
                .aggregationField("brand")
                .termField("category")
                .termValue("饮料")
                .returnFirstDoc(true)
                .docIgnoreProperties(Arrays.asList("location"))
                .build();
        return esClientSearchTool.aggregationSearch(ProductDoc.class, qry);
    }

    @Override
    public EsPage<ProductDoc> pageSearch(Integer current, Integer size, String keyword) {
        PageSearchQry qry = PageSearchQry.builder()
                .indexName(IndexNameConstants.PRODUCT_DOC)
                .current(current)
                .size(size)
                .keyword(keyword)
                .termField("category")
                .termValue("饮料")
                .fieldList(Arrays.asList("remark", "remark.pinyin", "productName.pinyin"))
                .fieldUnSplitList(Arrays.asList("productName"))
                .orderField("id")
                .orderType(OrderTypeEnum.ASC)
                .build();
        return esClientSearchTool.pageSearch(ProductDoc.class, qry);
    }
}
