package com.ac.product.qry;

import com.ac.core.mongo.MongoPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentQry extends MongoPage {

    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("评价人ID")
    private Long memberId;
}
