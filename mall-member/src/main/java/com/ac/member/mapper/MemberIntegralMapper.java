package com.ac.member.mapper;

import com.ac.member.entity.MemberIntegral;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Alan Chen
 * @description 用户积分-数据访问层(依赖mybatis-plus)，对应mapper.xml里的自定义sql
 * @date 2022/12/29
 */
public interface MemberIntegralMapper extends BaseMapper<MemberIntegral> {

    /**
     * 刷新用户总积分
     *
     * @param memberId
     */
    void freshTotalIntegral(@Param("memberId") Long memberId);
}
