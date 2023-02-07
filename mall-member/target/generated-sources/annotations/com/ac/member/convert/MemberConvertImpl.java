package com.ac.member.convert;

import com.ac.member.entity.Member;
import com.ac.member.vo.MemberEditVO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-07T15:56:51+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class MemberConvertImpl implements MemberConvert {

    @Override
    public Member editVoToEntity(MemberEditVO editVO) {
        if ( editVO == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberName( editVO.getMemberName() );
        member.setMobile( editVO.getMobile() );
        member.setSex( editVO.getSex() );
        member.setBirthday( editVO.getBirthday() );

        return member;
    }
}
