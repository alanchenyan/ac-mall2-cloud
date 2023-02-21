package com.ac.order.controller;

import com.ac.feign.member.api.MemberFeignApi;
import com.ac.feign.member.dto.MemberDTO;
import com.ac.order.service.OrderService;
import com.ac.order.vo.OrderAddVO;
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
    private MemberFeignApi memberFeignApi;

    @Resource
    private OrderService orderServiceImpl;

    @ApiOperation(value = "通过OpenFeign取用户数据")
    @GetMapping("feign/member/{id}")
    public MemberDTO test(@PathVariable Long id) {
        return memberFeignApi.findMember(id);
    }


    @ApiOperation(value = "新增订单")
    @PostMapping
    public Long createOrder(@RequestBody @Valid OrderAddVO addVO) {
        return orderServiceImpl.createOrder(addVO);
    }
}
