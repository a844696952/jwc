package com.yice.edu.cn.osp.controller.upload;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.yice.edu.cn.common.util.oss.QiniuUtil.commonUploadFile;
import static com.yice.edu.cn.common.util.oss.QiniuUtil.getExt;

@RequestMapping("/upload")
@RestController
public class UploadController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/uploadAvatar")
    public String uploadAvatar(MultipartFile file) {

        return QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_AVATAR);
    }

    @PostMapping("/uploadJournalImage")
    public String uploadJournalImage(MultipartFile file) {
        return QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_JOURNAL_IMAGE);
    }

    @PostMapping("/uploadOaAttachment")
    public String uploadOaAttachment(MultipartFile file) {
        return QiniuUtil.uploadOfficeFile(file);
    }

    @PostMapping("/uploadOaAttachmentByFile")
    public String uploadOaAttachmentByFile(MultipartFile file) {
        return QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_OA_ATTACHMENT);
    }

    /**
     * 智能笔中铺码资源图片上传
     *
     * @param file 图片文件
     * @return 数据map
     */
    @PostMapping("/uploadSmartPen")
    public Map<String, Object> uploadSmartPen(MultipartFile file, int maxSize) {
        if (maxSize > 0 && file.getSize() > 1024 * 1024 * maxSize) {
            throw new IllegalArgumentException("size should be less than " + 1024 * 1024 * maxSize);
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("originalName", file.getOriginalFilename());
        map.put("url", QiniuUtil.commonUploadFileKeepName(file, Constant.Upload.UPLOAD_SMART_PEN));
        return map;
    }

    /**
     * 给umeditor专用的上传图片
     *
     * @param upfile
     * @return
     */
    @PostMapping("/upload4ume")
    @ResponseBody
    public Map<String, Object> upload4ume(MultipartFile upfile) {
//        "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+
//                up.getOriginalName() +"\", \"size\": "+ up.getSize()
//                +", \"state\": \""+ up.getState() +"\", \"type\": \""+
//                up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";
        Map<String, Object> tm = new HashMap<>();
        tm.put("name", upfile.getName());
        tm.put("originalName", upfile.getOriginalFilename());
        tm.put("size", upfile.getSize());
        tm.put("state", "SUCCESS");
        tm.put("type", upfile.getContentType());
        tm.put("url", QiniuUtil.uploadImage(upfile, Constant.Upload.UPLOAD_EDITOR_IMAGE));
        return tm;
    }

    @PostMapping("/uploadImgForCk")
    public Map<String, Object> uploadImgForCk(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        String ext = getExt(file);
        if (".jpg".equals(ext)
                || ".png".equals(ext)
                || ".gif".equals(ext)
                || ".bmp".equals(ext)
                || ".jpeg".equals(ext)
        ) {
            if (file.getSize() > 20 * 1024 * 1024) {
                map.put("uploaded", 0);
                Map<String, String> error = new HashMap<>();
                error.put("message", "图片大小不能超过20MB");
                map.put("error", error);
                return map;
            }
            map.put("uploaded", 1);
            map.put("fileName", file.getOriginalFilename());
            map.put("url", Constant.RES_PRE + commonUploadFile(file, Constant.Upload.UPLOAD_EDITOR_IMAGE));
            return map;
        } else {
            map.put("uploaded", 0);
            Map<String, String> error = new HashMap<>();
            error.put("message", "只能上传图片");
            map.put("error", error);
            return map;
        }

    }

    @PostMapping("/uploadSvgString")
    public ResponseJson uploadSvgString(@RequestBody String base64) throws IOException {
        String decodeStr = URLDecoder.decode(base64, "utf-8");
        byte[] bytes = decodeStr.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        String key = Constant.Upload.UPLOAD_EDITOR_IMAGE + QiniuUtil.getDatePath() + "/" + QiniuUtil.newName() + ".svg";
        String s = QiniuUtil.commonUploadInputstreamForKey(inputStream, key);
        return new ResponseJson(s);
    }

    @PostMapping("/convertMmlToImage")
    public ResponseJson convertMmlToImage(@RequestBody String mml) throws IOException {
        HttpRequest request = HttpRequest.post(Constant.THIRDPARTY_SERVICE.WIRIS_SHOW_IMAGE)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("mml=" + URLEncoder.encode(mml, "UTF-8") + "&lang=zh-cn&metrics=true&centerbaseline=false");
        HttpResponse response = request.execute();
        JSONObject jsonObject = JSONUtil.parseObj(response.body());
        String svg = jsonObject.getJSONObject("result").getStr("content");
        byte[] bytes = svg.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        String key = Constant.Upload.UPLOAD_EDITOR_IMAGE + QiniuUtil.getDatePath() + "/" + QiniuUtil.newName() + ".svg";
        String s = QiniuUtil.commonUploadInputstreamForKey(inputStream, key);
        return new ResponseJson(s);

    }

    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam("path") String path, HttpServletResponse response) {
        HttpKit.downloadFile(path, response);
    }

    @PostMapping("/uploadPaperImg")
    public String uploadPaperImg(MultipartFile file) {
        return QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_PAPER_IMG, 5 * 1024 * 1024);
    }

    /**
     * 前端 uploadImg组件使用功能到的通过图片上传接口
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadCommonImg")
    public String uploadCommonImg(MultipartFile file) {
        return QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_COMMON_IMG, 10 * 1024 * 1024);
    }

    /**
     * 党建学习资源中上传视频
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadStudy/video")
    public String uploadStudyVideo(MultipartFile file) {
        //文件最大大小500M
        long size = 500;
        //得到文件大小，单位M，向上取整，1M=1024k=1048576字节
        int fileBigSize = (int) Math.ceil(file.getSize() / 1024 / 1024);
        String ext = getExt(file);
        if (".mkv".equalsIgnoreCase(ext)
                || ".mp4".equalsIgnoreCase(ext)
        ) {
            if (fileBigSize >= size) {
                throw new IllegalArgumentException("size should be less than " + size);
            }
            return commonUploadFile(file, Constant.Upload.UPLOAD_DJ_STUDY_RESOURCE);
        } else {
            throw new RuntimeException("wrong file type");
        }
    }

    /**
     * 党建学习资源中上传音频
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadStudy/voice")
    public String uploadStudyVoice(MultipartFile file) {
        //文件最大大小500M
        long size = 500;
        //得到文件大小，单位M，向上取整，1M=1024k=1048576字节
        int fileBigSize = (int) Math.ceil(file.getSize() / 1024 / 1024);
        String ext = getExt(file);
        if (".wma".equalsIgnoreCase(ext)
                || ".mp3".equalsIgnoreCase(ext)
        ) {
            if (fileBigSize >= size) {
                throw new IllegalArgumentException("size should be less than " + size);
            }
            return commonUploadFile(file, Constant.Upload.UPLOAD_DJ_STUDY_RESOURCE);
        } else {
            throw new RuntimeException("wrong file type");
        }
    }

    /**
     * 党建我的学习上传各种类型文档
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadStudy/file")
    public String uploadStudyFile(MultipartFile file) {
        //文件最大大小10M
        long size = 1024 * 10;
        String ext = getExt(file);
        if (".jpg".equalsIgnoreCase(ext)
                || ".png".equalsIgnoreCase(ext)
                || ".gif".equalsIgnoreCase(ext)
                || ".bmp".equalsIgnoreCase(ext)
                || ".doc".equalsIgnoreCase(ext)
                || ".docx".equalsIgnoreCase(ext)
                || ".ppt".equalsIgnoreCase(ext)
                || ".pptx".equalsIgnoreCase(ext)
                || ".txt".equalsIgnoreCase(ext)
                || ".pdf".equalsIgnoreCase(ext)
                || ".xls".equalsIgnoreCase(ext)
        ) {
            return commonUploadFile(file, Constant.Upload.UPLOAD_DJ_STUDY_RESOURCE);
        } else {
            return "只能上传文档类型文件！";
        }
    }

    /**
     * ck编辑器里自定义的上传图片功能
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadImgForImageBox")
    public ResponseJson uploadImgForImageBox(@RequestParam("file") MultipartFile file) {
        String ext = QiniuUtil.getExt(file);
        if (".jpg".equalsIgnoreCase(ext)
                || ".png".equalsIgnoreCase(ext)
                || ".gif".equalsIgnoreCase(ext)
                || ".bmp".equalsIgnoreCase(ext)
                || ".jpeg".equalsIgnoreCase(ext)
        ) {
            if (file.getSize() > 10 * 1024 * 1024) {
                return new ResponseJson(false, "图片大小不能超过10MB");
            }
            return new ResponseJson(Constant.RES_PRE + QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_EDITOR_IMAGE));
        } else {
            return new ResponseJson(false, "只能上传图片");
        }

    }
    @GetMapping("/uptoken")
    public ResponseJson uptoken(){
        return new ResponseJson(QiniuUtil.getSimpleToken());
    }

    public static void main(String[] args) throws IOException {
        HttpRequest body = HttpRequest.post(Constant.THIRDPARTY_SERVICE.WIRIS_SHOW_IMAGE)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("mml=" + URLEncoder.encode("<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mover><mo>&#x21CC;</mo><mn>12</mn></mover></math>", "UTF-8") + "&lang=zh-cn&metrics=true&centerbaseline=false");
        HttpResponse response = body.execute();
        System.out.println(response.body());

    }
}
