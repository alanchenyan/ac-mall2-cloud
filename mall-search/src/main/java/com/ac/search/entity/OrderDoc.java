package com.ac.search.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * @author Alan Chen
 * @description 订单
 * @date 2023/02/22
 *
 * 提示：ElasticsearchRestTemplate不读@Filed注解，所以你在@Field里面写再多代码也没用
 * 参考：https://blog.csdn.net/QQ401476683/article/details/121422427
 */
@Data
@Setting(settingPath = "/json/common-setting.json")
@Mapping(mappingPath = "/json/order-mapping.json")
@Document(indexName = "order_doc", shards = 1, replicas = 0)
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
