//package com.ac.order.task;
//
//import cn.hutool.core.util.StrUtil;
//import com.ac.common.constant.XXLJobHandlerConstant;
//import com.ac.core.util.DateUtil;
//import com.ac.member.cmd.AddDefaultXxlJobCmd;
//import com.ac.order.component.XxlJobComponent;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.time.temporal.TemporalUnit;
//
///**
// * @author Alan Chen
// * @description 动态创建定时任务
// * @date 2023/05/17
// */
//@Slf4j
//@Component
//public class TaskByDynamicCreateJob {
//
//    @Resource
//    private XxlJobComponent xxlJobComponent;
//
//    /**
//     * 创建任务
//     *
//     * @param memberId
//     * @param memberName
//     * @return
//     */
//    public void addJob(String memberId, String memberName) {
//        try {
//            log.info("TaskByDynamicCreateJob.addJob,memberId={},memberName={}", memberId, memberName);
//
//            String executorParam = memberId + "," + memberName;
//
//            LocalDateTime now = LocalDateTime.now();
//            //6小时后执行
//            LocalDateTime offset = DateUtil.offset(now, 6, ChronoUnit.HOURS);
//            String scheduleConf = DateUtil.getCron(cn.hutool.core.date.DateUtil.date(offset));
//
//            AddDefaultXxlJobCmd cmd = AddDefaultXxlJobCmd.builder()
//                    .jobGroup(2)  // 执行器管理的ID，在xxl-job-admin管理平台通过F2查看
//                    .jobDesc("6小时后打印用户名")
//                    .scheduleConf(scheduleConf)
//                    .executorHandler(XXLJobHandlerConstant.TASK_BY_DYNAMIC_CREATE)
//                    .executorParam(executorParam)
//                    .build();
//            xxlJobComponent.addAndStartJob(cmd);
//
//        } catch (Exception e) {
//            log.info("TaskByDynamicCreateJob.addJob,启动任务失败,msg={}", e.getMessage());
//        }
//    }
//
//    /**
//     * 日期偏移,根据field不同加不同值（偏移会修改传入的对象）
//     *
//     * @param time   {@link LocalDateTime}
//     * @param number 偏移量，正数为向后偏移，负数为向前偏移
//     * @param field  偏移单位，见{@link ChronoUnit}，不能为null
//     * @return 偏移后的日期时间
//     */
//    public static LocalDateTime offset(LocalDateTime time, long number, TemporalUnit field) {
//        if (null == time) {
//            return null;
//        }
//
//        return time.plus(number, field);
//    }
//
//
//    @XxlJob(value = XXLJobHandlerConstant.TASK_BY_DYNAMIC_CREATE)
//    public void doJob() {
//        try {
//            // 获取任务ID
//            long jobId = XxlJobHelper.getJobId();
//            log.info("TaskByDynamicCreateJob doJob,jobId={},param={}", jobId, XxlJobHelper.getJobParam());
//
//            // 获取任务参数
//            String[] params = StrUtil.splitToArray(XxlJobHelper.getJobParam(), ',');
//            if (params.length <= 1) {
//                String error = StrUtil.format("TaskByTimeJob.doJob,失败, 原因: 参数缺失, 任务ID: {}", jobId);
//                log.info(error);
//                XxlJobHelper.handleFail(error);
//                return;
//            }
//
//            // 业务逻辑
//            String memberId = params[0];
//            String memberName = params[1];
//            String logInfo = StrUtil.format("TaskByDynamicCreateJob.doJob,成功,memberId={},memberName={}", memberId, memberName);
//            log.info(logInfo);
//            XxlJobHelper.handleSuccess(logInfo);
//        } catch (Exception e) {
//            String error = StrUtil.format("TaskByDynamicCreateJob.doJob,失败, msg={}", e.getMessage());
//            log.info(error);
//            XxlJobHelper.handleFail(error);
//        }
//    }
//}
