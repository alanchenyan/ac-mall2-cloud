package com.ac.member.service.impl;

import com.ac.member.convert.MemberConvert;
import com.ac.member.dao.MemberDao;
import com.ac.member.dto.MemberDTO;
import com.ac.member.entity.Member;
import com.ac.member.qry.MemberQry;
import com.ac.member.rds.MemberRds;
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
    private MemberDao memberDaoImpl;

    @Resource
    private MemberRds memberRds;

    @Override
    public Member findById(Long id) {
        return Optional.ofNullable(memberDaoImpl.getById(id)).orElseThrow(() -> new RuntimeException("数据不存在"));
    }

    @Override
    public MemberDTO findMember(Long id) {
        Member entity = memberRds.get(id);
        if (entity == null) {
            entity = findById(id);
            memberRds.save(entity);
            log.info("从数据库查数据,id={}", id);
        }
        return MemberConvert.instance.entityToDto(entity);
    }

    @Override
    public Boolean addMember(MemberEditVO editVO) {
        Member entity = MemberConvert.instance.editVoToEntity(editVO);
        boolean result = memberDaoImpl.save(entity);
        if (result) {
            memberRds.save(entity);
        }
        return result;
    }

    @Override
    public List<MemberDTO> listMember(MemberQry qry) {
        return memberDaoImpl.listMember(qry);
    }
}
