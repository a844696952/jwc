package com.yice.edu.cn.osp.controller.jy.cloudClassroom.cloudCourse;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseFileResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.feignClient.jy.cloudClassroom.cloudCourse.CloudSubCourseFeign;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudCourseFileResourceService;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudCourseResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/cloudCourseResource")
@Api(value = "/cloudCourseResource",description = "云课堂录制资源模块")
public class CloudCourseResourceController {
    @Autowired
    private CloudCourseResourceService cloudCourseResourceService;
    @Autowired
    private CloudCourseFileResourceService cloudCourseFileResourceService;
    @Autowired
    private CloudSubCourseFeign cloudSubCourseFeign;

    @PostMapping("/ignore/saveCloudCourseResource")
    @ApiOperation(value = "保存云课堂录制资源对象", notes = "返回保存好的云课堂录制资源对象", response=CloudCourseResource.class)
    public ResponseJson saveCloudCourseResource(
            @ApiParam(value = "云课堂录制资源对象", required = true)
            @RequestBody CloudCourseResource cloudCourseResource){
       cloudCourseResource.setSchoolId(mySchoolId());
        CloudCourseResource s=cloudCourseResourceService.saveCloudCourseResource(cloudCourseResource);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCloudCourseResourceById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找云课堂录制资源", notes = "返回响应对象", response=CloudCourseResource.class)
    public ResponseJson findCloudCourseResourceById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudCourseResource cloudCourseResource=cloudCourseResourceService.findCloudCourseResourceById(id);
        return new ResponseJson(cloudCourseResource);
    }

    @PostMapping("/update/updateCloudCourseResource")
    @ApiOperation(value = "修改云课堂录制资源对象", notes = "返回响应对象")
    public ResponseJson updateCloudCourseResource(
            @ApiParam(value = "被修改的云课堂录制资源对象,对象属性不为空则修改", required = true)
            @RequestBody CloudCourseResource cloudCourseResource){
        cloudCourseResourceService.updateCloudCourseResource(cloudCourseResource);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCloudCourseResourceById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找云课堂录制资源", notes = "返回响应对象", response=CloudCourseResource.class)
    public ResponseJson lookCloudCourseResourceById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudCourseResource cloudCourseResource=cloudCourseResourceService.findCloudCourseResourceById(id);
        return new ResponseJson(cloudCourseResource);
    }

  /*  @PostMapping("/findCloudCourseResourcesByCondition")
    @ApiOperation(value = "根据条件查找云课堂录制资源", notes = "返回响应对象", response=CloudCourseResource.class)
    public ResponseJson findCloudCourseResourcesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourseResource cloudCourseResource){
       cloudCourseResource.setSchoolId(mySchoolId());
        List<CloudCourseResource> data=cloudCourseResourceService.findCloudCourseResourceListByCondition(cloudCourseResource);
        long count=cloudCourseResourceService.findCloudCourseResourceCountByCondition(cloudCourseResource);
        return new ResponseJson(data,count);
    }*/
    @PostMapping("/findOneCloudCourseResourceByCondition")
    @ApiOperation(value = "根据条件查找单个云课堂录制资源,结果必须为单条数据", notes = "没有时返回空", response=CloudCourseResource.class)
    public ResponseJson findOneCloudCourseResourceByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CloudCourseResource cloudCourseResource){
        CloudCourseResource one=cloudCourseResourceService.findOneCloudCourseResourceByCondition(cloudCourseResource);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCloudCourseResource/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCloudCourseResource(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        cloudCourseResourceService.deleteCloudCourseResource(id);
        return new ResponseJson();
    }


    @PostMapping("/findCloudCourseResourceListByCondition")
    @ApiOperation(value = "根据条件查找云课堂录制资源列表", notes = "返回响应对象,不包含总条数", response=CloudCourseResource.class)
    public ResponseJson findCloudCourseResourceListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourseResource cloudCourseResource){
       cloudCourseResource.setSchoolId(mySchoolId());
        List<CloudCourseResource> data=cloudCourseResourceService.findCloudCourseResourceListByCondition(cloudCourseResource);
        return new ResponseJson(data);
    }

    @PostMapping("/findCloudCourseResourcesByCondition")
    @ApiOperation(value = "根据条件查找老师个人录播资源列表", notes = "返回响应对象,不包含总条数", response=CloudCourseResource.class)
    public ResponseJson findRecordingAndBroadcastingResources(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourseResource cloudCourseResource){
        cloudCourseResource.setSchoolId(mySchoolId());
        Teacher teacher=new Teacher();
        teacher.setId(myId());
        cloudCourseResource.setTeacher(teacher);
        List<CloudCourseResource> data=cloudCourseResourceService.findRecordingAndBroadcastingResources(cloudCourseResource);
        int count = 0;
        if(data!=null && data.size()>0){
            count = data.size();
        }
        return new ResponseJson(data,count);
    }


    @PostMapping("/lookResource/findOneCloudCourseResourceByCondition")
    @ApiOperation(value = "根据CloudCourseResourceId查询对应的视频信息", notes = "返回响应对象", response= CloudCourseFileResource.class)
    public ResponseJson findCCFileResourcesByCloudCourseResourceId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CloudCourseFileResource cloudCourseFileResource){
        List<CloudCourseFileResource> data=cloudCourseFileResourceService.findCloudCourseFileResourceListByCondition(cloudCourseFileResource);
        return new ResponseJson(data);
    }

    @GetMapping("/deleteCloudCourseResource/re/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCloudCourseResourceTemp(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){

        cloudCourseResourceService.deleteCloudCourseResourceRe(id);
        return new ResponseJson();
    }

    @PostMapping("/findResourceRecordByCondition")
    @ApiOperation(value = "根据条件查找云课程", notes = "返回响应对象", response= CloudCourseResource.class)
    public ResponseJson findResourceRecordByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourseResource cloudCourseResource){
        List<CloudCourseResource> data=cloudCourseResourceService.findResourceRecordByCondition(cloudCourseResource);
        long count=cloudCourseResourceService.findCloudCourseResourceCountByCondition(cloudCourseResource);
        return new ResponseJson(data,count);
    }

    @PostMapping("/startCourse")
    @ApiOperation(value = "根据CloudCourseResourceId查询对应的视频信息", notes = "返回响应对象", response= CloudCourseFileResource.class)
    public void startCourse(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CloudSubCourse cloudSubCourse){
        cloudSubCourseFeign.startCourse(cloudSubCourse);
    }

    @PostMapping("/endCourse")
    @ApiOperation(value = "根据CloudCourseResourceId查询对应的视频信息", notes = "返回响应对象", response= CloudCourseFileResource.class)
    public void endCourse(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CloudSubCourse cloudSubCourse){
        cloudSubCourseFeign.endCourse(cloudSubCourse);
    }

    @GetMapping("/deleteResourceMsg/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteResourceMsg(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        cloudCourseResourceService.deleteResourceMsg(id);
        return new ResponseJson();
    }

}
