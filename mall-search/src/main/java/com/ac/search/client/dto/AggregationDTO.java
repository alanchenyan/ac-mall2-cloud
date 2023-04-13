package com.ac.search.client.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AggregationDTO {

    @ApiModelProperty(value = "分组项")
    private String aggregationItem;

    @ApiModelProperty(value = "数量")
    private long count;

    @ApiModelProperty(value = "第一个文档对象")
    private Object firstDoc;
}
