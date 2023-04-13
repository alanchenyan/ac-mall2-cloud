package com.ac.search.controller;

import com.ac.search.entity.OrderDoc;
import com.ac.search.entity.ProductDoc;
import com.ac.search.service.OrderDocService;
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

    @ApiOperation(value = "删除index")
    @DeleteMapping("deleteIndex")
    public boolean deleteIndex() {
        return orderDocServiceImpl.deleteIndex();
    }

    @ApiOperation(value = "新增文档")
    @PostMapping
    public boolean saveDoc(@RequestBody OrderDoc doc) {
        orderDocServiceImpl.saveDoc(doc);
        return true;
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
        return orderDocServiceImpl.listByTerm(keyword);
    }

    @ApiOperation(value = "列表-所有")
    @GetMapping("list")
    public List<OrderDoc> list() {
        return orderDocServiceImpl.listAll();
    }
}
