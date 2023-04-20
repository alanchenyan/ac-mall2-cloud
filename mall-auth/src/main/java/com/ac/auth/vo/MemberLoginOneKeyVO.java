package com.ac.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemberLoginOneKeyVO extends MemberLoginBaseVO {
    @ApiModelProperty(value = "一键登录token")
    @NotNull(message = "一键登录token不能为空")
    String token;
}