package com.ac.product.entity;

import com.ac.core.base.BaseEntity;
import com.ac.product.enums.PublishStateEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Alan Chen
 * @description 产品
 * @date 2023/02/22
 */
@Data
@TableName("t_product")
public class Product extends BaseEntity {

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("销量")
    private Integer sellNum;

    @ApiModelProperty("进价")
    private BigDecimal costPrice;

    @ApiModelProperty("售价")
    private BigDecimal sellPrice;

    @ApiModelProperty("折扣价")
    private BigDecimal discountPrice;

    @ApiModelProperty("发布状态")
    private PublishStateEnum publishState;

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @ApiModelProperty("产品详情描述")
    private String remark;
}
