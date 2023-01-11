package com.ac.product.service.impl;

import com.ac.product.convert.ProductConvert;
import com.ac.product.dao.ProductDao;
import com.ac.product.dto.ProductDTO;
import com.ac.product.entity.Product;
import com.ac.product.qry.ProductQry;
import com.ac.product.service.ProductService;
import com.ac.product.vo.ProductEditVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;

    @Override
    public Product findById(Long id) {
        return Optional.ofNullable(productDao.getById(id)).orElseThrow(() -> new RuntimeException("数据不存在"));
    }

    @Override
    public Boolean addProduct(ProductEditVO editVO) {
        Product entity = ProductConvert.instance.editVoToEntity(editVO);
        return productDao.save(entity);
    }

    @Override
    public List<ProductDTO> listProduct(ProductQry qry) {
        return productDao.listProduct(qry);
    }
}
