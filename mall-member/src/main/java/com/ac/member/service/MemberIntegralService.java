package com.ac.member.service;

/**
 * @author Alan Chen
 * @description 用户积分-业务层
 * @date 2023/02/22
 */
public interface MemberIntegralService {

    /**
     * 更新用户总积分
     *
     * @param memberId
     */
    void updateTotalIntegral(Long memberId);

}
