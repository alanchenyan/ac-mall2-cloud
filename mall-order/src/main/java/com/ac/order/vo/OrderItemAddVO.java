package com.ac.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderItemAddVO {

    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("购买数量")
    private Integer buyNum;
}
