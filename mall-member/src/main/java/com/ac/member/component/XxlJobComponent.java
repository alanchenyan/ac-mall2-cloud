package com.ac.member.component;

import com.ac.member.cmd.AddDefaultXxlJobCmd;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.service.XxlJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class XxlJobComponent {

    @Resource
    private XxlJobService xxlJobService;

    public void addAndStartJob(AddDefaultXxlJobCmd cmd) {
        XxlJobInfo jobInfo = convertXxlJobInfo(cmd);
        try {
            xxlJobService.add(jobInfo);
        } catch (Exception e) {
            log.error("创建定时任务失败");
            e.printStackTrace();
        }

        if (jobInfo.getId() > 0) {
            xxlJobService.start(jobInfo.getId());
        }
    }

    private XxlJobInfo convertXxlJobInfo(AddDefaultXxlJobCmd cmd) {
        XxlJobInfo jobInfo = new XxlJobInfo();
        /*基础配置*/
        jobInfo.setJobGroup(cmd.getJobGroup());
        jobInfo.setJobDesc(cmd.getJobDesc());
        jobInfo.setAuthor("SYSTEM");
        jobInfo.setAlarmEmail("test.126.com");
        //调度配置
        jobInfo.setScheduleType("CRON");
        jobInfo.setScheduleConf(cmd.getScheduleConf());
        //任务配置
        jobInfo.setGlueType("BEAN");
        jobInfo.setExecutorHandler(cmd.getExecutorHandler());
        jobInfo.setExecutorParam(cmd.getExecutorParam());

        /*高级配置*/
        //路由策略
        jobInfo.setExecutorRouteStrategy("CONSISTENT_HASH");
        //调度过期策略 DO_NOTHING忽略 FIRE_ONCE_NOW立即执行一次
        jobInfo.setMisfireStrategy("FIRE_ONCE_NOW");
        //阻塞处理策略
        jobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");

        return jobInfo;
    }
}
