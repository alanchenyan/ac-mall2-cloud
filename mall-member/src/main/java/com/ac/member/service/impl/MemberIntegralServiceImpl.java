package com.ac.member.service.impl;

import com.ac.member.dao.MemberIntegralDao;
import com.ac.member.service.MemberIntegralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class MemberIntegralServiceImpl implements MemberIntegralService {

    @Resource
    private MemberIntegralDao memberIntegralDaoImpl;

    @Override
    public void freshTotalIntegral(Long memberId) {
        memberIntegralDaoImpl.freshTotalIntegral(memberId);
    }
}
