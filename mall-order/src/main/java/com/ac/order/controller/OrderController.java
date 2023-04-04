package com.ac.order.controller;

import com.ac.common.qm.msg.MqOrderAction;
import com.ac.common.qm.msg.MqOrderMsg;
import com.ac.order.dto.OrderDTO;
import com.ac.order.mq.send.OrderSender;
import com.ac.order.qry.OrderPageQry;
import com.ac.order.service.OrderService;
import com.ac.order.vo.OrderAddVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;

@Api(tags = "订单")
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderServiceImpl;

    @Resource
    private OrderSender orderSender;

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

    @ApiOperation(value = "MQ")
    @GetMapping("mq")
    public Boolean mq() {

        //发送下单MQ
        MqOrderMsg mqMsg = MqOrderMsg.builder()
                .action(MqOrderAction.PAID)
                .orderId(1L)
                .memberId(273L)
                .payAmount(new BigDecimal(100))
                .build();
        orderSender.asyncSend(mqMsg);

        return true;
    }
}
