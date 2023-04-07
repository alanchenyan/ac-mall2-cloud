package com.ac.search.controller;

import com.ac.search.entity.ProductDoc;
import com.ac.search.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "商品")
@RestController
@RequestMapping("product_doc")
public class ProductDocController {

    @Resource
    private ProductService productServiceImpl;

    @ApiOperation(value = "删除index")
    @DeleteMapping("index")
    public boolean deleteIndex() {
        return productServiceImpl.deleteIndex();
    }

    @ApiOperation(value = "列表")
    @GetMapping("list")
    public List<ProductDoc> list() {
        return productServiceImpl.listAll();
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public boolean add(@RequestBody ProductDoc doc) {
        productServiceImpl.save(doc);
        return true;
    }
}
