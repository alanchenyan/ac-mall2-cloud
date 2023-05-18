package com.ac.order.controller;

import com.ac.order.component.XxlJobComponent;
import com.ac.order.dto.OrderDTO;
import com.ac.order.qry.OrderPageQry;
import com.ac.order.service.OrderService;
import com.ac.order.vo.OrderAddVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "订单")
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderServiceImpl;

    @Resource
    private XxlJobComponent xxlJobComponent;

    @ApiOperation(value = "新增订单")
    @PostMapping
    public Long createOrder(@RequestBody @Valid OrderAddVO addVO) {
        return orderServiceImpl.createOrder(addVO);
    }

    @ApiOperation(value = "分页查询订单")
    @GetMapping("page")
    public IPage<OrderDTO> pageOrder(@Valid OrderPageQry qry) {
        return orderServiceImpl.pageOrder(qry);
    }

    @ApiOperation(value = "xxl-job登录")
    @GetMapping("login")
    public String login() {
        return xxlJobComponent.login();
    }

    @ApiOperation(value = "xxl-job获取cookie")
    @GetMapping("getCookie")
    public String getCookie() {
        return xxlJobComponent.getCookie();
    }
}
