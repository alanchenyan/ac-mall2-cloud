package com.ac.member.vo;

import com.ac.member.enums.IntegralSourceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IntegralLogEditVO {

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("积分（加积分用正数，减积分用负数）")
    private Long integral;

    @ApiModelProperty("积分来源类型(下单奖励/签到奖励)")
    private IntegralSourceTypeEnum sourceType;

    @ApiModelProperty("积分来源描述（2023-02-23下单获得积分）")
    private String sourceRemark;
}
