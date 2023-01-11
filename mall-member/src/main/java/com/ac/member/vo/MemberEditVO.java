package com.ac.member.vo;

import com.ac.member.enums.MemberSexEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class MemberEditVO {

    @Length(max = 10, message = "姓名最长10个字")
    @ApiModelProperty("用户姓名")
    private String memberName;

    @ApiModelProperty("手机号")
    private String mobile;

    @NotNull(message = "请选择性别")
    @ApiModelProperty("性别")
    private MemberSexEnum sex;

    @ApiModelProperty("生日")
    private LocalDate birthday;
}
