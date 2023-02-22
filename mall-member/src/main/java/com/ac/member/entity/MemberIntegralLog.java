package com.ac.member.entity;

import com.ac.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Alan Chen
 * @description 用户积分明细
 * @date 2023/02/22
 */
@Data
@TableName("t_member_integral_log")
public class MemberIntegralLog extends BaseEntity {

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("积分")
    private Long integral;

    @ApiModelProperty("积分来源类型(下单奖励积分/签到积分)")
    private String sourceType;
}
