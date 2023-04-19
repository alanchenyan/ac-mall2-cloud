package com.ac.auth.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel(description = "客户端平台类型枚举")
public enum PlatformEnum {
    WINDOW("WINDOW", "windows"),
    WEB("WEB", "web"),
    H5("H5", "h5"),
    ANDROID("ANDROID", "安卓"),
    IOS("IOS", "iOS"),
    MINI_WX("MINI_WX", "微信小程序"),
    MINI_ALI("MINI_ALI", "支付宝小程序"),
    MINI_BYTE("MINI_BYTE", "字节小程序"),
    MINI_BAIDU("MINI_BAIDU", "百度小程序"),
    OTHER("OTHER", "其他");

    @EnumValue
    @JsonValue
    private String code;
    private String name;

    public static PlatformEnum parse(String type) {
        return parse(type, null);
    }

    public static PlatformEnum parse(String type, PlatformEnum dau) {
        if (null != type && !type.isEmpty()) {
            try {
                return PlatformEnum.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return dau;
    }
}
