package com.ac.search.controller;

import com.ac.common.page.EsPage;
import com.ac.search.dto.ProductHighlightDTO;
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

    @ApiOperation(value = "新增文档")
    @PostMapping
    public boolean saveDoc(@RequestBody ProductDoc doc) {
        productDocServiceImpl.saveDoc(doc);
        return true;
    }

    @ApiOperation(value = "修改文档")
    @PutMapping
    public boolean updateDoc(@RequestBody ProductDoc doc) {
        productDocServiceImpl.updateDoc(doc);
        return true;
    }

    @ApiOperation(value = "删除文档")
    @DeleteMapping
    public boolean deleteDoc(@RequestParam String docId) {
        productDocServiceImpl.deleteDoc(docId);
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

    @ApiOperation(value = "多字段分词查询")
    @GetMapping("listByMultiMatch")
    public List<ProductDoc> listByMultiMatch(@RequestParam String keyword) {
        return productDocServiceImpl.listByMultiMatch(keyword);
    }

    @ApiOperation(value = "分词查询-高亮显示")
    @GetMapping("listByMatchHighlight")
    public List<ProductHighlightDTO> listByMatchHighlight(@RequestParam String keyword) {
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

    @ApiOperation(value = "分页查询")
    @GetMapping("pageSearch")
    public EsPage<ProductDoc> pageSearch(@RequestParam Integer current, @RequestParam Integer size, @RequestParam String keyword) {
        return productDocServiceImpl.pageSearch(current, size, keyword);

        /**
         * select * from indexName where termField = 'termValue' and (field_1 in (被搜索字段的分词 AND)  or field_2 in (被搜索字段的分词 OR))
         *
         * {
         *     "from": 0,
         *     "size": 10,
         *     "query": {
         *         "bool": {
         *             "must": [
         *                 {
         *                     "term": {
         *                         "category": {
         *                             "value": "饮料",
         *                             "boost": 1.0
         *                         }
         *                     }
         *                 },
         *                 {
         *                     "bool": {
         *                         "should": [
         *                             {
         *                                 "multi_match": {
         *                                     "query": "雪碧",
         *                                     "fields": [
         *                                         "productName^1.0"
         *                                     ],
         *                                     "type": "best_fields",
         *                                     "operator": "AND",
         *                                     "slop": 0,
         *                                     "prefix_length": 0,
         *                                     "max_expansions": 50,
         *                                     "zero_terms_query": "NONE",
         *                                     "auto_generate_synonyms_phrase_query": true,
         *                                     "fuzzy_transpositions": true,
         *                                     "boost": 1.0
         *                                 }
         *                             },
         *                             {
         *                                 "multi_match": {
         *                                     "query": "雪碧",
         *                                     "fields": [
         *                                         "productName.pinyin^1.0",
         *                                         "remark^1.0",
         *                                         "remark..pinyin^1.0"
         *                                     ],
         *                                     "type": "best_fields",
         *                                     "operator": "OR",
         *                                     "slop": 0,
         *                                     "prefix_length": 0,
         *                                     "max_expansions": 50,
         *                                     "zero_terms_query": "NONE",
         *                                     "auto_generate_synonyms_phrase_query": true,
         *                                     "fuzzy_transpositions": true,
         *                                     "boost": 1.0
         *                                 }
         *                             }
         *                         ],
         *                         "adjust_pure_negative": true,
         *                         "boost": 1.0
         *                     }
         *                 }
         *             ],
         *             "adjust_pure_negative": true,
         *             "boost": 1.0
         *         }
         *     }
         * }
         */
    }
}
