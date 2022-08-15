package com.yice.edu.cn.yed.controller.general.upload;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
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

@RequestMapping("/upload")
@RestController
public class UploadController {

    @PostMapping("/uploadAvatar")
    public String uploadAvatar(MultipartFile file){
        return QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_AVATAR);
    }


    /**
     * 给umeditor专用的上传图片
     * @param upfile
     * @return
     */
    @PostMapping("/upload4ume")
    public Map<String,Object> upload4ume(MultipartFile upfile){
//        "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+
//                up.getOriginalName() +"\", \"size\": "+ up.getSize()
//                +", \"state\": \""+ up.getState() +"\", \"type\": \""+
//                up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";
        Map<String,Object> tm = new HashMap<>();
        tm.put("name",upfile.getName());
        tm.put("originalName",upfile.getOriginalFilename());
        tm.put("size",upfile.getSize());
        tm.put("state","SUCCESS");
        tm.put("type",upfile.getContentType());
        tm.put("url",QiniuUtil.uploadImage(upfile, Constant.Upload.UPLOAD_AVATAR));
        return tm;
    }

    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam("path") String path, HttpServletResponse response){
        HttpKit.downloadFile(path, response);
    }
    
    /**
     * 上传教材封面
     * @param file
     * @return
     */
    @PostMapping("/uploadMaterial")
    public String uploadMaterial(MultipartFile file){
        return QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_MATERIAL);
    }
    /**
     * 上传app图标
     * @param file
     * @return
     */
    @PostMapping("/uploadAppIcon")
    public String uploadAppIcon(MultipartFile file){
        return QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_APP_ICON);
    }
    @PostMapping("/uploadCover")
    public String uploadCover(MultipartFile file){
        return QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_COVER);
    }
    @PostMapping("/uploadImgForCk")
    public Map<String, Object> uploadImgForCk(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        String ext = QiniuUtil.getExt(file);
        if (".jpg".equals(ext)
                || ".png".equals(ext)
                || ".gif".equals(ext)
                || ".bmp".equals(ext)
                || ".jpeg".equals(ext)
        ) {
            if (file.getSize() > 3 * 1024 * 1024) {
                map.put("uploaded", 0);
                Map<String, String> error = new HashMap<>();
                error.put("message", "图片大小不能超过3MB");
                map.put("error", error);
                return map;
            }
            map.put("uploaded", 1);
            map.put("fileName", file.getOriginalFilename());
            map.put("url", Constant.RES_PRE + QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_EDITOR_IMAGE));
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
        String key = Constant.Upload.UPLOAD_EDITOR_IMAGE+QiniuUtil.getDatePath() + "/"+ QiniuUtil.newName()+".svg";
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
        String key = Constant.Upload.UPLOAD_EDITOR_IMAGE+QiniuUtil.getDatePath() + "/"+ QiniuUtil.newName()+".svg";
        String s = QiniuUtil.commonUploadInputstreamForKey(inputStream, key);
        return new ResponseJson(s);

    }

    /**
     * ck编辑器里自定义的上传图片功能
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
                return new ResponseJson(false,"图片大小不能超过10MB");
            }
            return new ResponseJson(Constant.RES_PRE + QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_EDITOR_IMAGE));
        } else {
            return new ResponseJson(false,"只能上传图片");
        }

    }
}
