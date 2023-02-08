package com.ac.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_order_item")
public class OrderItem {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("购买数量")
    private Integer buyNum;

    @ApiModelProperty("购买价")
    private BigDecimal buyPrice;

    @ApiModelProperty("图片地址")
    private String imageUrl;
}
