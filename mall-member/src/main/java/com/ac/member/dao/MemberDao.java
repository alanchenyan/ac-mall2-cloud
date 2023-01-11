package com.ac.member.dao;

import com.ac.member.dto.MemberDTO;
import com.ac.member.entity.Member;
import com.ac.member.qry.MemberQry;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Alan Chen
 * @description 用户-数据访问层(依赖mybatis-plus),重用mybatis-service提供的CURD方法
 * @date 2022/12/29
 */
public interface MemberDao extends IService<Member> {

    /**
     * 查询用户列表
     *
     * @param qry
     * @return
     */
    List<MemberDTO> listMember(MemberQry qry);
}
