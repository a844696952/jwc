package com.yice.edu.cn.common.pojo.dm.classCard;

import lombok.Data;

import java.io.Serializable;

/**
 * 云班牌
 */
@Data
public class DmClassCardReq implements Serializable {

    private String userName;//用户名
    private String passWord;//密码
    private Integer equipmentStatus;//设备状态 0：在线   1：离线
    private String version;//版本号
    private String equipmentName;//设备名称
    private String token;

}
