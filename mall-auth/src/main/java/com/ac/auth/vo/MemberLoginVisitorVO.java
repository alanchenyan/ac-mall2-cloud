package com.ac.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "匿名登录VO")
public class MemberLoginVisitorVO extends MemberLoginBaseVO {

    @ApiModelProperty("地址省name")
    private String provinceName;

    @ApiModelProperty("地址市name")
    private String cityName;

    @ApiModelProperty("地址县(区)name")
    private String countyName;

    @ApiModelProperty("地址镇name")
    private String townName;
}