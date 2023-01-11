package com.ac.member.qry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberQry {

    @ApiModelProperty("用户姓名")
    private String memberName;

    @ApiModelProperty("手机号")
    private String mobile;
}
