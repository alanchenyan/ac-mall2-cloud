package com.ac.order.service;

import com.ac.order.dto.OrderDetailDTO;

/**
 * @author Alan Chen
 * @description 订单-业务层
 * @date 2022/12/29
 */
public interface OrderService {

    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    OrderDetailDTO findOrderDetail(Long id);
}
