package com.ac.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OrderAddVO {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty("用户ID")
    private Long memberId;

    @Size(min = 1, message = "商品项不能为空")
    @ApiModelProperty("商品项")
    private List<OrderItemAddVO> orderItemList;
}
