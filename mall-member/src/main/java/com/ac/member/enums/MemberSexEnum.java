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
@ApiModel(description = "用户性别枚举")
public enum MemberSexEnum {
    UN_KNOW("UN_KNOW", "保密"),
    MEN("MEN", "男"),
    WOMEN("WOMEN", "女");

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
    public static MemberSexEnum parse(String type) {
        return parse(type, null);
    }

    /**
     * 解析
     *
     * @param type
     * @param defaultType
     * @return
     */
    public static MemberSexEnum parse(String type, MemberSexEnum defaultType) {
        if (null != type && !type.isEmpty()) {
            return MemberSexEnum.valueOf(type.toUpperCase());
        }
        return defaultType;
    }
}
