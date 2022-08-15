package com.yice.edu.cn.common.pojo.dm.classCard;

import lombok.Data;

@Data
public class ClassCardLock {
    private String[] ids;
    private String lockStatus;
    private String[] cardIds;
}
