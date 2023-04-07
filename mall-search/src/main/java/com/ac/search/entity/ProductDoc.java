package com.ac.search.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Alan Chen
 * @description 产品
 * @date 2023/02/22
 */
@Data
@Document(indexName = "product_doc", shards = 1, replicas = 0)
public class ProductDoc {

    @ApiModelProperty(value = "ID")
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    @ApiModelProperty("产品名称")
    private String productName;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("产品详情描述")
    private String remark;
}
