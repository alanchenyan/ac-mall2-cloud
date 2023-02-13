package com.ac.feign.product.api;

import com.ac.feign.product.dto.ProductDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("mall-product")
public interface ProductFeignApi {

    @ApiOperation(value = "获取产品")
    @GetMapping("product/{id}")
    ProductDTO findProduct(@PathVariable("id") Long id);
}
