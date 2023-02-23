package com.ac.member.service.impl;

import com.ac.member.dao.MemberIntegralDao;
import com.ac.member.entity.MemberIntegral;
import com.ac.member.service.MemberIntegralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class MemberIntegralServiceImpl implements MemberIntegralService {

    @Resource
    private MemberIntegralDao memberIntegralDaoImpl;

    /**
     * 此方法有并发问题，需要在外面加分布式锁
     *
     * @param memberId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTotalIntegral(Long memberId) {
        if (!memberIntegralDaoImpl.existsMemberIntegral(memberId)) {
            MemberIntegral defaultEntity = new MemberIntegral();
            defaultEntity.setMemberId(memberId);
            defaultEntity.setTotalIntegral(0L);
            memberIntegralDaoImpl.save(defaultEntity);
        }
        memberIntegralDaoImpl.freshTotalIntegral(memberId);
    }
}
