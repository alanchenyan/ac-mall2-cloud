package com.ac.order.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ac.common.xxljob.XxlJobGroup;
import com.ac.common.xxljob.XxlJobInfo;
import com.ac.order.cmd.AddDefaultXxlJobCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class XxlJobComponent {

    private final Map<String, String> loginCookie = new HashMap<>();

    private final String adminAddresses = "http://127.0.0.1:8081/xxl-job-admin";
    private final String username = "admin";
    private final String password = "123456";

    /**
     * 创建定时定时任务并启动
     *
     * @param cmd
     * @return
     */
    public boolean addAndStartJob(AddDefaultXxlJobCmd cmd) {
        int jobGroup = getJobGroupId(cmd.getAppName());
        if (jobGroup == -1) {
            log.error("获取执行管理器ID失败,appName={}", cmd.getAppName());
            return false;
        }

        cmd.setJobGroup(jobGroup);
        XxlJobInfo jobInfo = convertDefaultJobInfo(cmd);

        //创建定时任务
        Integer id = this.addJobInfo(jobInfo);
        if (id == -1) {
            log.error("创建定时任务失败,cmd={}", cmd);
            return false;
        }
        //启动定时任务
        return this.startJob(id);
    }

    /**
     * xxl-job登录
     *
     * @return
     */
    public String login() {
        String url = adminAddresses + "/login";
        HttpResponse response = HttpRequest.post(url)
                .form("userName", username)
                .form("password", password)
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        Optional<HttpCookie> cookieOpt = cookies.stream()
                .filter(cookie -> cookie.getName().equals("XXL_JOB_LOGIN_IDENTITY")).findFirst();
        if (!cookieOpt.isPresent())
            throw new RuntimeException("get xxl-job cookie error!");

        String value = cookieOpt.get().getValue();
        loginCookie.put("XXL_JOB_LOGIN_IDENTITY", value);

        log.info("XxlJobComponent.login.token={}", value);
        return value;
    }

    /**
     * 其他接口在调用时，直接从缓存中获取cookie，如果缓存中不存在则调用/login接口，为了避免这一过程失败，允许最多重试3次
     *
     * @return
     */
    public String getCookie() {
        for (int i = 0; i < 3; i++) {
            String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
            if (cookieStr != null) {
                return "XXL_JOB_LOGIN_IDENTITY=" + cookieStr;
            }
            login();
        }
        throw new RuntimeException("get xxl-job cookie error!");
    }

    /**
     * 获取执行管理器ID
     *
     * @param appName
     * @return
     */
    private int getJobGroupId(String appName) {
        List<XxlJobGroup> jobGroupList = listJobGroup(appName);
        if (CollectionUtil.isEmpty(jobGroupList)) {
            return -1;
        }
        return jobGroupList.get(0).getId();
    }

    /**
     * 获取执行管理器列表
     *
     * @param appName
     * @return
     */
    private List<XxlJobGroup> listJobGroup(String appName) {
        String url = adminAddresses + "/jobgroup/pageList";
        HttpResponse response = HttpRequest.post(url)
                .form("appname", appName)
                .cookie(getCookie())
                .execute();

        String body = response.body();
        JSONArray array = JSONUtil.parse(body).getByPath("data", JSONArray.class);
        List<XxlJobGroup> list = array.stream()
                .map(o -> JSONUtil.toBean((JSONObject) o, XxlJobGroup.class))
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 启动定时任务
     *
     * @param jobId
     * @return
     */
    private boolean startJob(Integer jobId) {
        String url = adminAddresses + "/jobinfo/start";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", jobId);

        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(getCookie())
                .execute();

        JSON json = JSONUtil.parse(response.body());
        Object code = json.getByPath("code");
        return code.equals(200);
    }

    /**
     * 创建定时任务
     *
     * @param xxlJobInfo
     * @return
     */
    private Integer addJobInfo(XxlJobInfo xxlJobInfo) {
        String url = adminAddresses + "/jobinfo/add";
        Map<String, Object> paramMap = BeanUtil.beanToMap(xxlJobInfo);
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(getCookie())
                .execute();

        JSON json = JSONUtil.parse(response.body());
        Object code = json.getByPath("code");
        if (code.equals(200)) {
            Object content = json.getByPath("content");
            if (content == null) {
                return -1;
            }
            return Integer.valueOf((String) content);
        }
        log.info("创建定时任务失败");
        return -1;
    }

    /**
     * 定时任务对象转换
     *
     * @param cmd
     * @return
     */
    private XxlJobInfo convertDefaultJobInfo(AddDefaultXxlJobCmd cmd) {
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
