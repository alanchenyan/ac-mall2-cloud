package com.ac.member.task;

import cn.hutool.core.util.StrUtil;
import com.ac.common.constant.XXLJobHandlerConstant;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskByTimeJob {

    @XxlJob(value = XXLJobHandlerConstant.PRINT_NAME_BY_TIME)
    public void doJob() {
        try {
            // 获取任务ID
            long jobId = XxlJobHelper.getJobId();
            log.info("TaskByTimeJob doJob,jobId={},param={}", jobId, XxlJobHelper.getJobParam());

            // 获取任务参数
            String[] params = StrUtil.splitToArray(XxlJobHelper.getJobParam(), ',');
            if (params.length <= 1) {
                String logInfo = StrUtil.format("TaskByTimeJob,失败, 原因: 参数缺失, 任务ID: {}", jobId);
                log.info(logInfo);
                XxlJobHelper.handleSuccess(logInfo);
            }

            String memberId = params[0];
            String memberName = params[1];
            log.info("TaskByTimeJob.doJob,memberId={},memberName={}", memberId, memberName);
        } catch (Exception e) {
            log.info("执行定时任务失败,msg={}", e.getMessage());
        }
    }
}
