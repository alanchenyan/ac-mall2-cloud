package com.ac.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "APP短信登录VO")
public class MemberLoginMsgCodeVO extends MemberLoginBaseVO {
    @ApiModelProperty(value = "全球区号")
    String globalCode = "86";

    @ApiModelProperty(value = "电话号码", required = true)
    @NotNull(message = "电话号码不能为空")
    String mobile;

    @ApiModelProperty(value = "短信验证码", required = true, example = "151109")
    @NotNull(message = "短信验证码不能为空")
    String msgCode;
}