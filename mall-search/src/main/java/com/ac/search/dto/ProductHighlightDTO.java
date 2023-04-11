package com.ac.search.dto;

import com.ac.search.highlight.BaseHighlight;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductHighlightDTO extends BaseHighlight {

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
}
