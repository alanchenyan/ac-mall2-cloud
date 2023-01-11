package com.ac.product.dao.impl;

import com.ac.product.dao.ProductDao;
import com.ac.product.dto.ProductDTO;
import com.ac.product.entity.Product;
import com.ac.product.mapper.ProductMapper;
import com.ac.product.qry.ProductQry;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Repository
public class ProductDaoImpl extends ServiceImpl<ProductMapper, Product> implements ProductDao {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> listProduct(ProductQry qry) {
        return productMapper.listProduct(qry);
    }
}
