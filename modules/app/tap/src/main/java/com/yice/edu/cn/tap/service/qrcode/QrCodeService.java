package com.yice.edu.cn.tap.service.qrcode;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.tap.feignClient.qrcode.QrCodeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QrCodeService {

    @Autowired
    private QrCodeFeign qrCodeFeign;


    public ResponseJson loginByQRCode(String tel, String qrCode){
        return qrCodeFeign.loginByQRCode(tel,qrCode);
    }

}
