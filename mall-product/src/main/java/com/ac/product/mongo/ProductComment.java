package com.ac.product.mongo;

import com.ac.common.base.mongo.MongoBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Alan Chen
 * @description 产品评价
 * @date 2023/05/22
 */
@Data
@Document(collection = "product_comment")
public class ProductComment extends MongoBaseEntity {

    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty("评价人ID")
    private Long memberId;

    @ApiModelProperty("评价人用户信息")
    private MemberInfo memberInfo;

    @ApiModelProperty("订单产品信息")
    private OrderProductInfo orderProductInfo;
}
