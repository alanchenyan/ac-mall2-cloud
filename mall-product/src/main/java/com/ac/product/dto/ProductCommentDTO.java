package com.ac.product.dto;

import com.ac.product.mongo.MemberInfo;
import com.ac.product.mongo.OrderProductInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductCommentDTO {

    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("评价人ID")
    private Long memberId;

    @ApiModelProperty("评价人用户信息")
    private MemberInfo memberInfo;

    @ApiModelProperty("订单产品信息")
    private OrderProductInfo orderProductInfo;
}
