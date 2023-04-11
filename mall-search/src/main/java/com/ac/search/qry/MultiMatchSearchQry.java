package com.ac.search.qry;

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
@ApiModel("多字段检索")
public class MultiMatchSearchQry {

    @ApiModelProperty(name = "fieldList", value = "检索字段", required = true)
    private List<String> fieldList;

    @ApiModelProperty(name = "keyword", value = "检索内容", required = true)
    private String keyword;

    @ApiModelProperty(name = "indexName", value = "索引名称", required = true)
    private String indexName;
}
