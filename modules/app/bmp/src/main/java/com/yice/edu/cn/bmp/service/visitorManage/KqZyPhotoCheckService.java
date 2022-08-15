package com.yice.edu.cn.bmp.service.visitorManage;

import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.util.zyDetector.AESUtil;
import com.yice.edu.cn.common.util.zyDetector.Base64;
import com.yice.edu.cn.common.util.zyDetector.Base64DecodeMultipartFile;
import com.yice.edu.cn.common.util.zyDetector.ZyDetector;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:xushu
 * @date:2019/4/12
 */
@Service
public class KqZyPhotoCheckService {

    public DataReceiveResBean zyPhotoCheck(Boolean isProd, KqSchoolInit kqSchool, String photo) {
        Map<String, Object> reqParam = new HashMap<String, Object>();
        reqParam.put("userAcct", AESUtil.encrypt(kqSchool.getUserAcct(), kqSchool.getKey()));
        reqParam.put("userPw", AESUtil.encrypt(kqSchool.getUserPw(), kqSchool.getKey()));
        String prsnAvtrUrlAddr = "";
        try {
            MultipartFile file = Base64DecodeMultipartFile.base64Convert(photo);
            prsnAvtrUrlAddr = Base64.encode(file.getBytes());
        } catch (Exception e) {
            System.out.println("图片文件异常");
            e.printStackTrace();
        }
        reqParam.put("photoRawData", prsnAvtrUrlAddr);
        String response = ZyDetector.postRequest(isProd, ZyDetector.FACE_DETECT,
                kqSchool.getCoCode(), kqSchool.getRequstSource(), kqSchool.getVersion(), JSON.toJSONString(reqParam));
        DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
        return dataReceiveResBean;
    }
}

