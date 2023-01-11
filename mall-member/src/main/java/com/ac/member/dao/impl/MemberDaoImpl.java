package com.ac.member.dao.impl;

import com.ac.member.dao.MemberDao;
import com.ac.member.dto.MemberDTO;
import com.ac.member.entity.Member;
import com.ac.member.mapper.MemberMapper;
import com.ac.member.qry.MemberQry;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Repository
public class MemberDaoImpl extends ServiceImpl<MemberMapper, Member> implements MemberDao {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public List<MemberDTO> listMember(MemberQry qry) {
        return memberMapper.listMember(qry);
    }
}
