package com.ac.search.controller;

import com.ac.search.dto.ProductHighlight;
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

    @ApiOperation(value = "新增")
    @PostMapping
    public boolean add(@RequestBody ProductDoc doc) {
        productDocServiceImpl.save(doc);
        return true;
    }

    @ApiOperation(value = "精确查询")
    @GetMapping("listByTerm")
    public List<ProductDoc> listByTerm(@RequestParam String keyword) {
        return productDocServiceImpl.listByTerm(keyword);
    }

    @ApiOperation(value = "分词查询")
    @GetMapping("listByMatch")
    public List<ProductDoc> listByMatch(@RequestParam String keyword) {
        return productDocServiceImpl.listByMatch(keyword);
    }

    @ApiOperation(value = "分词查询-高亮显示")
    @GetMapping("listByMatchHighlight")
    public List<ProductHighlight> listByMatchHighlight(@RequestParam String keyword) {
        return productDocServiceImpl.listByMatchHighlight(keyword);
    }
}
