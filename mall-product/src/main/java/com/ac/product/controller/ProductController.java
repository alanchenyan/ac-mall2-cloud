package com.ac.product.controller;

import com.ac.product.dto.ProductDTO;
import com.ac.product.entity.Product;
import com.ac.product.qry.ProductQry;
import com.ac.product.service.ProductService;
import com.ac.product.vo.ProductEditVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "产品")
@RestController
@RequestMapping("product")
public class ProductController {

    @Resource
    private ProductService productService;

    @ApiOperation(value = "通过ID查询")
    @GetMapping("{id}")
    public Product findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @ApiOperation(value = "新增产品")
    @PostMapping
    public Boolean addProduct(@RequestBody @Valid ProductEditVO editVO) {
        return productService.addProduct(editVO);
    }

    @ApiOperation(value = "查询产品列表")
    @GetMapping("list")
    public List<ProductDTO> listMember(@Valid ProductQry qry) {
        return productService.listProduct(qry);
    }
}
