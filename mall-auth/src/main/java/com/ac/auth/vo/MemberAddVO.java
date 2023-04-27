package com.ac.auth.vo;


import com.ac.oauth2.enums.PlatformEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户信息添加VO")
public class MemberAddVO {

    @ApiModelProperty(value = "手机区号")
    String globalCode;

    @ApiModelProperty(value = "手机号码")
    String mobile;

    @ApiModelProperty(value = "昵称")
    @NotNull(message = "昵称不能为空")
    String nickName;

    @ApiModelProperty(value = "用户的个性说明")
    String displayNote;

    /** 用户头像地址 */
    @ApiModelProperty(value = "用户头像地址")
    @NotNull(message = "用户头像地址不能为空")
    String iconUrl;

    @ApiModelProperty(value = "注册平台")
    @NotNull(message = "注册平台不能为空")
    private PlatformEnum registerDevice;

    /** ip */
    @ApiModelProperty(value = "ip")
    @NotNull(message = "ip不能为空")
    private String ip;
}