package com.ac.member.service;

import com.ac.member.vo.IntegralLogEditVO;

/**
 * @author Alan Chen
 * @description 用户积分明细-业务层
 * @date 2023/02/22
 */
public interface MemberIntegralLogService {

    /**
     * 新增积分
     *
     * @param logEditVO
     */
    void addIntegral(IntegralLogEditVO logEditVO);
}
