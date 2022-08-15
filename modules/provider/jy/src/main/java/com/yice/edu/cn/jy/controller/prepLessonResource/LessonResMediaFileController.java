package com.yice.edu.cn.jy.controller.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResMediaFile;
import com.yice.edu.cn.jy.service.prepLessonResource.LessonResMediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessonResMediaFile")
@Api(value = "/lessonResMediaFile",description = "运营平台备课资源音视频模块")
public class LessonResMediaFileController {
    @Autowired
    private LessonResMediaFileService lessonResMediaFileService;

    @GetMapping("/findLessonResMediaFileById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public LessonResMediaFile findLessonResMediaFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return lessonResMediaFileService.findLessonResMediaFileById(id);
    }

    @PostMapping("/saveLessonResMediaFile")
    @ApiOperation(value = "保存", notes = "返回对象")
    public LessonResMediaFile saveLessonResMediaFile(
            @ApiParam(value = "对象", required = true)
            @RequestBody LessonResMediaFile lessonResMediaFile){
        lessonResMediaFileService.saveLessonResMediaFile(lessonResMediaFile);
        return lessonResMediaFile;
    }

    @PostMapping("/find/findLessonResMediaFileListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<LessonResMediaFile> findLessonResMediaFileListByCondition(
            @ApiParam(value = "对象")
            @RequestBody LessonResMediaFile lessonResMediaFile){
        return lessonResMediaFileService.findLessonResMediaFileListByCondition(lessonResMediaFile);
    }
    @PostMapping("/find/findLessonResMediaFileListByCondition2")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<LessonResMediaFile> findLessonResMediaFileListByCondition2(
            @ApiParam(value = "对象")
            @RequestBody LessonResMediaFile lessonResMediaFile){
        return lessonResMediaFileService.findLessonResMediaFileListByCondition2(lessonResMediaFile);
    }
    @PostMapping("/find/findLessonResMediaFileCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findLessonResMediaFileCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody LessonResMediaFile lessonResMediaFile){
        return lessonResMediaFileService.findLessonResMediaFileCountByCondition(lessonResMediaFile);
    }
    @PostMapping("/find/findLessonResMediaFileCountByCondition2")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findLessonResMediaFileCountByCondition2(
            @ApiParam(value = "对象")
            @RequestBody LessonResMediaFile lessonResMediaFile){
        return lessonResMediaFileService.findLessonResMediaFileCountByCondition2(lessonResMediaFile);
    }
    @PostMapping("/updateLessonResMediaFile")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateLessonResMediaFile(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody LessonResMediaFile lessonResMediaFile){
        lessonResMediaFileService.updateLessonResMediaFile(lessonResMediaFile);
    }

    @GetMapping("/deleteLessonResMediaFile/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteLessonResMediaFile(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        lessonResMediaFileService.deleteLessonResMediaFile(id);
    }
    @PostMapping("/deleteLessonResMediaFileByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteLessonResMediaFileByCondition(
            @ApiParam(value = "对象")
            @RequestBody LessonResMediaFile lessonResMediaFile){
        lessonResMediaFileService.deleteLessonResMediaFileByCondition(lessonResMediaFile);
    }
    @PostMapping("/findOneLessonResMediaFileByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public LessonResMediaFile findOneLessonResMediaFileByCondition(
            @ApiParam(value = "对象")
            @RequestBody LessonResMediaFile lessonResMediaFile){
        return lessonResMediaFileService.findOneLessonResMediaFileByCondition(lessonResMediaFile);
    }

    @GetMapping("/update/downloadCountChange/{id}")
    @ApiOperation(value = "通过id更新多媒体文件下载次数")
    public void downloadCountChange(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        lessonResMediaFileService.downloadCountChange(id);
    }
    @GetMapping("/update/numLookChange/{id}")
    @ApiOperation(value = "通过id更新多媒体文件下载次数")
    public void numLookChange(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        lessonResMediaFileService.numLookChange(id);
    }
}
