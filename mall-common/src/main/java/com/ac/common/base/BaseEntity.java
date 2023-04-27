package com.ac.common.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("逻辑删除标志")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty("数据插入时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("数据修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
