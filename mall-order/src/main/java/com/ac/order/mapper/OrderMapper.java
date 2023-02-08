package com.ac.order.mapper;

import com.ac.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Alan Chen
 * @description 订单-数据访问层(依赖mybatis-plus)，对应mapper.xml里的自定义sql
 * @date 2022/12/29
 */
public interface OrderMapper extends BaseMapper<Order> {

}
