package com.ac.product.qry;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentQry extends Page {

    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty("商品ID")
    private Long productId;
}
