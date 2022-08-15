package com.yice.edu.cn.osp.controller.jy.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCoursewareVo;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourseLessonsFile;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudSubCourseLessonsFileService;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudSubCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/cloudSubCourseLessonsFile")
@Api(value = "/cloudSubCourseLessonsFile",description = "云课堂课件表模块")
public class CloudSubCourseLessonsFileController {
    @Autowired
    private CloudSubCourseLessonsFileService cloudSubCourseLessonsFileService;
    @Autowired
    private CloudSubCourseService cloudSubCourseService;

    @PostMapping("/saveCloudSubCourseLessonsFile")
    @ApiOperation(value = "保存云课堂课件表对象", notes = "返回保存好的云课堂课件表对象", response=CloudSubCourseLessonsFile.class)
    public ResponseJson saveCloudSubCourseLessonsFile(
            @ApiParam(value = "云课堂课件表对象", required = true)
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
       cloudSubCourseLessonsFile.setSchoolId(mySchoolId());
        CloudSubCourseLessonsFile s=cloudSubCourseLessonsFileService.saveCloudSubCourseLessonsFile(cloudSubCourseLessonsFile);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCloudSubCourseLessonsFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找云课堂课件表", notes = "返回响应对象", response=CloudSubCourseLessonsFile.class)
    public ResponseJson findCloudSubCourseLessonsFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudSubCourseLessonsFile cloudSubCourseLessonsFile=cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileById(id);
        return new ResponseJson(cloudSubCourseLessonsFile);
    }

    @PostMapping("/update/updateCloudSubCourseLessonsFile")
    @ApiOperation(value = "修改云课堂课件表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateCloudSubCourseLessonsFile(
            @ApiParam(value = "被修改的云课堂课件表对象,对象属性不为空则修改", required = true)
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        cloudSubCourseLessonsFileService.updateCloudSubCourseLessonsFile(cloudSubCourseLessonsFile);
        return new ResponseJson();
    }

    @PostMapping("/update/updateCloudSubCourseLessonsFileForAll")
    @ApiOperation(value = "修改云课堂课件表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateCloudSubCourseLessonsFileForAll(
            @ApiParam(value = "被修改的云课堂课件表对象", required = true)
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        cloudSubCourseLessonsFileService.updateCloudSubCourseLessonsFileForAll(cloudSubCourseLessonsFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCloudSubCourseLessonsFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找云课堂课件表", notes = "返回响应对象", response=CloudSubCourseLessonsFile.class)
    public ResponseJson lookCloudSubCourseLessonsFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudSubCourseLessonsFile cloudSubCourseLessonsFile=cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileById(id);
        return new ResponseJson(cloudSubCourseLessonsFile);
    }

    @PostMapping("/findCloudSubCourseLessonsFilesByCondition")
    @ApiOperation(value = "根据条件查找云课堂课件表", notes = "返回响应对象", response=CloudSubCourseLessonsFile.class)
    public ResponseJson findCloudSubCourseLessonsFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
       cloudSubCourseLessonsFile.setSchoolId(mySchoolId());
        List<CloudSubCourseLessonsFile> data=cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileListByCondition(cloudSubCourseLessonsFile);
        long count=cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileCountByCondition(cloudSubCourseLessonsFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneCloudSubCourseLessonsFileByCondition")
    @ApiOperation(value = "根据条件查找单个云课堂课件表,结果必须为单条数据", notes = "没有时返回空", response=CloudSubCourseLessonsFile.class)
    public ResponseJson findOneCloudSubCourseLessonsFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        CloudSubCourseLessonsFile one=cloudSubCourseLessonsFileService.findOneCloudSubCourseLessonsFileByCondition(cloudSubCourseLessonsFile);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCloudSubCourseLessonsFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCloudSubCourseLessonsFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        cloudSubCourseLessonsFileService.deleteCloudSubCourseLessonsFile(id);
        return new ResponseJson();
    }


    @PostMapping("/findCloudSubCourseLessonsFileListByCondition")
    @ApiOperation(value = "根据条件查找云课堂课件表列表", notes = "返回响应对象,不包含总条数", response=CloudSubCourseLessonsFile.class)
    public ResponseJson findCloudSubCourseLessonsFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
       cloudSubCourseLessonsFile.setSchoolId(mySchoolId());
        List<CloudSubCourseLessonsFile> data=cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileListByCondition(cloudSubCourseLessonsFile);
        return new ResponseJson(data);
    }

   @GetMapping("/findCloudSubCourseListByCloudCourseId/{id}")
   @ApiOperation(value = "根据条件查找云课堂子课程列表", notes = "返回响应对象,不包含总条数", response= CloudSubCourse.class)
   public ResponseJson findCloudSubCourseListByCloudCourseId(@PathVariable String id){
       List<CloudSubCourse> cloudSubCourseList = cloudSubCourseLessonsFileService.findCloudSubCourseListByCloudCourseId(id);
       return new ResponseJson(cloudSubCourseList);
   }

    @GetMapping("/findCoursewareListByCloudSubCourseId/{id}")
    @ApiOperation(value = "根据条件查找课件列表", notes = "返回响应对象,不包含总条数", response= CloudSubCourseLessonsFile.class)
    public ResponseJson findCoursewareListByCloudSubCourseId(@PathVariable String id){
        CloudSubCourseLessonsFile cloudSubCourseLessonsFile=new CloudSubCourseLessonsFile();
        cloudSubCourseLessonsFile.setCloudSubCourseId(id);
        List<CloudSubCourseLessonsFile> cloudSubCourseLessonsFileList = cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileListByCondition(cloudSubCourseLessonsFile);
        return new ResponseJson(cloudSubCourseLessonsFileList);
    }

    @PostMapping("/deleteCloudSubCourseLessonsFileByCondition")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCloudSubCourseLessonsFileByCondition(@RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
         cloudSubCourseLessonsFileService.deleteCloudSubCourseLessonsFileByCondition(cloudSubCourseLessonsFile);
         return new ResponseJson();
    }

    @PostMapping("/saveCoursewareListByCondition")
    @ApiOperation(value = "保存云课堂课件表对象", notes = "返回保存好的云课堂课件表对象", response=CloudSubCourseLessonsFile.class)
    public ResponseJson saveCoursewareListByCondition(
            @ApiParam(value = "云课堂课件表对象", required = true)
            @RequestBody CloudCoursewareVo cloudCoursewareVo){
        cloudCoursewareVo.getLessonsFileList().forEach(s->{
            s.setSchoolId(mySchoolId());
            s.setTeacherId(myId());
        });
        cloudSubCourseLessonsFileService.saveCoursewareListByCondition(cloudCoursewareVo);
        return new ResponseJson();
    }

    @PostMapping("/unifiedLessons")
    @ApiOperation(value = "保存云课堂课件表对象", notes = "返回保存好的云课堂课件表对象", response=CloudSubCourseLessonsFile.class)
    public ResponseJson unifiedLessons( @ApiParam(value = "云课堂课件表对象", required = true) @RequestBody List<CloudSubCourseLessonsFile> lessonsFileList){
        cloudSubCourseLessonsFileService.unifiedLessons(lessonsFileList);
        return new ResponseJson();
    }
}
