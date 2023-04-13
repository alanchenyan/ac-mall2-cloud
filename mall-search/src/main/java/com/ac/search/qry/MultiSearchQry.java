package com.ac.search.qry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Alan Chen
 * @description 多字段检索
 * @date 2023/02/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("多个字段检索")
public class MultiSearchQry {

    @JsonIgnore
    @ApiModelProperty(value = "索引名称")
    private String indexName;

    @ApiModelProperty(value = "检索内容")
    private String keyword;

    @ApiModelProperty(value = "检索字段")
    private List<String> fieldList;
}
