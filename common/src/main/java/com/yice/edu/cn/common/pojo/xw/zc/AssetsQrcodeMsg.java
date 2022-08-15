package com.yice.edu.cn.common.pojo.xw.zc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资产二维码下载消息
 * @author 邓峰峰
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsQrcodeMsg {
    /**
     *消息提示
     */
    private String msg;
    /**
     *是否可以马上下载
     */
    private Boolean wait;
}
