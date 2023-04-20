package com.ac.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "APP密码登录VO")
public class MemberLoginPwdVO extends MemberLoginBaseVO {

    @ApiModelProperty(value = "电话号码", required = true)
    @NotNull(message = "电话号码不能为空")
    String mobile;

    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "密码不能为空")
    String password;
}