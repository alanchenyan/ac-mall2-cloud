package com.ac.auth.vo;

import com.ac.auth.enums.MemberSocialTypeEnum;
import com.ac.auth.enums.PlatformEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户信息表(Member)VO")
public class MemberAddSocialVO {

    @ApiModelProperty(value = "平台类型")
    PlatformEnum platform;

    @ApiModelProperty(value = "手机号码")
    String mobile;

    @ApiModelProperty(value = "昵称")
    @NotNull(message = "昵称不能为空")
    String nickName;

    @ApiModelProperty(value = "用户的个性说明")
    String displayNote;

    @ApiModelProperty(value = "用户头像地址")
    @NotNull(message = "用户头像地址不能为空")
    String iconUrl;

    @ApiModelProperty(value = "第三方登录类型")
    @NotNull(message = "第三方登录类型不能为空")
    MemberSocialTypeEnum socialType;

    @ApiModelProperty(value = "第三方登录ID")
    @NotNull(message = "第三方登录ID不能为空")
    String uid;

    @ApiModelProperty(value = "ip")
    @NotNull(message = "ip不能为空")
    private String ip;
}