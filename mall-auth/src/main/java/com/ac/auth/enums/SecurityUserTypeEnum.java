package com.ac.auth.enums;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "登录用户类型枚举")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SecurityUserTypeEnum {
    ADMIN("运营平台用户"),
    APP("APP用户");

    private String name;
}
