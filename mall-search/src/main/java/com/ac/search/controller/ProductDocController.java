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

        /**
         * 查询结果：
         * [
         *        {
         * 		"highlightFields": {
         * 			"remark": {
         * 				"name": "remark",
         * 				"fragment": true,
         * 				"text": "可口可乐 迷你小瓶<span style='color:red'>雪碧</span>300ml*6/12/24瓶整箱装柠檬味汽水碳酸饮料批发 <span style='color:red'>雪碧</span>【300mlX6瓶】】 口味"
         *            },
         * 			"productName": {
         * 				"name": "productName",
         * 				"fragment": true,
         * 				"text": "<span style='color:red'>雪碧</span>"
         *            }
         *        },
         * 		"id": "1001",
         * 		"productName": "雪碧",
         * 		"category": "饮料",
         * 		"brand": "可口可乐",
         * 		"remark": "可口可乐 迷你小瓶雪碧300ml*6/12/24瓶整箱装柠檬味汽水碳酸饮料批发 雪碧【300mlX6瓶】】 口味"
         *    },
         *    {
         * 		"highlightFields": {
         * 			"remark": {
         * 				"name": "remark",
         * 				"fragment": true,
         * 				"text": "可口可乐 迷你小瓶<span style='color:red'>雪碧</span>300ml*6/12/24瓶整箱装柠檬味汽水碳酸饮料批发 <span style='color:red'>雪碧</span>【300mlX6瓶】】 口味"
         *            },
         * 			"productName": {
         * 				"name": "productName",
         * 				"fragment": true,
         * 				"text": "迷你<span style='color:red'>雪碧</span>"
         *            }
         *        },
         * 		"id": "1002",
         * 		"productName": "迷你雪碧",
         * 		"category": "饮料",
         * 		"brand": "可口可乐",
         * 		"remark": "可口可乐 迷你小瓶雪碧300ml*6/12/24瓶整箱装柠檬味汽水碳酸饮料批发 雪碧【300mlX6瓶】】 口味"
         *    }
         * ]
         */
    }
}
