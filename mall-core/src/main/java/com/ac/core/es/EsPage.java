package com.ac.core.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EsPage<T> {

    @ApiModelProperty("数据列表")
    private List<T> records;

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("每页记录数")
    private long size;

    @ApiModelProperty("当前分页数")
    private long current;

    @ApiModelProperty("总页数")
    private long pages;
}
