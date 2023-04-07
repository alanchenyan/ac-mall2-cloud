package com.ac.search.controller;

import com.ac.search.entity.ProductDoc;
import com.ac.search.service.ProductDocService;
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
    private ProductDocService productDocServiceImpl;

    @ApiOperation(value = "初始化index")
    @PostMapping("initIndex")
    public boolean initIndex() {
        return productDocServiceImpl.initIndex();
    }

    @ApiOperation(value = "列表")
    @GetMapping("list")
    public List<ProductDoc> list() {
        return productDocServiceImpl.listAll();
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public boolean add(@RequestBody ProductDoc doc) {
        productDocServiceImpl.save(doc);
        return true;
    }
}
