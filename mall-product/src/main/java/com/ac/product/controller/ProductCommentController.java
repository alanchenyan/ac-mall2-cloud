package com.ac.product.controller;

import com.ac.product.dto.ProductCommentDTO;
import com.ac.product.mongo.ProductComment;
import com.ac.product.qry.CommentQry;
import com.ac.product.service.ProductCommentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "分页查询")
    @GetMapping("page")
    public IPage<ProductCommentDTO> page(@Valid CommentQry qry) {
        return productCommentServiceImpl.page(qry);
    }

    @ApiOperation(value = "分页查询2")
    @GetMapping("page2")
    public IPage<ProductCommentDTO> page2(@Valid CommentQry qry) {
        return productCommentServiceImpl.page2(qry);
    }
}
