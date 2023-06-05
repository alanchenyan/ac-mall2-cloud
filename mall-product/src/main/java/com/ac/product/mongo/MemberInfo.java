package com.ac.product.mongo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Alan Chen
 * @description 用户信息
 * @date 2023/02/22
 */
@Data
public class MemberInfo {

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("用户姓名")
    private String memberName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("性别")
    private String sex;
}
