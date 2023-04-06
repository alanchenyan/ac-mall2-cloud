package com.ac.product.vo;

import com.ac.product.enums.PublishStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductEditVO {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("库存")
    private Integer stock;

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
