package com.ac.order.entity;

import com.ac.common.base.BaseEntity;
import com.ac.order.enums.OrderStateEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Alan Chen
 * @description 订单
 * @date 2023/02/22
 */
@Data
@TableName("t_order")
public class Order extends BaseEntity {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("支付渠道(微信/支付宝)")
    private String payChannel;

    @ApiModelProperty("订单状态")
    private OrderStateEnum orderState;

    @ApiModelProperty("下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty("付款时间")
    private LocalDateTime payTime;

    @ApiModelProperty("退单时间")
    private LocalDateTime refundTime;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("用户姓名(冗余)")
    private String memberName;

    @ApiModelProperty("手机号(冗余)")
    private String mobile;

    @ApiModelProperty("商品总金额")
    private BigDecimal productAmount;

    @ApiModelProperty("优惠总金额")
    private BigDecimal discountAmount;

    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;
}
