package com.ac.auth.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SmsBuzTypeEnum {
    LOGIN_CODE("LOGIN_CODE", "登录验证码"),
    MU_CODE("MU_CODE", "修改手机号验证码"),
    MB_CODE("MB_CODE", "绑定手机号验证码"),
    REGISTER_CODE("REGISTER_CODE", "注册验证码"),
    INVITE("INVITE", "邀请短信"),
    PAY("PAY", "支付验证短信"),
    ;

    @JsonValue
    @EnumValue
    private String code;
    private String name;

    public static String parseLower(SmsBuzTypeEnum type) {
        return type.getCode().toLowerCase();
    }

}

