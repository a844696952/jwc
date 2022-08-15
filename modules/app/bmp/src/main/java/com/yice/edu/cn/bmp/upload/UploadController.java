package com.yice.edu.cn.bmp.upload;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/upload")
@RestController
public class UploadController {

    @PostMapping("/uploadAvatar")
    public ResponseJson uploadAvatar(MultipartFile file){
        final String path = QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_AVATAR);
        return new ResponseJson(path);
    }
}
