package com.yice.edu.cn.jy.controller.cloudClassroom.cloudCourse;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCoursewareVo;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourseLessonsFile;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudSubCourseLessonsFileService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudSubCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cloudSubCourseLessonsFile")
@Api(value = "/cloudSubCourseLessonsFile",description = "云课堂课件表模块")
public class CloudSubCourseLessonsFileController {
    @Autowired
    private CloudSubCourseLessonsFileService cloudSubCourseLessonsFileService;
    @Autowired
    private CloudSubCourseService cloudSubCourseService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findCloudSubCourseLessonsFileById/{id}")
    @ApiOperation(value = "通过id查找云课堂课件表", notes = "返回云课堂课件表对象")
    public CloudSubCourseLessonsFile findCloudSubCourseLessonsFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileById(id);
    }

    @PostMapping("/saveCloudSubCourseLessonsFile")
    @ApiOperation(value = "保存云课堂课件表", notes = "返回云课堂课件表对象")
    public CloudSubCourseLessonsFile saveCloudSubCourseLessonsFile(
            @ApiParam(value = "云课堂课件表对象", required = true)
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        cloudSubCourseLessonsFileService.saveCloudSubCourseLessonsFile(cloudSubCourseLessonsFile);
        return cloudSubCourseLessonsFile;
    }

    @PostMapping("/batchSaveCloudSubCourseLessonsFile")
    @ApiOperation(value = "批量保存云课堂课件表")
    public void batchSaveCloudSubCourseLessonsFile(
            @ApiParam(value = "云课堂课件表对象集合", required = true)
            @RequestBody List<CloudSubCourseLessonsFile> cloudSubCourseLessonsFiles){
        cloudSubCourseLessonsFileService.batchSaveCloudSubCourseLessonsFile(cloudSubCourseLessonsFiles);
    }

    @PostMapping("/findCloudSubCourseLessonsFileListByCondition")
    @ApiOperation(value = "根据条件查找云课堂课件表列表", notes = "返回云课堂课件表列表")
    public List<CloudSubCourseLessonsFile> findCloudSubCourseLessonsFileListByCondition(
            @ApiParam(value = "云课堂课件表对象")
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        return cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileListByCondition(cloudSubCourseLessonsFile);
    }
    @PostMapping("/findCloudSubCourseLessonsFileCountByCondition")
    @ApiOperation(value = "根据条件查找云课堂课件表列表个数", notes = "返回云课堂课件表总个数")
    public long findCloudSubCourseLessonsFileCountByCondition(
            @ApiParam(value = "云课堂课件表对象")
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        return cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileCountByCondition(cloudSubCourseLessonsFile);
    }

    @PostMapping("/updateCloudSubCourseLessonsFile")
    @ApiOperation(value = "修改云课堂课件表有值的字段", notes = "云课堂课件表对象必传")
    public void updateCloudSubCourseLessonsFile(
            @ApiParam(value = "云课堂课件表对象,对象属性不为空则修改", required = true)
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        cloudSubCourseLessonsFileService.updateCloudSubCourseLessonsFile(cloudSubCourseLessonsFile);
    }
    @PostMapping("/updateCloudSubCourseLessonsFileForAll")
    @ApiOperation(value = "修改云课堂课件表所有字段", notes = "云课堂课件表对象必传")
    public void updateCloudSubCourseLessonsFileForAll(
            @ApiParam(value = "云课堂课件表对象", required = true)
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        cloudSubCourseLessonsFileService.updateCloudSubCourseLessonsFileForAll(cloudSubCourseLessonsFile);
    }

    @GetMapping("/deleteCloudSubCourseLessonsFile/{id}")
    @ApiOperation(value = "通过id删除云课堂课件表")
    public void deleteCloudSubCourseLessonsFile(
            @ApiParam(value = "云课堂课件表对象", required = true)
            @PathVariable String id){
        cloudSubCourseLessonsFileService.deleteCloudSubCourseLessonsFile(id);
    }
    @PostMapping("/deleteCloudSubCourseLessonsFileByCondition")
    @ApiOperation(value = "根据条件删除云课堂课件表")
    public void deleteCloudSubCourseLessonsFileByCondition(
            @ApiParam(value = "云课堂课件表对象")
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        cloudSubCourseLessonsFileService.deleteCloudSubCourseLessonsFileByCondition(cloudSubCourseLessonsFile);
    }
    @PostMapping("/findOneCloudSubCourseLessonsFileByCondition")
    @ApiOperation(value = "根据条件查找单个云课堂课件表,结果必须为单条数据", notes = "返回单个云课堂课件表,没有时为空")
    public CloudSubCourseLessonsFile findOneCloudSubCourseLessonsFileByCondition(
            @ApiParam(value = "云课堂课件表对象")
            @RequestBody CloudSubCourseLessonsFile cloudSubCourseLessonsFile){
        return cloudSubCourseLessonsFileService.findOneCloudSubCourseLessonsFileByCondition(cloudSubCourseLessonsFile);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @GetMapping("/findCloudSubCourseListByCloudCourseId/{id}")
    @ApiOperation(value = "根据条件查找云课堂子课程列表", notes = "返回响应对象,不包含总条数", response= CloudSubCourse.class)
    public List<CloudSubCourse> findCloudSubCourseListByCloudCourseId(@PathVariable String id){
        CloudSubCourse cloudSubCourse=new CloudSubCourse();
        CloudCourse cloudCourse=new CloudCourse();
        cloudCourse.setId(id);
        cloudSubCourse.setCloudCourse(cloudCourse);
        List<CloudSubCourse> cloudSubCourseList = cloudSubCourseService.findCloudSubCourseListByCondition(cloudSubCourse);
        CloudSubCourseLessonsFile cloudSubCourseLessonsFile=new CloudSubCourseLessonsFile();
        cloudSubCourseList.forEach(s->{
            cloudSubCourseLessonsFile.setCloudSubCourseId(s.getId());
            long cloudSubCourseLessonsFileCount = cloudSubCourseLessonsFileService.findCloudSubCourseLessonsFileCountByCondition(cloudSubCourseLessonsFile);
            s.setCoursewareNum(cloudSubCourseLessonsFileCount);
        });
        return cloudSubCourseList;
    }

    @PostMapping("/unifiedLessons")
    public void unifiedLessons(@RequestBody List<CloudSubCourseLessonsFile> lessonsFileList){
        CloudSubCourseLessonsFile cloudSubCourseLessonsFile1=new CloudSubCourseLessonsFile();
        CloudSubCourse cloudSubCourse=new CloudSubCourse();
        CloudCourse cloudCourse=new CloudCourse();
        lessonsFileList.forEach(s->{
            cloudSubCourseLessonsFile1.setLessonsFileId(s.getLessonsFileId());
            cloudSubCourseLessonsFile1.setCloudCourseId(s.getCloudCourseId());
            cloudSubCourseLessonsFileService.deleteCloudSubCourseLessonsFileByCondition(cloudSubCourseLessonsFile1);
            cloudCourse.setId(s.getCloudCourseId());
        });
        cloudSubCourse.setCloudCourse(cloudCourse);
        List<CloudSubCourse> cloudSubCourseList = cloudSubCourseService.findCloudSubCourseListByCondition(cloudSubCourse);
        cloudSubCourseList.forEach(s->{
            lessonsFileList.forEach(c->{
                c.setCloudSubCourseId(s.getId());
            });
            cloudSubCourseLessonsFileService.batchSaveCloudSubCourseLessonsFile(lessonsFileList);
        });
    }

    @PostMapping("/saveCoursewareListByCondition")
    public void saveCoursewareListByCondition(@RequestBody CloudCoursewareVo cloudCoursewareVo){
        CloudSubCourseLessonsFile cloudSubCourseLessonsFile=new CloudSubCourseLessonsFile();
        cloudSubCourseLessonsFile.setCloudSubCourseId(cloudCoursewareVo.getCloudSubCourseId());
        cloudSubCourseLessonsFileService.deleteCloudSubCourseLessonsFileByCondition(cloudSubCourseLessonsFile);
        cloudSubCourseLessonsFileService.batchSaveCloudSubCourseLessonsFile(cloudCoursewareVo.getLessonsFileList());

    }
}
