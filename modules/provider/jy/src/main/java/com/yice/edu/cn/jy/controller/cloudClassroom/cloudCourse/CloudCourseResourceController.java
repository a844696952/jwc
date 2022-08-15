package com.yice.edu.cn.jy.controller.cloudClassroom.cloudCourse;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.*;
import com.yice.edu.cn.common.util.VideoThumbnailUtils;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseFileResourceService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseResourceService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudSubCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloudCourseResource")
@Api(value = "/cloudCourseResource",description = "云课堂录制资源模块")
public class CloudCourseResourceController {

    private Logger log = LoggerFactory.getLogger(CloudCourseResourceController.class);

    @Autowired
    private CloudCourseResourceService cloudCourseResourceService;
    @Autowired
    private CloudSubCourseService cloudSubCourseService;
    @Autowired
    private CloudCourseService cloudCourseService;
    @Autowired
    private CloudCourseFileResourceService cloudCourseFileResourceService;
    @GetMapping("/findCloudCourseResourceById/{id}")
    @ApiOperation(value = "通过id查找云课堂录制资源", notes = "返回云课堂录制资源对象")
    public CloudCourseResource findCloudCourseResourceById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return cloudCourseResourceService.findCloudCourseResourceById(id);
    }

    @PostMapping("/saveCloudCourseResource")
    @ApiOperation(value = "保存云课堂录制资源", notes = "返回云课堂录制资源对象")
    public CloudCourseResource saveCloudCourseResource(
            @ApiParam(value = "云课堂录制资源对象", required = true)
            @RequestBody CloudCourseResource cloudCourseResource){
        cloudCourseResourceService.saveCloudCourseResource(cloudCourseResource);
        return cloudCourseResource;
    }

    @PostMapping("/saveUploadCloudCourseResource")
    @ApiOperation(value = "保存上传的云课堂录制资源", notes = "返回云课堂录制资源对象")
    public CloudCourseResource saveUploadCloudCourseResource(
            @ApiParam(value = "云课堂录制资源对象", required = true)
            @RequestBody UploadCloudCourseParam param){
        CloudSubCourse beforeCloudSubCourse = cloudSubCourseService.findCloudSubCourseById(param.getCloudSubCourseId());
        CloudCourse cloudCourse = null;
        if(beforeCloudSubCourse!=null) {
            cloudCourse = beforeCloudSubCourse.getCloudCourse();
        }
        if(beforeCloudSubCourse==null || cloudCourse==null){
            log.info("课程信息缺失");
            return null;
        }
        //查询云课堂资源表是否存在条目
        CloudCourseResource query = new CloudCourseResource();
        query.setSchoolId(beforeCloudSubCourse.getSchoolId());
        query.setCloudCourseId(cloudCourse.getId());
        CloudCourseResource beforeCloudCourseResource = cloudCourseResourceService.findOneCloudCourseResourceByCondition(query);
        CloudCourseResource cloudCourseResource = null;
        //未存在资源
        if(beforeCloudCourseResource==null) {
            cloudCourseResource = assembleCloudCourseResource(beforeCloudSubCourse, cloudCourse);
            cloudCourseResourceService.saveCloudCourseResource(cloudCourseResource);
            beforeCloudCourseResource = cloudCourseResource;
        }
        CloudCourseFileResource cloudCourseFileResource = assembleCloudCourseFileResource(param, beforeCloudSubCourse, beforeCloudCourseResource);
        cloudCourseFileResourceService.saveCloudCourseFileResource(cloudCourseFileResource);
        return cloudCourseResource;
    }

    /**
     * 装载课程资源文件
     */
    private CloudCourseFileResource assembleCloudCourseFileResource(@RequestBody @ApiParam(value = "云课堂录制资源对象", required = true) UploadCloudCourseParam param, CloudSubCourse beforeCloudSubCourse, CloudCourseResource beforeCloudCourseResource) {
        CloudCourseFileResource cloudCourseFileResource = new CloudCourseFileResource();
        cloudCourseFileResource.setCloudCourseResourceId(beforeCloudCourseResource.getId());
        cloudCourseFileResource.setCloudSubCourseId(beforeCloudSubCourse.getId());
        cloudCourseFileResource.setSchoolId(beforeCloudSubCourse.getSchoolId());
        cloudCourseFileResource.setUrl(param.getPath());
        cloudCourseFileResource.setName(beforeCloudSubCourse.getName()+"的录播视频");
        cloudCourseFileResource.setThumbnail(param.getPath()+ VideoThumbnailUtils.getVideoThumbnailUtils(1));
        cloudCourseFileResource.setType("1");
        cloudCourseFileResource.setStatus(param.getStatus());
        cloudCourseFileResource.setFlag(param.getFlag());
        cloudCourseFileResource.setCreateTime(DateUtil.now());
        return cloudCourseFileResource;
    }

    /**
     * 装载课程资源对象
     */
    private CloudCourseResource assembleCloudCourseResource(CloudSubCourse beforeCloudSubCourse, CloudCourse cloudCourse) {
        CloudCourseResource cloudCourseResource = new CloudCourseResource();
        cloudCourseResource.setSchoolId(beforeCloudSubCourse.getSchoolId());
        cloudCourseResource.setCloudCourseId(beforeCloudSubCourse.getCloudCourse().getId());
        cloudCourseResource.setCreateTeacher(cloudCourse.getCreateTeacher());
        cloudCourseResource.setTeacher(beforeCloudSubCourse.getTeacher());
        cloudCourseResource.setCloudCourseName(beforeCloudSubCourse.getName());
        cloudCourseResource.setBeginTime(beforeCloudSubCourse.getStartTime());
        Integer courseTime = cloudCourseResource.getCourseTime();
        courseTime = (courseTime==null?0:courseTime);
        long duration = DateUtil.between(DateUtil.parse(beforeCloudSubCourse.getStartTime()),DateUtil.parse(beforeCloudSubCourse.getEndTime()), DateUnit.MINUTE);
        courseTime+=(int)duration;
        cloudCourseResource.setCourseTime(courseTime);
        cloudCourseResource.setListenTeachers(cloudCourse.getListenTeachers());
        cloudCourseResource.setOtherSchoolAccounts(cloudCourse.getOtherSchoolAccounts());
        cloudCourseResource.setSchoolId(beforeCloudSubCourse.getSchoolId());
        return cloudCourseResource;
    }

    @PostMapping("/findCloudCourseResourceListByCondition")
    @ApiOperation(value = "根据条件查找云课堂录制资源列表", notes = "返回云课堂录制资源列表")
    public List<CloudCourseResource> findCloudCourseResourceListByCondition(
            @ApiParam(value = "云课堂录制资源对象")
            @RequestBody CloudCourseResource cloudCourseResource){
        return cloudCourseResourceService.findCloudCourseResourceListByCondition(cloudCourseResource);
    }

    @PostMapping("/findRecordingAndBroadcastingResources")
    @ApiOperation(value = "根据条件查找老师个人录播资源列表", notes = "返回云课堂录制资源列表")
    public List<CloudCourseResource> findRecordingAndBroadcastingResources(
            @ApiParam(value = "云课堂录制资源对象")
            @RequestBody CloudCourseResource cloudCourseResource){
        return cloudCourseResourceService.findRecordingAndBroadcastingResources(cloudCourseResource);
    }

    @PostMapping("/findCloudCourseResourceCountByCondition")
    @ApiOperation(value = "根据条件查找云课堂录制资源列表个数", notes = "返回云课堂录制资源总个数")
    public long findCloudCourseResourceCountByCondition(
            @ApiParam(value = "云课堂录制资源对象")
            @RequestBody CloudCourseResource cloudCourseResource){
        return cloudCourseResourceService.findCloudCourseResourceCountByCondition(cloudCourseResource);
    }

    @PostMapping("/updateCloudCourseResource")
    @ApiOperation(value = "修改云课堂录制资源", notes = "云课堂录制资源对象必传")
    public void updateCloudCourseResource(
            @ApiParam(value = "云课堂录制资源对象,对象属性不为空则修改", required = true)
            @RequestBody CloudCourseResource cloudCourseResource){
        cloudCourseResourceService.updateCloudCourseResource(cloudCourseResource);
    }


    @GetMapping("/deleteCloudCourseResourceRe/{id}")
    @ApiOperation(value = "通过id删除云课堂录制资源")
    public void deleteCloudCourseResourceRe(
            @ApiParam(value = "云课堂录制资源对象", required = true)
            @PathVariable String id){
        cloudCourseResourceService.deleteCloudCourseResourceRe(id);
    }
    @GetMapping("/deleteCloudCourseResource/{id}")
    @ApiOperation(value = "通过id删除云课堂录制资源")
    public void deleteCloudCourseResource(
            @ApiParam(value = "云课堂录制资源对象", required = true)
            @PathVariable String id){
        cloudCourseResourceService.deleteCloudCourseResource(id);
    }
    @PostMapping("/deleteCloudCourseResourceByCondition")
    @ApiOperation(value = "根据条件删除云课堂录制资源")
    public void deleteCloudCourseResourceByCondition(
            @ApiParam(value = "云课堂录制资源对象")
            @RequestBody CloudCourseResource cloudCourseResource){
        cloudCourseResourceService.deleteCloudCourseResourceByCondition(cloudCourseResource);
    }
    @PostMapping("/findOneCloudCourseResourceByCondition")
    @ApiOperation(value = "根据条件查找单个云课堂录制资源,结果必须为单条数据", notes = "返回单个云课堂录制资源,没有时为空")
    public CloudCourseResource findOneCloudCourseResourceByCondition(
            @ApiParam(value = "云课堂录制资源对象")
            @RequestBody CloudCourseResource cloudCourseResource){
        return cloudCourseResourceService.findOneCloudCourseResourceByCondition(cloudCourseResource);
    }

    @PostMapping("/findResourceRecordByCondition")
    @ApiOperation(value = "根据条件查找云课程列表", notes = "返回云课程列表")
    public List<CloudCourseResource> findResourceRecordByCondition(
            @ApiParam(value = "云课程对象")
            @RequestBody CloudCourseResource cloudCourseResource){
        List<CloudCourseResource> resourceRecordList = cloudCourseResourceService.findCloudCourseResourceListByCondition(cloudCourseResource);
        CloudSubCourse cloudSubCourse=new CloudSubCourse();
        CloudCourseFileResource cloudCourseFileResource=new CloudCourseFileResource();
        CloudCourse c=new CloudCourse();
        resourceRecordList.forEach(s->{
            c.setId(s.getCloudCourseId());
            cloudSubCourse.setCloudCourse(c);
            cloudSubCourse.setStatus(3);
            long num = cloudSubCourseService.findCloudSubCourseCountByCondition(cloudSubCourse); //已完成课程
            cloudSubCourse.setStatus(null);
            long num1 = cloudSubCourseService.findCloudSubCourseCountByCondition(cloudSubCourse); //所有课程
            s.setCompletedCourse(num+"/"+num1);
            cloudCourseFileResource.setCloudCourseResourceId(s.getId());
            long cloudCourseFileResourceCount = cloudCourseFileResourceService.findCloudCourseFileResourceCountByCondition(cloudCourseFileResource);
            s.setCount(cloudCourseFileResourceCount);
        });
        return resourceRecordList;
    }

    @GetMapping("/deleteResourceMsg/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public void deleteResourceMsg(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        CloudCourseResource cloudCourseResource = cloudCourseResourceService.findCloudCourseResourceById(id);
        CloudCourseFileResource cloudCourseFileResource=new CloudCourseFileResource();
        cloudCourseFileResource.setCloudCourseResourceId(cloudCourseResource.getId());
        cloudCourseFileResourceService.deleteCloudCourseFileResourceByCondition(cloudCourseFileResource);
        cloudCourseResourceService.deleteCloudCourseResource(id);
    }
}
