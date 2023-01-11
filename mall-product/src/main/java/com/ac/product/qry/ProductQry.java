package com.ac.product.qry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductQry {

    @ApiModelProperty("产品名称")
    private String productName;
}
