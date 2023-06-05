package com.ac.core.mongo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "MongoDB分页参数")
public class MongoPage {

    @ApiModelProperty(value = "当前页数", position = 1, dataType = "int", example = "1")
    private int current = 1;

    @ApiModelProperty(value = "每页记录数", position = 1, dataType = "int", example = "10")
    private int size = 10;

    @ApiModelProperty(value = "总记录数", position = 4, dataType = "int", example = "100")
    private int total = 0;

    @ApiModelProperty(value = "排序方式  升序:ASC，降序:DESC", position = 2)
    private Sort.Direction sortType = Sort.Direction.DESC;

    @ApiModelProperty(value = "排序字段", position = 3, example = "id")
    private String sort;

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

    /**
     * 页码数减一
     *
     * @return
     */
    public int getCurrentMinusOne() {
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
