package com.ac.search.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * @author Alan Chen
 * @description 产品
 * @date 2023/02/22
 */
@Data
public class ProductDoc {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("产品详情描述")
    private String remark;

    @ApiModelProperty(value = "地理位置")
    private GeoPoint location;
}
