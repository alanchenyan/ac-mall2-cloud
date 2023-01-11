package com.ac.product.mapper;

import com.ac.product.dto.ProductDTO;
import com.ac.product.entity.Product;
import com.ac.product.qry.ProductQry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Alan Chen
 * @description 产品-数据访问层(依赖mybatis-plus)，对应mapper.xml里的自定义sql
 * @date 2022/12/29
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询产品列表
     *
     * @param qry
     * @return
     */
    List<ProductDTO> listProduct(@Param("qry") ProductQry qry);
}
