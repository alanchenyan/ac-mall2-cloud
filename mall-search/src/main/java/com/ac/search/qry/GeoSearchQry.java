package com.ac.search.qry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.common.unit.DistanceUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "GEO地理位置查询")
public class GeoSearchQry {

    @JsonIgnore
    @ApiModelProperty(value = "索引名称")
    private String indexName;

    @JsonIgnore
    @ApiModelProperty(value = "地理位置字段")
    private String geoPointField;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "经度")
    private Double lon;

    @ApiModelProperty(value = "距离")
    private Double distance;

    @ApiModelProperty(value = "单位（默认米）")
    private DistanceUnit distanceUnit = DistanceUnit.METERS;
}
