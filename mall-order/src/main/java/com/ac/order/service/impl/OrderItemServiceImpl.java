package com.ac.order.service.impl;

import com.ac.feign.product.api.ProductFeignApi;
import com.ac.feign.product.dto.ProductDTO;
import com.ac.order.dao.OrderItemDao;
import com.ac.order.entity.OrderItem;
import com.ac.order.service.OrderItemService;
import com.ac.order.vo.OrderItemAddVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderItemDao orderItemDaoImpl;

    @Resource
    private ProductFeignApi productFeignApi;

    @Override
    public OrderItem addOrderItem(Long orderId, OrderItemAddVO orderItemAdd) {
        ProductDTO product = productFeignApi.findProduct(orderItemAdd.getProductId());

        OrderItem entity = new OrderItem();
        entity.setOrderId(orderId);
        entity.setProductId(product.getId());
        entity.setProductName(product.getProductName());
        entity.setImageUrl(product.getImageUrl());
        entity.setBuyNum(orderItemAdd.getBuyNum());
        if (product.getDiscountPrice() != null && product.getDiscountPrice().doubleValue() > 0) {
            entity.setBuyPrice(product.getDiscountPrice());
        } else {
            entity.setBuyPrice(product.getSellPrice());
        }
        orderItemDaoImpl.save(entity);
        return entity;
    }
}
