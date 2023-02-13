package com.ac.order.controller;

import com.ac.feign.member.api.MemberFeignApi;
import com.ac.feign.member.dto.MemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "订单")
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private MemberFeignApi memberFeignApi;

    @ApiOperation(value = "通过OpenFeign取用户数据")
    @GetMapping("feign/member/{id}")
    public MemberDTO test(@PathVariable Long id) {
        return memberFeignApi.findMember(id);
    }
}
