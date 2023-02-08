package com.ac.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailDTO {

    @ApiModelProperty("订单基本信息")
    private OrderDTO orderDTO;

    @ApiModelProperty("订单项")
    private List<OrderItemDTO> orderItemList;
}
