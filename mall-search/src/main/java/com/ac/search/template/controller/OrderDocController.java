package com.ac.search.template.controller;

import com.ac.search.template.entity.OrderDoc;
import com.ac.search.template.service.OrderDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "订单")
@RestController
@RequestMapping("order_doc")
public class OrderDocController {

    @Resource
    private OrderDocService orderDocServiceImpl;

    @ApiOperation(value = "初始化index")
    @PostMapping("initIndex")
    public boolean initIndex() {
        return orderDocServiceImpl.initIndex();
    }

    @ApiOperation(value = "新增文档")
    @PostMapping
    public boolean saveDoc(@RequestBody OrderDoc doc) {
        orderDocServiceImpl.saveDoc(doc);
        return true;

        /**
         * {
         *   "id": "1001",
         *   "orderNo": "P1001",
         *   "productName": "大瓶雪碧"
         * }
         */
    }

    @ApiOperation(value = "修改文档")
    @PutMapping
    public boolean updateDoc(@RequestBody OrderDoc doc) {
        orderDocServiceImpl.updateDoc(doc);
        return true;
    }

    @ApiOperation(value = "删除文档")
    @DeleteMapping
    public boolean deleteDoc(@RequestParam String docId) {
        orderDocServiceImpl.deleteDoc(docId);
        return true;
    }

    @ApiOperation(value = "精确查询")
    @GetMapping("listByTerm")
    public List<OrderDoc> listByTerm(@RequestParam String keyword) {
        //127.0.0.1:7040/order_doc/listByTerm?keyword=P1001
        return orderDocServiceImpl.listByTerm(keyword);
    }

    @ApiOperation(value = "分词查询")
    @GetMapping("listByMatch")
    public List<OrderDoc> listByMatch(@RequestParam String keyword) {
        //127.0.0.1:7040/order_doc/listByMatch?keyword=雪碧
        return orderDocServiceImpl.listByMatch(keyword);
    }

    @ApiOperation(value = "列表-所有")
    @GetMapping("list")
    public List<OrderDoc> list() {
        return orderDocServiceImpl.listAll();
    }
}
