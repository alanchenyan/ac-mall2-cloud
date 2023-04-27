package com.ac.order.entity;

import com.ac.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Alan Chen
 * @description 订单项
 * @date 2023/02/22
 */
@Data
@TableName("t_order_item")
public class OrderItem extends BaseEntity {

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
