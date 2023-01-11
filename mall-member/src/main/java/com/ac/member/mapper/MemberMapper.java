package com.ac.member.mapper;

import com.ac.member.dto.MemberDTO;
import com.ac.member.entity.Member;
import com.ac.member.qry.MemberQry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Alan Chen
 * @description 用户-数据访问层(依赖mybatis-plus)，对应mapper.xml里的自定义sql
 * @date 2022/12/29
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 查询用户
     *
     * @param qry
     * @return
     */
    List<MemberDTO> listMember(@Param("qry") MemberQry qry);
}
