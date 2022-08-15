package com.yice.edu.cn.tap.upload;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/upload")
@RestController
public class UploadController {

    @PostMapping("/uploadAvatar")
    @ApiOperation(value = "单个图片上传",notes = "返回图片路径字符串")
    public ResponseJson uploadAvatar(@RequestParam("file") MultipartFile file){
        final String path = QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_AVATAR);
        return new ResponseJson(path);
    }

    @PostMapping("/batchUpload")
    @ApiOperation(value = "多个图片上传",notes = "返回图片路径集合",response = String.class,responseContainer = "List")
    public ResponseJson batchUpload(@ApiParam(value = "指定files")@RequestParam("files") List<MultipartFile> files){
        List<String> paths = new ArrayList<>();
        files.forEach(file->paths.add(QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_AVATAR)));
        return new ResponseJson(paths);
    }

    @PostMapping("/uploadFileImg")
    @ApiOperation(value = "单个文件或图片上传",notes = "返回文件或图片路径字符串")
    public ResponseJson uploadFileImg(@RequestParam("file") MultipartFile file){
        int size = 1024*1024*10;
        final String path = QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_DOC,size);
        return new ResponseJson(path);
    }

    @PostMapping("/uploadOaAttachment")
    @ApiOperation(value = "oa使用到的能保留文件名称的上传",notes = "返回文件或图片路径字符串")
    public ResponseJson uploadOaAttachment(MultipartFile file) {
        return new ResponseJson(QiniuUtil.uploadOfficeFile(file));
    }

}
