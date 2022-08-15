package com.yice.edu.cn.common.pojo.dm.classCard;

import lombok.Data;

@Data
public class Equipment {
    private Long onLineCount; //在线数量
    private Long offLineCount; //离线数量
    private Long notBoundCount; //未绑定数量
    private Long boundCount;   //绑定数量
}
