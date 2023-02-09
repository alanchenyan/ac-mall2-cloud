package com.ac.order.service;

import com.ac.order.dto.OrderDetailDTO;
import com.ac.order.entity.Order;
import com.ac.order.qry.OrderPageQry;
import com.baomidou.mybatisplus.core.metadata.IPage;

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

    /**
     * 分页查询订单
     *
     * @param qry
     * @return
     */
    IPage<Order> pageOrder(OrderPageQry qry);
}
