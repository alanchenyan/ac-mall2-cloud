package com.ac.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IntegralLogEditVO {

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("积分（加积分用正数，减积分用负数）")
    private Long integral;

    @ApiModelProperty("积分来源类型(下单奖励积分/签到积分)")
    private String sourceType;
}
