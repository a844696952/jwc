package com.yice.edu.cn.jy.controller.cloudClassroom.cloudCourse;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseShareFile;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseShareFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/cloudCourseShareFile")
@Api(value = "/cloudCourseShareFile",description = "云课堂共享文件模块")
public class CloudCourseShareFileController {
    @Autowired
    private CloudCourseShareFileService cloudCourseShareFileService;

    @GetMapping("/findCloudCourseShareFileById/{id}")
    @ApiOperation(value = "通过id查找云课堂共享文件", notes = "返回云课堂共享文件对象")
    public CloudCourseShareFile findCloudCourseShareFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return cloudCourseShareFileService.findCloudCourseShareFileById(id);
    }

    @PostMapping("/saveCloudCourseShareFile")
    @ApiOperation(value = "保存云课堂共享文件", notes = "返回云课堂共享文件对象")
    public CloudCourseShareFile saveCloudCourseShareFile(
            @ApiParam(value = "云课堂共享文件对象", required = true)
            @RequestBody CloudCourseShareFile cloudCourseShareFile){
        cloudCourseShareFileService.saveCloudCourseShareFile(cloudCourseShareFile);
        return cloudCourseShareFile;
    }

    @PostMapping("/findCloudCourseShareFileListByCondition")
    @ApiOperation(value = "根据条件查找云课堂共享文件列表", notes = "返回云课堂共享文件列表")
    public List<CloudCourseShareFile> findCloudCourseShareFileListByCondition(
            @ApiParam(value = "云课堂共享文件对象")
            @RequestBody CloudCourseShareFile cloudCourseShareFile){
        return cloudCourseShareFileService.findCloudCourseShareFileListByCondition(cloudCourseShareFile);
    }
    @PostMapping("/findCloudCourseShareFileCountByCondition")
    @ApiOperation(value = "根据条件查找云课堂共享文件列表个数", notes = "返回云课堂共享文件总个数")
    public long findCloudCourseShareFileCountByCondition(
            @ApiParam(value = "云课堂共享文件对象")
            @RequestBody CloudCourseShareFile cloudCourseShareFile){
        return cloudCourseShareFileService.findCloudCourseShareFileCountByCondition(cloudCourseShareFile);
    }

    @PostMapping("/updateCloudCourseShareFile")
    @ApiOperation(value = "修改云课堂共享文件", notes = "云课堂共享文件对象必传")
    public void updateCloudCourseShareFile(
            @ApiParam(value = "云课堂共享文件对象,对象属性不为空则修改", required = true)
            @RequestBody CloudCourseShareFile cloudCourseShareFile){
        cloudCourseShareFileService.updateCloudCourseShareFile(cloudCourseShareFile);
    }

    @GetMapping("/deleteCloudCourseShareFile/{id}")
    @ApiOperation(value = "通过id删除云课堂共享文件")
    public void deleteCloudCourseShareFile(
            @ApiParam(value = "云课堂共享文件对象", required = true)
            @PathVariable String id){
        cloudCourseShareFileService.deleteCloudCourseShareFile(id);
    }
    @PostMapping("/deleteCloudCourseShareFileByCondition")
    @ApiOperation(value = "根据条件删除云课堂共享文件")
    public void deleteCloudCourseShareFileByCondition(
            @ApiParam(value = "云课堂共享文件对象")
            @RequestBody CloudCourseShareFile cloudCourseShareFile){
        cloudCourseShareFileService.deleteCloudCourseShareFileByCondition(cloudCourseShareFile);
    }
    @PostMapping("/findOneCloudCourseShareFileByCondition")
    @ApiOperation(value = "根据条件查找单个云课堂共享文件,结果必须为单条数据", notes = "返回单个云课堂共享文件,没有时为空")
    public CloudCourseShareFile findOneCloudCourseShareFileByCondition(
            @ApiParam(value = "云课堂共享文件对象")
            @RequestBody CloudCourseShareFile cloudCourseShareFile){
        return cloudCourseShareFileService.findOneCloudCourseShareFileByCondition(cloudCourseShareFile);
    }
}
