package com.ac.auth.vo;

import com.ac.oauth2.enums.MemberSocialTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "APP第三方登录VO")
public class MemberLoginSocialVO extends MemberLoginBaseVO {
    @ApiModelProperty(value = "第三方平台类型", required = true)
    @NotNull(message = "第三方平台类型不能为空")
    private MemberSocialTypeEnum socialType;

    @ApiModelProperty(value = "第三方-uid", required = true)
    @NotNull(message = "第三方-uid不能为空")
    private String uid;

    @ApiModelProperty(value = "第三方-acc", required = true)
    @NotNull(message = "第三方-acc不能为空")
    private String acc;

    @ApiModelProperty(value = "第三方-头像", required = true)
    @NotNull(message = "第三方-头像不能为空")
    private String iconUrl;

    @ApiModelProperty(value = "第三方-昵称", required = true)
    @NotNull(message = "第三方-昵称不能为空")
    private String nickName;
}