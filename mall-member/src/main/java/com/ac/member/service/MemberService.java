package com.ac.member.service;

import com.ac.member.dto.MemberDTO;
import com.ac.member.entity.Member;
import com.ac.member.qry.MemberQry;
import com.ac.member.vo.MemberEditVO;

import java.util.List;

/**
 * @author Alan Chen
 * @description 用户-业务层
 * @date 2022/12/29
 */
public interface MemberService {

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    Member findById(Long id);

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    MemberDTO findMember(Long id);

    /**
     * 新增用户
     *
     * @param editVO
     * @return
     */
    Boolean addMember(MemberEditVO editVO);

    /**
     * 查询用户列表
     *
     * @param qry
     * @return
     */
    List<MemberDTO> listMember(MemberQry qry);
}
