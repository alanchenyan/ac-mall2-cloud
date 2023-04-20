package com.ac.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public abstract class MemberLoginBaseVO {

    @ApiModelProperty(value = "版本", required = true, example = "1.0.0")
    @NotNull(message = "版本不能为空")
    String version;

    @ApiModelProperty(value = "登录设备(机型)", example = "Iphone12 pro max 512G 磨砂金版")
    String device;

    @ApiModelProperty(value = "iemi", example = "862856041735598")
    String iemi;

    @ApiModelProperty(value = "经度", example = "113.530801")
    String longi;

    @ApiModelProperty(value = "纬度", example = "22.160957")
    Double lati;

    @ApiModelProperty(value = "IP地址", example = "119.146.178.178")
    String ip;

    @ApiModelProperty(value = "登录平台（IOS/ANDROID/H5）", required = true, example = "OTHER")
    @NotNull(message = "登录平台不能为空")
    String platform;

    @ApiModelProperty(value = "登录地址", example = "珠海")
    String location;

    @ApiModelProperty(value = "客户端类型（app、web,H5）", example = "app")
    String clientId = "app";

    @ApiModelProperty(value = "推荐码", example = "437487")
    String recommendCode;
}
