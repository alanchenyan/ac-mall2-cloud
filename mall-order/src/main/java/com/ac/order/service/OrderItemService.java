package com.ac.order.service;

import com.ac.order.entity.OrderItem;
import com.ac.order.vo.OrderItemAddVO;

/**
 * @author Alan Chen
 * @description 订单项-业务层
 * @date 2022/12/29
 */
public interface OrderItemService {

    /**
     * 新增
     *
     * @param orderId
     * @param orderItemAdd
     * @return
     */
    OrderItem addOrderItem(Long orderId, OrderItemAddVO orderItemAdd);
}
