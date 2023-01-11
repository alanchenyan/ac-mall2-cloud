package com.ac.member.convert;

import com.ac.member.entity.Member;
import com.ac.member.vo.MemberEditVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberConvert {

    MemberConvert instance = Mappers.getMapper(MemberConvert.class);

    /**
     * vo转entity
     *
     * @param editVO
     * @return
     */
    Member editVoToEntity(MemberEditVO editVO);
}
