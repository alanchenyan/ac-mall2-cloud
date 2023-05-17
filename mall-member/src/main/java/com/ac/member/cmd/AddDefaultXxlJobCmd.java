package com.ac.member.cmd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zak
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AddDefaultXxlJobCmd {

    @ApiModelProperty("执行器主键ID")
    private int jobGroup;

    @ApiModelProperty("任务描述")
    private String jobDesc;

    @ApiModelProperty("cron表达式")
    private String scheduleConf;

    @ApiModelProperty("执行器任务Handler名称")
    private String executorHandler;

    @ApiModelProperty("执行器任务参数")
    private String executorParam;
}