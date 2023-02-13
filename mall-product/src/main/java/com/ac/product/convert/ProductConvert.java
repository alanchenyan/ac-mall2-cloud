package com.ac.product.convert;

import com.ac.product.dto.ProductDTO;
import com.ac.product.entity.Product;
import com.ac.product.vo.ProductEditVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductConvert {

    ProductConvert instance = Mappers.getMapper(ProductConvert.class);

    /**
     * vo转entity
     *
     * @param editVO
     * @return
     */
    Product editVoToEntity(ProductEditVO editVO);

    /**
     * entity转DTO
     *
     * @param entity
     * @return
     */
    ProductDTO entityToDto(Product entity);
}
