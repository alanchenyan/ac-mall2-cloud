package com.ac.product.dao;

import com.ac.product.dto.ProductDTO;
import com.ac.product.entity.Product;
import com.ac.product.qry.ProductQry;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Alan Chen
 * @description 产品-数据访问层(依赖mybatis-plus),重用mybatis-service提供的CURD方法
 * @date 2022/12/29
 */
public interface ProductDao extends IService<Product> {

    /**
     * 查询产品列表
     *
     * @param qry
     * @return
     */
    List<ProductDTO> listProduct(ProductQry qry);
}
