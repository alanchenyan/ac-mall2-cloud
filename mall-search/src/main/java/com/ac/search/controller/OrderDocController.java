package com.ac.search.controller;

import com.ac.search.entity.OrderDoc;
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

    @ApiOperation(value = "列表")
    @GetMapping("list")
    public List<OrderDoc> list() {
        return orderDocServiceImpl.listAll();
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public boolean add(@RequestBody OrderDoc doc) {
        orderDocServiceImpl.save(doc);
        return true;
    }
}
