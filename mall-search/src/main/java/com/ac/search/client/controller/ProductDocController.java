package com.ac.search.client.controller;

import com.ac.common.page.EsPage;
import com.ac.search.client.dto.AggregationDTO;
import com.ac.search.client.dto.ProductHighlightDTO;
import com.ac.search.client.entity.ProductDoc;
import com.ac.search.common.qry.GeoSearchQry;
import com.ac.search.client.service.ProductDocService;
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

    @ApiOperation(value = "初始化index-json")
    @PostMapping("initIndexByJson")
    public boolean initIndexByJson() {
        return productDocServiceImpl.initIndexByJson();
    }

    @ApiOperation(value = "新增文档")
    @PostMapping
    public boolean saveDoc(@RequestBody ProductDoc doc) {
        productDocServiceImpl.saveDoc(doc);
        return true;

        /**
         * {
         *   "id": 1001,
         *   "productName": "迷你雪碧",
         *   "category": "饮料",
         *   "brand": "可口可乐",
         *   "stock": 100,
         *   "costPrice": 0.50,
         *   "sellPrice": 6.00,
         *   "discountPrice": 4.50,
         *   "imageUrl": "https://img2.baidu.com/it/u=302349378,686513206&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
         *   "remark": "可口可乐 迷你小瓶雪碧300ml*6/12/24瓶整箱装柠檬味汽水碳酸饮料批发 雪碧【300mlX6瓶】】 口味",
         *   "location": {
         *     "lat": 20.1322,
         *     "lon": 110.2562
         *   }
         * }
         *
         * {
         *   "id": 1002,
         *   "productName": "大瓶雪碧",
         *   "category": "饮料",
         *   "brand": "可口可乐",
         *   "stock": 200,
         *   "costPrice": 0.50,
         *   "sellPrice": 16.00,
         *   "discountPrice": 14.50,
         *   "imageUrl": "https://img2.baidu.com/it/u=302349378,686513206&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
         *   "remark": "可口可乐 大瓶雪碧600ml*6/12/24瓶整箱装柠檬味汽水碳酸饮料批发 雪碧【600mlX6瓶】】 口味",
         *   "location": {
         *     "lat": 20.1321,
         *     "lon": 110.2561
         *   }
         * }
         */
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
        //127.0.0.1:7040/product_doc/listByTerm?keyword=饮料
        return productDocServiceImpl.listByTerm(keyword);
    }

    @ApiOperation(value = "分词查询")
    @GetMapping("listByMatch")
    public List<ProductDoc> listByMatch(@RequestParam String keyword) {
        //127.0.0.1:7040/product_doc/listByMatch?keyword=雪碧
        return productDocServiceImpl.listByMatch(keyword);
    }

    @ApiOperation(value = "多字段分词查询")
    @GetMapping("listByMultiMatch")
    public List<ProductDoc> listByMultiMatch(@RequestParam String keyword) {
        //127.0.0.1:7040/product_doc/listByMultiMatch?keyword=碳酸
        return productDocServiceImpl.listByMultiMatch(keyword);
    }

    @ApiOperation(value = "GEO地理位置查询")
    @GetMapping("listByGeo")
    public List<ProductDoc> listByGeo(GeoSearchQry qry) {
        //127.0.0.1:7040/product_doc/listByGeo?lat=20.1322&lon=110.2562&distance=100
        return productDocServiceImpl.listByGeo(qry);
    }

    @ApiOperation(value = "分组查询")
    @GetMapping("aggregationSearch")
    public List<AggregationDTO> aggregationSearch() {
        return productDocServiceImpl.aggregationSearch();

        /**
         * [
         *    {
         * 		"aggregationItem": "可口可乐",
         * 		"count": 2,
         * 		"firstDoc": {
         * 			"id": "1001",
         * 			"productName": "迷你雪碧",
         * 			"category": "饮料",
         * 			"brand": "可口可乐",
         * 			"remark": "可口可乐 迷你小瓶雪碧300ml*6/12/24瓶整箱装柠檬味汽水碳酸饮料批发 雪碧【300mlX6瓶】】 口味",
         * 			"location": null
         *        }
         *    },
         *    {
         * 		"aggregationItem": "哇哈哈",
         * 		"count": 1,
         * 		"firstDoc": {
         * 			"id": "1003",
         * 			"productName": "哇哈哈矿泉水",
         * 			"category": "饮料",
         * 			"brand": "哇哈哈",
         * 			"remark": "哇哈哈 大瓶600ml*6/12/24瓶整箱装批发 【600mlX6瓶】】 口味",
         * 			"location": null
         *        }
         *    }
         * ]
         */
    }

    @ApiOperation(value = "分词查询-高亮显示")
    @GetMapping("listByMatchHighlight")
    public List<ProductHighlightDTO> listByMatchHighlight(@RequestParam String keyword) {
        //127.0.0.1:7040/product_doc/listByMatchHighlight?keyword=雪碧
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
        //127.0.0.1:7040/product_doc/pageSearch?current=1&size=10&keyword=雪碧
        return productDocServiceImpl.pageSearch(current, size, keyword);

        /**
         * select * from indexName where termField = 'termValue' and (field_1 in (被搜索字段的分词 AND)  or field_2 in (被搜索字段的分词 OR)) order by id asc
         *
         *{
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
         *                                         "remark.pinyin^1.0"
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
         *     },
         *     "sort": [
         *         {
         *             "id": {
         *                 "order": "asc"
         *             }
         *         }
         *     ]
         * }
         */
    }
}
