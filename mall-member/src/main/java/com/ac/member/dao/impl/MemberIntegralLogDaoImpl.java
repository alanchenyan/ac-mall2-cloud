package com.ac.member.dao.impl;

import com.ac.member.dao.MemberIntegralLogDao;
import com.ac.member.entity.MemberIntegralLog;
import com.ac.member.mapper.MemberIntegralLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Slf4j
@Repository
public class MemberIntegralLogDaoImpl extends ServiceImpl<MemberIntegralLogMapper, MemberIntegralLog> implements MemberIntegralLogDao {

    @Resource
    private MemberIntegralLogMapper memberIntegralLogMapper;

}
