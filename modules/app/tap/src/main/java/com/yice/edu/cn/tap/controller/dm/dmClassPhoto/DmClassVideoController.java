package com.yice.edu.cn.tap.controller.dm.dmClassPhoto;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassVideo;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.tap.service.dmClassPhoto.DmClassVideoService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/dmClassVideo")
@Api(value = "/dmClassVideo",description = "班级短视频表模块")
public class DmClassVideoController {
    @Autowired
    private DmClassVideoService dmClassVideoService;

    @PostMapping("/saveDmClassVideo")
    @ApiOperation(value = "保存班级短视频表对象", notes = "返回保存好的班级短视频表对象", response= DmClassVideo.class)
    public ResponseJson saveDmClassVideo(
            @ApiParam(value = "班级短视频表对象", required = true)
            @RequestBody DmClassVideo dmClassVideo){

        if(StringUtil.isNullOrEmpty(dmClassVideo.getClassId())){
            return new ResponseJson(false, "classId不能为空");
        }

        if(StringUtil.isNullOrEmpty(dmClassVideo.getVideoName())){
            return new ResponseJson(false, "视频名称不能为空");
        }

        if(StringUtil.isNullOrEmpty(dmClassVideo.getVideoUrl())){
            return new ResponseJson(false, "视频地址不能为空");
        }
        dmClassVideo.setSchoolId(mySchoolId());
        dmClassVideo.setVideoEntry(new Integer(3));

        DmClassVideo s=dmClassVideoService.saveDmClassVideo(dmClassVideo);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmClassVideoById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找班级短视频表", notes = "返回响应对象", response=DmClassVideo.class)
    public ResponseJson findDmClassVideoById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassVideo dmClassVideo=dmClassVideoService.findDmClassVideoById(id);
        return new ResponseJson(dmClassVideo);
    }

    @PostMapping("/update/updateDmClassVideo")
    @ApiOperation(value = "修改班级短视频表对象", notes = "返回响应对象")
    public ResponseJson updateDmClassVideo(
            @ApiParam(value = "被修改的班级短视频表对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassVideo dmClassVideo){
        dmClassVideo.setSchoolId(mySchoolId());
        dmClassVideoService.updateDmClassVideo(dmClassVideo);
        return new ResponseJson();
    }

    @PostMapping("/findDmClassVideoListByCondition")
    @ApiOperation(value = "根据条件查找班级短视频表列表", notes = "返回响应对象,不包含总条数", response=DmClassVideo.class)
    public ResponseJson findDmClassVideoListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassVideo dmClassVideo){

        if(StringUtil.isNullOrEmpty(dmClassVideo.getClassId())){
            return new ResponseJson(false, "classId不能为空");
        }
        dmClassVideo.setSchoolId(mySchoolId());
        dmClassVideo.setVideoEntry(new Integer(3));
        List<DmClassVideo> data = dmClassVideoService.findDmClassVideoListByCondition(dmClassVideo);
        Long count = dmClassVideoService.findDmClassVideoCountByCondition(dmClassVideo);
        return new ResponseJson(data,count);
    }

    @PostMapping("/uploadAvatar")
    @ApiOperation(value = "单个视频上传",notes = "返回视频路径字符串")
    public ResponseJson uploadAvatar(@RequestParam("file") MultipartFile file){
        String ext = QiniuUtil.getExt(file);
        String acceptExt = ".mov,.rmvb,.mp4,.mkv,.flv,.wmv,.avi,.mpg,.webm";
        if(acceptExt.indexOf(ext.toLowerCase()) == -1){
            return new ResponseJson(false, "后缀名"+ext+"不支持");
        }

        long size = file.getSize();
        if(size > 500 * 1024 * 1024){
            //5120000
            return new ResponseJson(false, "文件大小不能超过500M");
        }

        final String path = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_CLASS_VIDEO);
        return new ResponseJson(path);
    }

    @PostMapping("/batchUpload")
    @ApiOperation(value = "多个视频上传",notes = "返回视频路径集合",response = String.class,responseContainer = "List")
    public ResponseJson batchUpload(@ApiParam(value = "指定files")@RequestParam("files") List<MultipartFile> files){
        List<String> paths = new ArrayList<>();
        files.forEach(file->paths.add(QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_CLASS_VIDEO)));
        return new ResponseJson(paths);
    }

    @GetMapping("/deleteDmClassVideo/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmClassVideo(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmClassVideoService.deleteDmClassVideo(id);
        return new ResponseJson();
    }

    @PostMapping("/batchDeleteDmClassVideo")
    @ApiOperation(value = "根据id批量删除", notes = "返回响应对象")
    public ResponseJson batchDeleteDmClassVideo(
            @RequestBody List<String> idlist){
        dmClassVideoService.batchDeleteDmClassVideo(idlist);
        return new ResponseJson();
    }
}
