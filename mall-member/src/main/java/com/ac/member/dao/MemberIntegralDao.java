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
     *
     * @param memberId
     */
    void freshTotalIntegral(Long memberId);
}
