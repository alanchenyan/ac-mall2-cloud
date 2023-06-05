package com.ac.common.base.mongo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MongoBaseEntity implements Serializable {

    @JsonDeserialize(using = ObjectIdJsonDeserializer.class)
    @JsonSerialize(using = ObjectIdJsonSerializer.class)
    @JSONField(serializeUsing = ObjectIdSerializer.class, deserializeUsing = ObjectIdSerializer.class)
    @MongoId
    private ObjectId id;

    @ApiModelProperty("逻辑删除标志")
    private Boolean deleted = false;

    @ApiModelProperty("数据插入时间")
    @CreatedDate
    private LocalDateTime createTime;

    @ApiModelProperty("数据修改时间")
    private LocalDateTime updateTime;
}
