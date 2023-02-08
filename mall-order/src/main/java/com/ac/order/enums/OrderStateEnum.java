package com.ac.order.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单状态枚举")
public enum OrderStateEnum {
    UN_PAY("UN_PAY", "待付款"),
    PAYED("PAYED", "已付款"),
    REFUND("REFUND", "已退款"),
    INVALID("INVALID", "超时未支付"),
    ;

    @EnumValue
    @JsonValue
    private String code;
    private String name;

    /**
     * 解析
     *
     * @param type
     * @return
     */
    public static OrderStateEnum parse(String type) {
        return parse(type, null);
    }

    /**
     * 解析
     *
     * @param type
     * @param defaultType
     * @return
     */
    public static OrderStateEnum parse(String type, OrderStateEnum defaultType) {
        if (null != type && !type.isEmpty()) {
            return OrderStateEnum.valueOf(type.toUpperCase());
        }
        return defaultType;
    }
}
