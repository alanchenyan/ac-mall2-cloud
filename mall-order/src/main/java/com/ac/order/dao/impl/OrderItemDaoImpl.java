package com.ac.order.dao.impl;

import com.ac.order.dao.OrderItemDao;
import com.ac.order.entity.OrderItem;
import com.ac.order.mapper.OrderItemMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Slf4j
@Repository
public class OrderItemDaoImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemDao {

    @Resource
    private OrderItemMapper orderItemMapper;

}
