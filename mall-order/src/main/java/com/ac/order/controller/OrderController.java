package com.ac.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单")
@RestController
@RequestMapping("order")
public class OrderController {

    @ApiOperation(value = "通过ID查询")
    @GetMapping
    public String test() {
        return "订单";
    }
}
