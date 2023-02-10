package com.ac.feign.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDTO {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("用户姓名")
    private String memberName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("生日")
    private LocalDate birthday;
}
