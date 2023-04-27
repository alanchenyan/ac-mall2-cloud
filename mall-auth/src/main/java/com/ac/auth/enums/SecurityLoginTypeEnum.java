package com.ac.auth.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "登录方式类型枚举")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SecurityLoginTypeEnum {
    APP_SMS("APP_SMS", "app验证码登录"),
    APP_ONE_KEY("APP_ONE_KEY", "app一键登录"),
    APP_PWD("APP_PWD", "app密码登录"),
    APP_SOCIAL("APP_SOCIAL", "app第三方登录"),
    APP_ANONYMOUS("APP_ANONYMOUS", "匿名登录"),
    ADMIN_PWD("ADMIN_PWD", "运营中心密码登录"),
    APP_QR_CODE("APP_QR_CODE", "扫码登录"),
    OTHER("OTHER", "其他");

    @EnumValue
    @JsonValue
    private String code;
    private String name;

    public static SecurityLoginTypeEnum parse(String type) {
        return parse(type, null);
    }

    public static SecurityLoginTypeEnum parse(String type, SecurityLoginTypeEnum dau) {
        if (null != type && !type.isEmpty()) {
            try {
                return SecurityLoginTypeEnum.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
            }
        }
        return dau;
    }
}
