package com.ac.search.client.service;

import com.ac.core.es.EsPage;
import com.ac.search.client.dto.AggregationDTO;
import com.ac.search.client.dto.ProductHighlightDTO;
import com.ac.search.client.entity.ProductDoc;
import com.ac.search.common.qry.GeoSearchQry;

import java.util.List;

public interface ProductDocService {

    /**
     * 初始化index
     *
     * @return
     */
    boolean initIndexByJson();

    /**
     * 初始化index
     *
     * @return
     */
    boolean initIndex();

    /**
     * 删除index
     */
    boolean deleteIndex();

    /**
     * 创建index
     *
     * @return
     */
    boolean createIndex();

    /**
     * 创建index
     * @return
     */
    boolean createIndexByJson();

    /**
     * 保存文档
     *
     * @param doc
     */
    void saveDoc(ProductDoc doc);

    /**
     * 修改文档
     *
     * @param doc
     */
    void updateDoc(ProductDoc doc);

    /**
     * 删除文档
     *
     * @param docId
     */
    void deleteDoc(String docId);

    /**
     * 精确查询
     *
     * @param keyword
     * @return
     */
    List<ProductDoc> listByTerm(String keyword);

    /**
     * 分词查询
     *
     * @param keyword
     * @return
     */
    List<ProductDoc> listByMatch(String keyword);

    /**
     * 多字段分词查询
     *
     * @param keyword
     * @return
     */
    List<ProductDoc> listByMultiMatch(String keyword);

    /**
     * GEO地理位置查询
     *
     * @param qry
     * @return
     */
    List<ProductDoc> listByGeo(GeoSearchQry qry);

    /**
     * 分词查询-高亮显示
     *
     * @param keyword
     * @return
     */
    List<ProductHighlightDTO> listByMatchHighlight(String keyword);

    /**
     * 分组查询
     *
     * @return
     */
    List<AggregationDTO> aggregationSearch();

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @param keyword
     * @return
     */
    EsPage<ProductDoc> pageSearch(Integer current, Integer size, String keyword);
}
