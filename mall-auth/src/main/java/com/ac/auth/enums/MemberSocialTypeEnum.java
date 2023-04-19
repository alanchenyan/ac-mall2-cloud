package com.ac.auth.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel(description = "会员第三方绑定类型枚举")
public enum MemberSocialTypeEnum {
    WE_CHAT("WE_CHAT", "微信开放平台"),
    WE_CHAT_MP("WE_CHAT_MP", "微信公众平台"),
    WE_CHAT_MINI("WE_CHAT_MINI", "微信小程序"),
    QQ("QQ", "QQ"),
    WEIBO("WEIBO", "微博"),
    ALI_PAY("ALI_PAY", "支付宝"),
    ALI_PAY_MINI("ALI_PAY_MINI", "支付宝小程序"),
    BYTE_MINI("BYTE_MINI", "字节小程序"),
    BAIDU_MINI("BAIDU_MINI", "百度小程序"),
    APPLE("APPLE", "苹果");

    @JsonValue
    @EnumValue
    private String code;
    private String name;

    public static MemberSocialTypeEnum parse(String type) {
        return parse(type, null);
    }

    public static MemberSocialTypeEnum parse(String type, MemberSocialTypeEnum dau) {
        if (null != type && !type.isEmpty()) {
            try {
                return MemberSocialTypeEnum.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return dau;
    }
}
