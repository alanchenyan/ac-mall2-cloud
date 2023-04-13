package com.ac.search.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Alan Chen
 * @description 订单
 * @date 2023/02/22
 */
@Data
@Document(indexName = "order_doc", shards = 1, replicas = 0)
public class OrderDoc {

    @Id
    @ApiModelProperty(value = "ID")
    private String id;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty("订单号")
    private String orderNo;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    @ApiModelProperty("产品名称")
    private String productName;

}
