package com.ac.order.dao;

import com.ac.order.entity.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Alan Chen
 * @description 订单项-数据访问层(依赖mybatis-plus),重用mybatis-service提供的CURD方法
 * @date 2022/12/29
 */
public interface OrderItemDao extends IService<OrderItem> {

}
