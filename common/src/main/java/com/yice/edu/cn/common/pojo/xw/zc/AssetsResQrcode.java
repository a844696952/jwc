package com.yice.edu.cn.common.pojo.xw.zc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsResQrcode {

    private String serialNumber;

    private String assetsResId;

    private byte[] qrCode;

    private String assetsName;

    private String schoolId;

    private String createTime;


}
