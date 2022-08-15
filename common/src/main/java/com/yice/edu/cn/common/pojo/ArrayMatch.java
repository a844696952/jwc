package com.yice.edu.cn.common.pojo;

import lombok.Data;

/**
 * 给mongodb用的,用于匹配字段是数组的查询
 * 当elemMatch为true时,需要fieldValue为一个对象
 */
@Data
public class ArrayMatch {
    private String fieldName;
    private Object fieldValue;
    private boolean elemMatch;
}
