package com.ac.search.qry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AggregationSearchQry {

    @JsonIgnore
    @ApiModelProperty(value = "索引名称")
    private String indexName;

    @JsonIgnore
    @ApiModelProperty(value = "分组字段")
    private String aggregationField;

    @ApiModelProperty(value = "精确匹配字段")
    private String termField;

    @ApiModelProperty(value = "精确匹配-值")
    private String termValue;
}
