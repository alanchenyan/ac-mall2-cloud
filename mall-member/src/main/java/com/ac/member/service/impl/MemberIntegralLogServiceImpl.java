package com.ac.member.service.impl;

import com.ac.member.dao.MemberIntegralLogDao;
import com.ac.member.entity.MemberIntegralLog;
import com.ac.member.service.MemberIntegralLogService;
import com.ac.member.vo.IntegralLogEditVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class MemberIntegralLogServiceImpl implements MemberIntegralLogService {

    @Resource
    private MemberIntegralLogDao memberIntegralLogDaoImpl;

    @Override
    public void addIntegral(IntegralLogEditVO logEditVO) {
        MemberIntegralLog entity = new MemberIntegralLog();
        entity.setMemberId(logEditVO.getMemberId());
        entity.setIntegral(logEditVO.getIntegral());
        entity.setSourceType(logEditVO.getSourceType());
        memberIntegralLogDaoImpl.save(entity);
    }
}
