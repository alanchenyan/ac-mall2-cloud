package com.ac.member.dao.impl;

import com.ac.member.dao.MemberIntegralDao;
import com.ac.member.entity.MemberIntegral;
import com.ac.member.mapper.MemberIntegralMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Slf4j
@Repository
public class MemberIntegralDaoImpl extends ServiceImpl<MemberIntegralMapper, MemberIntegral> implements MemberIntegralDao {

    @Resource
    private MemberIntegralMapper memberIntegralMapper;

    @Override
    public void freshTotalIntegral(Long memberId) {
        memberIntegralMapper.freshTotalIntegral(memberId);
    }

    @Override
    public Boolean existsMemberIntegral(Long memberId) {
        LambdaQueryWrapper<MemberIntegral> wrapper = new LambdaQueryWrapper();
        wrapper.eq(MemberIntegral::getMemberId, memberId);
        return memberIntegralMapper.exists(wrapper);
    }
}
