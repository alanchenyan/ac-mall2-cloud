package com.ac.member.dao;

import com.ac.member.entity.MemberIntegral;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Alan Chen
 * @description 用户积分-数据访问层(依赖mybatis-plus),重用mybatis-service提供的CURD方法
 * @date 2022/12/29
 */
public interface MemberIntegralDao extends IService<MemberIntegral> {

    /**
     * 刷新用户总积分
     * 备注：采用了一条SQL直接更新用户总积分的方式，而不是在service里分两步来操作(并发下有数据不一致的问题)
     *
     * @param memberId
     */
    void freshTotalIntegral(Long memberId);

    /**
     * 是否存在记录
     *
     * @param memberId
     * @return
     */
    Boolean existsMemberIntegral(Long memberId);
}
