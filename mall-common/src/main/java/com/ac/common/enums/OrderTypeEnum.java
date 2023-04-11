package com.ac.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "排序类型")
public enum OrderTypeEnum {
    ASC("ASC", "升序"),
    DESC("DESC", "降序");

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
    public static OrderTypeEnum parse(String type) {
        return parse(type, null);
    }

    /**
     * 解析
     *
     * @param type
     * @param defaultType
     * @return
     */
    public static OrderTypeEnum parse(String type, OrderTypeEnum defaultType) {
        if (null != type && !type.isEmpty()) {
            return OrderTypeEnum.valueOf(type.toUpperCase());
        }
        return defaultType;
    }
}
