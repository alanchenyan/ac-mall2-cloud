package com.ac.order.service.impl;

import com.ac.order.dao.OrderDao;
import com.ac.order.dto.OrderDTO;
import com.ac.order.dto.OrderDetailDTO;
import com.ac.order.qry.OrderPageQry;
import com.ac.order.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDaoImpl;

    @Override
    public OrderDetailDTO findOrderDetail(Long id) {
        return null;
    }

    @Override
    public IPage<OrderDTO> pageOrder(OrderPageQry qry) {
        return orderDaoImpl.pageOrder(qry);
    }
}
