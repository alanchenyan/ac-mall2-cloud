package com.ac.member.entity;

import com.ac.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Alan Chen
 * @description 用户积分
 * @date 2023/02/22
 */
@Data
@TableName("t_member_integral")
public class MemberIntegral extends BaseEntity {

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("用户总积分")
    private Long totalIntegral;
}
