package com.ac.member.component;

import com.ac.member.service.MemberIntegralLogService;
import com.ac.member.service.MemberIntegralService;
import com.ac.member.vo.IntegralLogEditVO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class MemberIntegralComponent {

    @Resource
    private MemberIntegralLogService memberIntegralLogServiceImpl;

    @Resource
    private MemberIntegralService memberIntegralServiceImpl;

    /**
     * 记录积分
     *
     * @param logEditVO
     */
    @Transactional
    public void recordIntegral(IntegralLogEditVO logEditVO) {
        memberIntegralLogServiceImpl.addIntegral(logEditVO);
        memberIntegralServiceImpl.freshTotalIntegral(logEditVO.getMemberId());
    }
}
