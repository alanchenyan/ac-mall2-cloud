package com.ac.product.service;

import com.ac.product.dto.ProductDTO;
import com.ac.product.entity.Product;
import com.ac.product.qry.ProductQry;
import com.ac.product.vo.ProductEditVO;

import java.util.List;

/**
 * @author Alan Chen
 * @description 产品-业务层
 * @date 2022/12/29
 */
public interface ProductService {

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    Product findById(Long id);

    /**
     * 获取产品
     *
     * @param id
     * @return
     */
    ProductDTO findProduct(Long id);

    /**
     * 新增产品
     *
     * @param editVO
     * @return
     */
    Boolean addProduct(ProductEditVO editVO);

    /**
     * 查询产品列表
     *
     * @param qry
     * @return
     */
    List<ProductDTO> listProduct(ProductQry qry);
}
