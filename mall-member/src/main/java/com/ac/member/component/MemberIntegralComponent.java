package com.ac.member.component;

import cn.hutool.core.thread.ThreadUtil;
import com.ac.member.service.MemberIntegralLogService;
import com.ac.member.service.MemberIntegralService;
import com.ac.member.vo.IntegralLogEditVO;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Component
public class MemberIntegralComponent {

    @Resource
    private MemberIntegralLogService memberIntegralLogServiceImpl;

    @Resource
    private MemberIntegralService memberIntegralServiceImpl;

    @Resource
    private TransactionTemplate transactionTemplate;

    /**
     * 记录积分
     * 并发问题：出现死锁
     * 并发下相同的业务参数去执行，第一个事物还没提交后面的事物又来了，这种我们加分布式锁就好了
     *
     * @param logEditVO
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean recordIntegral(IntegralLogEditVO logEditVO) {
        //记录积分明细
        memberIntegralLogServiceImpl.addIntegral(logEditVO);
        //更新用户总积分
        memberIntegralServiceImpl.updateTotalIntegral(logEditVO.getMemberId());
        return true;
    }
}
