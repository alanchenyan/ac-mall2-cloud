package com.ac.member.service;

/**
 * @author Alan Chen
 * @description 用户积分-业务层
 * @date 2023/02/22
 */
public interface MemberIntegralService {

    /**
     * 刷新用户总积分
     * 备注：采用了一条SQL直接更新用户总积分的方式，而不是在service里分两步来操作(并发下有数据不一致的问题)
     *
     * @param memberId
     */
    void freshTotalIntegral(Long memberId);
}
