package com.ac.member.service.impl;

import com.ac.member.convert.MemberConvert;
import com.ac.member.dao.MemberDao;
import com.ac.member.dto.MemberDTO;
import com.ac.member.entity.Member;
import com.ac.member.qry.MemberQry;
import com.ac.member.service.MemberService;
import com.ac.member.vo.MemberEditVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberDao memberDao;

    @Override
    public Member findById(Long id) {
        return Optional.ofNullable(memberDao.getById(id)).orElseThrow(() -> new RuntimeException("数据不存在"));
    }

    @Override
    public Boolean addMember(MemberEditVO editVO) {
        Member entity = MemberConvert.instance.editVoToEntity(editVO);
        return memberDao.save(entity);
    }

    @Override
    public List<MemberDTO> listMember(MemberQry qry) {
        return memberDao.listMember(qry);
    }
}
