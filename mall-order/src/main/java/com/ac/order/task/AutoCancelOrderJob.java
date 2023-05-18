package com.ac.order.task;

import cn.hutool.core.util.StrUtil;
import com.ac.common.constant.XXLJobHandlerConstant;
import com.ac.core.util.DateUtil;
import com.ac.order.cmd.AddDefaultXxlJobCmd;
import com.ac.order.component.XxlJobComponent;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Alan Chen
 * @description 订单未支付倒计时关闭
 * @date 2023/05/17
 */
@Slf4j
@Component
public class AutoCancelOrderJob {

    @Resource
    private XxlJobComponent xxlJobComponent;

    /**
     * 订单未付款自动关闭15分钟倒计时
     *
     * @param orderNo
     */
    public void addJob(String orderNo) {
        try {
            log.info("AutoCancelOrderJob.addJob,orderNo={}", orderNo);

            String executorParam = orderNo;

            LocalDateTime now = LocalDateTime.now();
            //6小时后执行
            LocalDateTime offset = DateUtil.offset(now, 15, ChronoUnit.MINUTES);
            String scheduleConf = DateUtil.getCron(cn.hutool.core.date.DateUtil.date(offset));

            AddDefaultXxlJobCmd cmd = AddDefaultXxlJobCmd.builder()
                    .appName("executor-order")
                    .jobDesc("订单未付款自动关闭15分钟倒计时")
                    .scheduleConf(scheduleConf)
                    .executorHandler(XXLJobHandlerConstant.AUTO_CANCEL_ORDER)
                    .executorParam(executorParam)
                    .build();
            xxlJobComponent.addAndStartJob(cmd);
        } catch (Exception e) {
            log.info("AutoCancelOrderJob.addJob,启动任务失败,msg={}", e.getMessage());
        }
    }

    @XxlJob(value = XXLJobHandlerConstant.AUTO_CANCEL_ORDER)
    public void doJob() {
        try {
            // 获取任务ID
            long jobId = XxlJobHelper.getJobId();
            log.info("AutoCancelOrderJob.doJob,jobId={},param={}", jobId, XxlJobHelper.getJobParam());

            // 获取任务参数
            String orderNo = XxlJobHelper.getJobParam();
            // 业务逻辑

            log.info("AutoCancelOrderJob.doJob,关闭订单,orderNo={}", orderNo);

            String logInfo = StrUtil.format("AutoCancelOrderJob.doJob,成功关闭订单,orderNo={}", orderNo);
            log.info(logInfo);
            XxlJobHelper.handleSuccess(logInfo);
        } catch (Exception e) {
            String error = StrUtil.format("AutoCancelOrderJob.doJob,失败, msg={}", e.getMessage());
            log.info(error);
            XxlJobHelper.handleFail(error);
        }
    }
}
