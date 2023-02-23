package com.ac.member.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "积分来源类型")
public enum IntegralSourceTypeEnum {
    AWARD_ORDER("AWARD_ORDER", "下单奖励"),
    AWARD_SIGN_IN("AWARD_SIGN_IN", "签到奖励"),
    SUB_DRAW("SUB_DRAW", "抽奖扣减积分");

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
    public static IntegralSourceTypeEnum parse(String type) {
        return parse(type, null);
    }

    /**
     * 解析
     *
     * @param type
     * @param defaultType
     * @return
     */
    public static IntegralSourceTypeEnum parse(String type, IntegralSourceTypeEnum defaultType) {
        if (null != type && !type.isEmpty()) {
            return IntegralSourceTypeEnum.valueOf(type.toUpperCase());
        }
        return defaultType;
    }
}
