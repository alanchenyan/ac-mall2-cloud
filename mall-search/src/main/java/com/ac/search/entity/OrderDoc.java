package com.ac.search.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

/**
 * @author Alan Chen
 * @description 订单
 * @date 2023/02/22
 *
 * 提示：ElasticsearchRestTemplate不读@Filed注解，所以你在@Field里面写再多代码也没用
 */
@Data
@Document(indexName = "order_doc", shards = 1, replicas = 0)
@Mapping(mappingPath = "/json/order-mapping.json")
public class OrderDoc {

    //@Id
    @ApiModelProperty(value = "ID")
    private String id;

    //@Field(index = false, type = FieldType.Keyword)
    @ApiModelProperty("订单号")
    private String orderNo;

    //@Field(type = FieldType.Text, analyzer = "ik_max_word")
    @ApiModelProperty("产品名称")
    private String productName;

}
