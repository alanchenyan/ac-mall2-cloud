package com.ac.common.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "MongoDB分页参数")
public class MongoPage {

    @ApiModelProperty(value = "当前页数", position = 1, dataType = "int", example = "1")
    int current = 0;

    @ApiModelProperty(value = "每页记录数", position = 1, dataType = "int", example = "10")
    int size = 10;

    @ApiModelProperty(value = "总记录数", position = 4, dataType = "int", example = "100")
    int total = 0;

    @ApiModelProperty(value = "排序方式  升序:asc，降序:desc", position = 2)
    String sortType = "desc";

    @ApiModelProperty(value = "排序字段", position = 3, example = "id")
    String sort = "id";

    public MongoPage(int current, int size) {
        this.current = current;
        this.size = size;
    }

    public void setCurrent(int current) {
        if (current <= 0) {
            this.current = 1;
        } else {
            this.current = current;
        }
    }

    public int getCurrent() {
        if (this.current > 0) {
            //重点：MongoDB分页查询，页数是从0开始的，所以页数要减1
            return current - 1;
        }
        return 0;
    }

    public void setSize(int size) {
        if (size <= 0) {
            this.size = 10;
        } else {
            this.size = size;
        }
    }
}
