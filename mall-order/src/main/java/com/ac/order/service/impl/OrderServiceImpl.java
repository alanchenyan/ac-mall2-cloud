package com.ac.order.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.ac.feign.member.api.MemberFeignApi;
import com.ac.feign.member.dto.MemberDTO;
import com.ac.feign.product.api.ProductFeignApi;
import com.ac.order.dao.OrderDao;
import com.ac.order.dto.OrderDTO;
import com.ac.order.dto.OrderDetailDTO;
import com.ac.order.entity.Order;
import com.ac.order.entity.OrderItem;
import com.ac.order.enums.OrderStateEnum;
import com.ac.order.qry.OrderPageQry;
import com.ac.order.service.OrderItemService;
import com.ac.order.service.OrderService;
import com.ac.order.vo.OrderAddVO;
import com.ac.order.vo.OrderItemAddVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDaoImpl;

    @Resource
    private MemberFeignApi memberFeignApi;

    @Resource
    private OrderItemService orderItemServiceImpl;

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

        //取用户信息
        MemberDTO member = memberFeignApi.findMember(addVO.getMemberId());
        order.setMemberId(addVO.getMemberId());
        order.setMemberName(member.getMemberName());
        order.setMobile(member.getMobile());
        orderDaoImpl.save(order);

        BigDecimal discountAmount = new BigDecimal(0.00);
        BigDecimal productAmount = new BigDecimal(0.00);
        //存订单项信息
        for (OrderItemAddVO orderItemAdd : addVO.getOrderItemList()) {
            OrderItem orderItem = orderItemServiceImpl.addOrderItem(order.getId(), orderItemAdd);
            productAmount.add(orderItem.getBuyPrice().multiply(new BigDecimal(orderItem.getBuyNum())));
        }

        //更新订单金额信息
        order.setDiscountAmount(discountAmount);
        order.setProductAmount(productAmount);
        BigDecimal payAmount = productAmount.subtract(discountAmount);
        order.setPayAmount(payAmount);
        orderDaoImpl.updateById(order);

        return order.getId();
    }
}
