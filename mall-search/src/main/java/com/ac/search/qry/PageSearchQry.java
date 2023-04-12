package com.ac.search.qry;

import com.ac.common.enums.OrderTypeEnum;
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
 * @description 文档分页查询对象
 * @date 2023/02/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页查询")
public class PageSearchQry {

    @JsonIgnore
    @ApiModelProperty(value = "索引名称")
    private String indexName;

    @ApiModelProperty(value = "页码")
    private Integer current = 1;

    @ApiModelProperty(value = "每页数量")
    private Integer size = 20;

    @ApiModelProperty(value = "搜索关键字")
    private String keyword;

    @ApiModelProperty(value = "检索字段-分词(Operator.OR)")
    private List<String> fieldList;

    @ApiModelProperty(value = "检索字段-不分词(Operator.AND)")
    private List<String> fieldUnSplitList;

    @ApiModelProperty(value = "精确匹配字段")
    private String termField;

    @ApiModelProperty(value = "精确匹配-值")
    private String termValue;

    @ApiModelProperty(value = "排序字段")
    private String orderField;

    @ApiModelProperty(value = "排序方式")
    private OrderTypeEnum orderType;
}
