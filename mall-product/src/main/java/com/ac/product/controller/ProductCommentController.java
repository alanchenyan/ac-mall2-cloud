package com.ac.product.controller;

import com.ac.product.mongo.ProductComment;
import com.ac.product.service.ProductCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "产品评价")
@RestController
@RequestMapping("productComment")
public class ProductCommentController {

    @Resource
    private ProductCommentService productCommentServiceImpl;

    @ApiOperation(value = "新增产品评价")
    @PostMapping
    public String addProduct(@RequestBody @Valid ProductComment doc) {
        return productCommentServiceImpl.save(doc);
    }
}
