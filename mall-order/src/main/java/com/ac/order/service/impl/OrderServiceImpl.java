package com.ac.order.service.impl;

import com.ac.order.dto.OrderDetailDTO;
import com.ac.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public OrderDetailDTO findOrderDetail(Long id) {
        return null;
    }
}
