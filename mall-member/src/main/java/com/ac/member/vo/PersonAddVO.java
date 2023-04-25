package com.ac.member.vo;

import com.ac.common.validation.action.AddAction;
import com.ac.common.validation.validator.mobile.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class PersonAddVO {

    @Length(max = 5, message = "姓名最长5个字")
    @ApiModelProperty("用户姓名")
    private String memberName;

    @NotNull
    @Mobile(message = "手机号格式不正确", groups = AddAction.class)
    @ApiModelProperty("手机号")
    private String mobile;
}
