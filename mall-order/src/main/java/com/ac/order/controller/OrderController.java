package com.ac.order.controller;

import com.ac.common.constant.XXLJobHandlerConstant;
import com.ac.core.util.DateUtil;
import com.ac.order.cmd.AddDefaultXxlJobCmd;
import com.ac.order.component.XxlJobComponent;
import com.ac.order.dto.OrderDTO;
import com.ac.order.qry.OrderPageQry;
import com.ac.order.service.OrderService;
import com.ac.order.vo.OrderAddVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Api(tags = "订单")
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderServiceImpl;

    @Resource
    private XxlJobComponent xxlJobComponent;

    @ApiOperation(value = "新增订单")
    @PostMapping
    public Long createOrder(@RequestBody @Valid OrderAddVO addVO) {
        return orderServiceImpl.createOrder(addVO);
    }

    @ApiOperation(value = "分页查询订单")
    @GetMapping("page")
    public IPage<OrderDTO> pageOrder(@Valid OrderPageQry qry) {
        return orderServiceImpl.pageOrder(qry);
    }

    @ApiOperation(value = "xxl-job登录")
    @GetMapping("login")
    public String login() {
        return xxlJobComponent.login();
    }

    @ApiOperation(value = "xxl-job获取cookie")
    @GetMapping("getCookie")
    public String getCookie() {
        return xxlJobComponent.getCookie();
    }

    @ApiOperation(value = "xxl-job创建任务")
    @GetMapping("addJob")
    public boolean addJob(@RequestParam String memberId, @RequestParam String memberName) {
        String executorParam = memberId + "," + memberName;

        LocalDateTime now = LocalDateTime.now();
        //6小时后执行
        LocalDateTime offset = DateUtil.offset(now, 6, ChronoUnit.HOURS);
        String scheduleConf = DateUtil.getCron(cn.hutool.core.date.DateUtil.date(offset));

        AddDefaultXxlJobCmd cmd = AddDefaultXxlJobCmd.builder()
                .jobGroup(3)  // 执行器管理的ID，在xxl-job-admin管理平台通过F2查看
                .jobDesc("订单未付款自动关闭1小时倒计时")
                .scheduleConf(scheduleConf)
                .executorHandler(XXLJobHandlerConstant.TASK_BY_DYNAMIC_CREATE)
                .executorParam(executorParam)
                .build();
        return xxlJobComponent.addAndStartJob(cmd);
    }
}
