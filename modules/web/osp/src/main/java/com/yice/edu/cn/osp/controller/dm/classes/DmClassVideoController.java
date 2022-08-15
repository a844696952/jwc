package com.yice.edu.cn.osp.controller.dm.classes;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassVideo;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.dm.classes.DmClassVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmClassVideo")
@Api(value = "/dmClassVideo",description = "班级短视频表模块")
public class DmClassVideoController {
    @Autowired
    private DmClassVideoService dmClassVideoService;

    @Autowired
    private FileTypeUtil fileTypeUtil;

    @PostMapping("/saveDmClassVideo")
    @ApiOperation(value = "保存班级短视频表对象", notes = "返回保存好的班级短视频表对象", response= DmClassVideo.class)
    public ResponseJson saveDmClassVideo(
            @ApiParam(value = "班级短视频表对象", required = true)
            @RequestBody DmClassVideo dmClassVideo){
       dmClassVideo.setSchoolId(mySchoolId());
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
        dmClassVideoService.updateDmClassVideo(dmClassVideo);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmClassVideoById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找班级短视频表", notes = "返回响应对象", response=DmClassVideo.class)
    public ResponseJson lookDmClassVideoById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassVideo dmClassVideo=dmClassVideoService.findDmClassVideoById(id);
        return new ResponseJson(dmClassVideo);
    }

    @PostMapping("/findDmClassVideosByCondition")
    @ApiOperation(value = "根据条件查找班级短视频表", notes = "返回响应对象", response=DmClassVideo.class)
    public ResponseJson findDmClassVideosByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassVideo dmClassVideo){
       dmClassVideo.setSchoolId(mySchoolId());
        List<DmClassVideo> data=dmClassVideoService.findDmClassVideoListByCondition(dmClassVideo);
        long count=dmClassVideoService.findDmClassVideoCountByCondition(dmClassVideo);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmClassVideoByCondition")
    @ApiOperation(value = "根据条件查找单个班级短视频表,结果必须为单条数据", notes = "没有时返回空", response=DmClassVideo.class)
    public ResponseJson findOneDmClassVideoByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassVideo dmClassVideo){
        DmClassVideo one=dmClassVideoService.findOneDmClassVideoByCondition(dmClassVideo);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmClassVideo/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmClassVideo(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmClassVideoService.deleteDmClassVideo(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmClassVideoListByCondition")
    @ApiOperation(value = "根据条件查找班级短视频表列表", notes = "返回响应对象,不包含总条数", response=DmClassVideo.class)
    public ResponseJson findDmClassVideoListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassVideo dmClassVideo){
       dmClassVideo.setSchoolId(mySchoolId());
        List<DmClassVideo> data=dmClassVideoService.findDmClassVideoListByCondition(dmClassVideo);
        return new ResponseJson(data);
    }

    @PostMapping("/uploadQiniuFile")
    @ApiOperation(value = "创建时间：2018-10-29。说明：上传文件到七牛", notes = "返回资源名称和资源的url")
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件file", required = true)@RequestParam("file") MultipartFile file) {
        try{
            //文件名后缀
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            int suffixInt = fileTypeUtil.setResouceType(suffix);
            if (suffixInt == 0) {
                return new ResponseJson(false, "不支持的文件格式");
            }

            long size = file.getSize();
            if(size > 150 * 1024 * 1024){
                //5120000
                return new ResponseJson(false, "文件大小不能超过500M");
            }
            //不包含文件后缀名
            String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
            Map<String, String> map = new HashMap<>();
            map.put("name", file.getOriginalFilename());
            //获取保存文件路径
            String url = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_NETWORK + suffix);
            map.put("url", url);
            return new ResponseJson(map);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseJson(false,"上传失败");
        }
    }

    @PostMapping("/batchDeleteDmClassVideo")
    @ApiOperation(value = "根据id批量删除", notes = "返回响应对象")
    public ResponseJson batchDeleteDmClassVideo(
            @RequestBody List<String> idlist){
        dmClassVideoService.batchDeleteDmClassVideo(idlist);
        return new ResponseJson();
    }
}
