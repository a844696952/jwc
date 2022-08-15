package com.yice.edu.cn.ewb.upload;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/upload")
@RestController
public class UploadController {


    @GetMapping("/qiniu/uptoken")
    public UpToken uptoken(){
        return new UpToken(QiniuUtil.getSimpleToken(), Constant.RES_PRE);
    }


    private static class UpToken{
        private String uptoken;
        private String domain;

        private UpToken(String uptoken, String domain) {
            this.uptoken = uptoken;
            this.domain = domain;
        }

        public String getUptoken() {
            return uptoken;
        }

        public void setUptoken(String uptoken) {
            this.uptoken = uptoken;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }

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
    @PostMapping("/uploadQuestion")
    @ApiOperation(value = "学生平板端上传学生的作答结果图",notes = "返回图片路径字符串")
    public ResponseJson uploadQuestion(@RequestParam("file") MultipartFile file){
        final String path = QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_STUDENT_QUESTION);
        return new ResponseJson(path);
    }


    @GetMapping("/getPercent")
    public Integer getUploadPercent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int percent = session.getAttribute("upload_percent") == null ? 0 : (int) session.getAttribute("upload_percent");
        return percent;
    }
    /**
     * 重置上传进度 前端调用进度之前调用此接口
     *
     * @param request
     * @return void
     */
    @PostMapping("/resetPercent")
    public void resetPercent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("upload_percent", 0);
    }

    @DeleteMapping("/deleteFile")
    public ResponseJson deleteFile(String url){
        QiniuUtil.deleteFile(url);
        return new ResponseJson("删除成功");
    }

}
