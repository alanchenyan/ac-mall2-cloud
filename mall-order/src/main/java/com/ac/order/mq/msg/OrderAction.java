package com.ac.order.mq.msg;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel(description = "事件动作")
public enum OrderAction {
    PAID("PAID", "已支付"),
    USED("USED", "核销"),
    REFUND("REFUND", "退单");

    @EnumValue
    @JsonValue
    private String code;
    private String name;
}
