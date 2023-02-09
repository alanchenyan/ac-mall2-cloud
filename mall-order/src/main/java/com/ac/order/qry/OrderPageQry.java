package com.ac.order.qry;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderPageQry extends Page {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("用户姓名")
    private String memberName;

    @ApiModelProperty("手机号")
    private String mobile;
}
