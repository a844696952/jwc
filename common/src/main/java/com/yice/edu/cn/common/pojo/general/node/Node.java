package com.yice.edu.cn.common.pojo.general.node;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class Node {
    @Field("id")
    private String id;
    private String name;
}
