package com.ac.order.dao;

import com.ac.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Alan Chen
 * @description 订单-数据访问层(依赖mybatis-plus),重用mybatis-service提供的CURD方法
 * @date 2022/12/29
 */
public interface OrderDao extends IService<Order> {

}
