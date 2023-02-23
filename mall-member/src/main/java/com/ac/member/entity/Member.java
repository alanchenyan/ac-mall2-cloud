package com.ac.member.entity;

import com.ac.common.base.BaseEntity;
import com.ac.member.enums.MemberSexEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Alan Chen
 * @description 用户
 * @date 2023/02/22
 */
@Data
@TableName("t_member")
public class Member extends BaseEntity {

    @ApiModelProperty("用户姓名")
    private String memberName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("性别")
    private MemberSexEnum sex;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @ApiModelProperty("用户总积分")
    private Long integral;
}
