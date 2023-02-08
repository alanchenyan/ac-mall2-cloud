package com.ac.order.dao.impl;

import com.ac.order.dao.OrderDao;
import com.ac.order.entity.Order;
import com.ac.order.mapper.OrderMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Slf4j
@Repository
public class OrderDaoImpl extends ServiceImpl<OrderMapper, Order> implements OrderDao {

    @Resource
    private OrderMapper orderMapper;

}
