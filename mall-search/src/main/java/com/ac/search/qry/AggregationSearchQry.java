package com.ac.search.qry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分组查询")
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

    @ApiModelProperty(value = "是否-获取第一个文档对象")
    private boolean returnFirstDoc;

    @ApiModelProperty(value = "文档-忽略字段（returnFirstDoc=true时有效）")
    private List<String> docIgnoreProperties;
}
