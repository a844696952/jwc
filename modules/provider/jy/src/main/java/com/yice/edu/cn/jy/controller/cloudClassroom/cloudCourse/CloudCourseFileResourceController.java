package com.yice.edu.cn.jy.controller.cloudClassroom.cloudCourse;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseFileResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudSubCourse;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseFileResourceService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseResourceService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudCourseService;
import com.yice.edu.cn.jy.service.cloudClassroom.cloudCourse.CloudSubCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloudCourseFileResource")
@Api(value = "/cloudCourseFileResource",description = "模块")
public class CloudCourseFileResourceController {
    @Autowired
    private CloudCourseFileResourceService cloudCourseFileResourceService;
    @Autowired
    private CloudSubCourseService cloudSubCourseService;
    @Autowired
    private CloudCourseService cloudCourseService;
    @Autowired
    private CloudCourseResourceService courseResourceService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findCloudCourseFileResourceById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public CloudCourseFileResource findCloudCourseFileResourceById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return cloudCourseFileResourceService.findCloudCourseFileResourceById(id);
    }

    @PostMapping("/saveCloudCourseFileResource")
    @ApiOperation(value = "保存", notes = "返回对象")
    public CloudCourseFileResource saveCloudCourseFileResource(
            @ApiParam(value = "对象", required = true)
            @RequestBody CloudCourseFileResource CloudCourseFileResource){
        cloudCourseFileResourceService.saveCloudCourseFileResource(CloudCourseFileResource);
        return CloudCourseFileResource;
    }

    @PostMapping("/batchSaveCloudCourseFileResource")
    @ApiOperation(value = "批量保存")
    public void batchSaveCloudCourseFileResource(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<CloudCourseFileResource> CloudCourseFileResources){
        cloudCourseFileResourceService.batchSaveCloudCourseFileResource(CloudCourseFileResources);
    }

    @PostMapping("/findCloudCourseFileResourceListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<CloudCourseFileResource> findCloudCourseFileResourceListByCondition(
            @ApiParam(value = "对象")
            @RequestBody CloudCourseFileResource CloudCourseFileResource){
        return cloudCourseFileResourceService.findCloudCourseFileResourceListByCondition(CloudCourseFileResource);
    }
    @PostMapping("/findCloudCourseFileResourceCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findCloudCourseFileResourceCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody CloudCourseFileResource CloudCourseFileResource){
        return cloudCourseFileResourceService.findCloudCourseFileResourceCountByCondition(CloudCourseFileResource);
    }

    @PostMapping("/updateCloudCourseFileResource")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateCloudCourseFileResource(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody CloudCourseFileResource CloudCourseFileResource){
        cloudCourseFileResourceService.updateCloudCourseFileResource(CloudCourseFileResource);
    }
    @PostMapping("/updateCloudCourseFileResourceForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateCloudCourseFileResourceForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody CloudCourseFileResource CloudCourseFileResource){
        cloudCourseFileResourceService.updateCloudCourseFileResourceForAll(CloudCourseFileResource);
    }

    @GetMapping("/deleteCloudCourseFileResource/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteCloudCourseFileResource(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        cloudCourseFileResourceService.deleteCloudCourseFileResource(id);
    }
    @PostMapping("/deleteCloudCourseFileResourceByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteCloudCourseFileResourceByCondition(
            @ApiParam(value = "对象")
            @RequestBody CloudCourseFileResource CloudCourseFileResource){
        cloudCourseFileResourceService.deleteCloudCourseFileResourceByCondition(CloudCourseFileResource);
    }
    @PostMapping("/findOneCloudCourseFileResourceByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public CloudCourseFileResource findOneCloudCourseFileResourceByCondition(
            @ApiParam(value = "对象")
            @RequestBody CloudCourseFileResource CloudCourseFileResource){
        return cloudCourseFileResourceService.findOneCloudCourseFileResourceByCondition(CloudCourseFileResource);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @GetMapping("/findCloudCourseFileResourceByCloudCourseId/{id}")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response= CloudCourse.class)
    public CloudCourseResource findCloudCourseFileResourceByCloudCourseId(@PathVariable String id){
        CloudCourseResource cloudCourseResource = courseResourceService.findCloudCourseResourceById(id);
        CloudCourse c=new CloudCourse();
        c.setId(cloudCourseResource.getCloudCourseId());
        CloudSubCourse cloudSubCourse=new CloudSubCourse();
        cloudSubCourse.setCloudCourse(c);

        List<CloudSubCourse> cloudSubCourseList = cloudSubCourseService.findCloudSubCourseListByCondition(cloudSubCourse);
        /*CloudCourse cloudCourse=cloudCourseService.findCloudCourseById(id);*/

        cloudSubCourse.setStatus(3);
        long num = cloudSubCourseService.findCloudSubCourseCountByCondition(cloudSubCourse); //已完成课程
        cloudSubCourse.setStatus(null);
        long num1 = cloudSubCourseService.findCloudSubCourseCountByCondition(cloudSubCourse); //所有课程
        cloudCourseResource.setCompletedCourse(num+"/"+num1);

        cloudSubCourseList.forEach(s->{
            cloudCourseResource.setCloudSubCourseList(cloudSubCourseList);
        });
        cloudCourseResource.setCourseHour(minTohour(cloudCourseResource.getCourseTime()));
        return cloudCourseResource;
    }

    @PostMapping("/deleteCloudCourseFileResourceByList")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCloudCourseFileResourceByList(
            @ApiParam(value = "被删除记录的id", required = true)
            @RequestBody List<String> ids){
        ids.forEach(s->{
            cloudCourseFileResourceService.deleteCloudCourseFileResource(s);
        });
        return new ResponseJson();
    }

    private String minTohour(int time){
        int hours = (int) Math.floor(time / 60);
        int minute = time % 60;
        return hours + "小时" + minute + "分钟";
    }
}
