package com.ac.order.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.ac.order.dao.OrderDao;
import com.ac.order.dto.OrderDTO;
import com.ac.order.dto.OrderDetailDTO;
import com.ac.order.entity.Order;
import com.ac.order.enums.OrderStateEnum;
import com.ac.order.qry.OrderPageQry;
import com.ac.order.service.OrderService;
import com.ac.order.vo.OrderAddVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createOrder(OrderAddVO addVO) {
        Order order = new Order();
        order.setOrderNo(RandomUtil.randomNumbers(8));
        order.setOrderState(OrderStateEnum.UN_PAY);
        order.setOrderTime(LocalDateTime.now());

        //TODO 用户信息
        order.setMemberId(addVO.getMemberId());
        order.setMemberName("");
        order.setMobile("");

        orderDaoImpl.save(order);
        return order.getId();
    }
}
