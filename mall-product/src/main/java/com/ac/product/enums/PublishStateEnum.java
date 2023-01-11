package com.ac.product.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "产品发布状态枚举")
public enum PublishStateEnum {
    UN_PUBLISHED("UN_PUBLISHED", "未发布"),
    PUBLISHED("PUBLISHED", "已发布"),
    OFF("OFF", "下架");

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
    public static PublishStateEnum parse(String type) {
        return parse(type, null);
    }

    /**
     * 解析
     *
     * @param type
     * @param defaultType
     * @return
     */
    public static PublishStateEnum parse(String type, PublishStateEnum defaultType) {
        if (null != type && !type.isEmpty()) {
            return PublishStateEnum.valueOf(type.toUpperCase());
        }
        return defaultType;
    }
}
