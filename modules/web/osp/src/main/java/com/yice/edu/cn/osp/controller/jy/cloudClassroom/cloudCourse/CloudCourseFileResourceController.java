package com.yice.edu.cn.osp.controller.jy.cloudClassroom.cloudCourse;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;

import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourse;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseFileResource;
import com.yice.edu.cn.common.pojo.cc.cloudCourse.CloudCourseResource;
import com.yice.edu.cn.common.util.VideoThumbnailUtils;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudCourseFileResourceService;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudCourseService;
import com.yice.edu.cn.osp.service.jy.cloudClassroom.cloudCourse.CloudSubCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/cloudCourseFileResource")
@Api(value = "/cloudCourseFileResource",description = "模块")
public class CloudCourseFileResourceController {
    @Autowired
    private CloudCourseFileResourceService cloudCourseFileResourceService;
    /*@Autowired
    private CloudCourseService cloudCourseService;
    @Autowired
    private CloudSubCourseService cloudSubCourseService;*/

    @PostMapping("/saveCloudCourseFileResource")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= CloudCourseFileResource.class)
    public ResponseJson saveCloudCourseFileResource(
            @ApiParam(value = "对象", required = true)
            @RequestBody CloudCourseFileResource cloudCourseFileResource){
        cloudCourseFileResource.setThumbnail(cloudCourseFileResource.getUrl()+ VideoThumbnailUtils.getVideoThumbnailUtils(Constant.FILE_SERVICE.TYPE_QINIU));
        cloudCourseFileResource.setType("1");
        cloudCourseFileResource.setCreateTime(DateUtil.now());
        cloudCourseFileResource.setSchoolId(mySchoolId());
        CloudCourseFileResource s=cloudCourseFileResourceService.saveCloudCourseFileResource(cloudCourseFileResource);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCloudCourseFileResourceById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response= CloudCourseFileResource.class)
    public ResponseJson findcloudCourseFileResourceById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudCourseFileResource cloudCourseFileResource=cloudCourseFileResourceService.findCloudCourseFileResourceById(id);
        return new ResponseJson(cloudCourseFileResource);
    }

    @PostMapping("/update/updateCloudCourseFileResource")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateCloudCourseFileResource(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody CloudCourseFileResource cloudCourseFileResource){
        cloudCourseFileResourceService.updateCloudCourseFileResource(cloudCourseFileResource);
        return new ResponseJson();
    }

    @PostMapping("/update/updateCloudCourseFileResourceForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateCloudCourseFileResourceForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody CloudCourseFileResource cloudCourseFileResource){
        cloudCourseFileResourceService.updateCloudCourseFileResourceForAll(cloudCourseFileResource);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCloudCourseFileResourceById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response= CloudCourseFileResource.class)
    public ResponseJson lookcloudCourseFileResourceById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CloudCourseFileResource cloudCourseFileResource=cloudCourseFileResourceService.findCloudCourseFileResourceById(id);
        return new ResponseJson(cloudCourseFileResource);
    }

    @PostMapping("/findCloudCourseFileResourcesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response= CloudCourseFileResource.class)
    public ResponseJson findCloudCourseFileResourcesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourseFileResource cloudCourseFileResource){
        List<CloudCourseFileResource> data=cloudCourseFileResourceService.findCloudCourseFileResourceListByCondition(cloudCourseFileResource);
        long count=cloudCourseFileResourceService.findCloudCourseFileResourceCountByCondition(cloudCourseFileResource);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneCloudCourseFileResourceByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response= CloudCourseFileResource.class)
    public ResponseJson findOneCloudCourseFileResourceByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CloudCourseFileResource cloudCourseFileResource){
        CloudCourseFileResource one=cloudCourseFileResourceService.findOneCloudCourseFileResourceByCondition(cloudCourseFileResource);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCloudCourseFileResource/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCloudCourseFileResource(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        cloudCourseFileResourceService.deleteCloudCourseFileResource(id);
        return new ResponseJson();
    }


    @PostMapping("/findCloudCourseFileResourceListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response= CloudCourseFileResource.class)
    public ResponseJson findCloudCourseFileResourceListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CloudCourseFileResource cloudCourseFileResource){
        List<CloudCourseFileResource> data=cloudCourseFileResourceService.findCloudCourseFileResourceListByCondition(cloudCourseFileResource);
        return new ResponseJson(data);
    }

    @GetMapping("/findCloudCourseFileResourceByCloudCourseId/{id}")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response= CloudCourse.class)
    public ResponseJson findCloudCourseFileResourceByCloudCourseId(@PathVariable String id){
        /*CloudCourse c=new CloudCourse();
        c.setId(id);
        CloudSubCourse cloudSubCourse=new CloudSubCourse();
        cloudSubCourse.setCloudCourse(c);
        List<CloudSubCourse> cloudSubCourseList = cloudSubCourseService.findCloudSubCourseListByCondition(cloudSubCourse);
        CloudCourse cloudCourse=cloudCourseService.findCloudCourseById(id);
        cloudSubCourseList.forEach(s->{
                cloudCourse.setCloudSubCourseList(cloudSubCourseList);
        });*/
        CloudCourseResource cloudCourseResource = cloudCourseFileResourceService.findCloudCourseFileResourceByCloudCourseId(id);
        return new ResponseJson(cloudCourseResource);
    }

    @PostMapping("/findCloudCourseFileResourceByCloudSubCourseId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response= CloudCourseFileResource.class)
    public ResponseJson findCloudCourseFileResourceBycloudSubCourseId(@RequestBody CloudCourseFileResource cloudCourseFileResource){
        List<CloudCourseFileResource> cloudCourseFileResourceList = cloudCourseFileResourceService.findCloudCourseFileResourceListByCondition(cloudCourseFileResource);
        return new ResponseJson(cloudCourseFileResourceList);
    }

    @PostMapping("/deleteCloudCourseFileResourceByList")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCloudCourseFileResourceByList(
            @ApiParam(value = "被删除记录的id", required = true)
            @RequestBody List<String> ids){
        cloudCourseFileResourceService.deleteCloudCourseFileResourceByList(ids);
        return new ResponseJson();
    }


}
