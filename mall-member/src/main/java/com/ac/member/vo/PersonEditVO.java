package com.ac.member.vo;

import com.ac.common.validation.action.AddAction;
import com.ac.common.validation.action.EditAction;
import com.ac.common.validation.validator.mobile.Mobile;
import com.ac.common.validation.validator.mobile.MobilePattern;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PersonEditVO {

    @NotNull(message = "ID不能为空", groups = EditAction.class)
    @ApiModelProperty("ID")
    private Long id;

    @NotBlank(message = "用户姓名不能为空", groups = AddAction.class)
    @Length(max = 5, message = "姓名最长5个字")
    @ApiModelProperty("用户姓名")
    private String memberName;

    @NotBlank(message = "手机号不能为空", groups = AddAction.class)
    @Mobile(message = "手机号格式不正确", pattern = MobilePattern.MOBILE_PATTERN_ZH_CN, groups = {AddAction.class, EditAction.class})
    @ApiModelProperty("手机号")
    private String mobile;
}
