package com.ac.common.apilog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "api请求日志记录")
public class ApiLog {

    @ApiModelProperty(value = "接口耗时")
    private Long timeCost;

    @ApiModelProperty(value = "用户ID")
    private String memberId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "APP版本")
    private String appVersion;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "http方法 GET POST PUT DELETE PATCH")
    private String httpMethod;

    @ApiModelProperty(value = "类方法")
    private String classMethod;

    @ApiModelProperty(value = "请求参数")
    private Object requestParams;

    @ApiModelProperty(value = "返回参数")
    private Object result;

    @ApiModelProperty(value = "线程ID")
    private String threadId;

    @ApiModelProperty(value = "线程名称")
    private String threadName;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "是否为移动平台")
    private boolean isMobile;

    @ApiModelProperty(value = "浏览器类型")
    private String browser;

    @ApiModelProperty(value = "平台类型")
    private String platform;

    @ApiModelProperty(value = "系统类型")
    private String os;

    @ApiModelProperty(value = "引擎类型")
    private String engine;

    @ApiModelProperty(value = "浏览器版本")
    private String browserVersion;

    @ApiModelProperty(value = "引擎版本")
    private String engineVersion;
}
