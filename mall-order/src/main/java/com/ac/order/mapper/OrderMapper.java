package com.ac.order.mapper;

import com.ac.order.entity.Order;
import com.ac.order.qry.OrderPageQry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author Alan Chen
 * @description 订单-数据访问层(依赖mybatis-plus)，对应mapper.xml里的自定义sql
 * @date 2022/12/29
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 分页查询订单
     *
     * @param page 分页查询参数
     * @param qry  查询参数
     * @return
     */
    IPage<Order> pageOrder(Page page, @Param("qry") OrderPageQry qry);
}
