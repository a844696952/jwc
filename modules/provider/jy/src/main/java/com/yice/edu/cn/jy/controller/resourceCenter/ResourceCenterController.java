package com.yice.edu.cn.jy.controller.resourceCenter;

import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenter;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.TeacherCourse;
import com.yice.edu.cn.jy.service.resourceCenter.ResourceCenterService;
import com.yice.edu.cn.jy.service.resourceCenter.ResourceCenterTypeConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resourceCenter")
@Api(value = "/resourceCenter",description = "资源中心信息表模块")
public class ResourceCenterController {
    @Autowired
    private ResourceCenterService resourceCenterService;
    @Autowired
    private ResourceCenterTypeConditionService resourceCenterTypeConditionService;


    @GetMapping("/findResourceCenterById/{id}")
    @ApiOperation(value = "通过id查找资源中心信息表", notes = "返回资源中心信息表对象")
    public ResourceCenter findResourceCenterById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return resourceCenterService.findResourceCenterById(id);
    }

    @PostMapping("/saveResourceCenter")
    @ApiOperation(value = "保存资源中心信息表", notes = "返回资源中心信息表对象")
    public ResourceCenter saveResourceCenter(
            @ApiParam(value = "资源中心信息表对象", required = true)
            @RequestBody ResourceCenter resourceCenter){
        resourceCenterService.saveResourceCenter(resourceCenter);
        return resourceCenter;
    }

    @PostMapping("/findResourceCenterListByCondition")
    @ApiOperation(value = "根据条件查找资源中心信息表列表", notes = "返回资源中心信息表列表")
    public List<ResourceCenter> findResourceCenterListByCondition(
            @ApiParam(value = "资源中心信息表对象")
            @RequestBody ResourceCenter resourceCenter){
        return resourceCenterService.findResourceCenterListByCondition(resourceCenter);
    }
    @PostMapping("/findResourceCenterCountByCondition")
    @ApiOperation(value = "根据条件查找资源中心信息表列表个数", notes = "返回资源中心信息表总个数")
    public long findResourceCenterCountByCondition(
            @ApiParam(value = "资源中心信息表对象")
            @RequestBody ResourceCenter resourceCenter){
        return resourceCenterService.findResourceCenterCountByCondition(resourceCenter);
    }

    @PostMapping("/updateResourceCenter")
    @ApiOperation(value = "修改资源中心信息表", notes = "资源中心信息表对象必传")
    public void updateResourceCenter(
            @ApiParam(value = "资源中心信息表对象,对象属性不为空则修改", required = true)
            @RequestBody ResourceCenter resourceCenter){
        resourceCenter.setReleaseStatus("2");
        resourceCenterService.updateResourceCenter(resourceCenter);
        ResourceCenterTypeCondition typeCondition=new ResourceCenterTypeCondition();
        typeCondition.setResourceCenterId(resourceCenter.getId());
        resourceCenterTypeConditionService.deleteResourceCenterTypeConditionByCondition(typeCondition);
        resourceCenter.getTypeConditionList().forEach(
                resourceCenterTypeCondition -> {
                    resourceCenterTypeCondition.setResourceCenterId(resourceCenter.getId());
                    resourceCenterTypeCondition.setSchoolId(resourceCenter.getSchoolId());
                    resourceCenterTypeConditionService.saveResourceCenterTypeCondition(resourceCenterTypeCondition);
                }

        );


    }

    @GetMapping("/deleteResourceCenter/{id}")
    @ApiOperation(value = "通过id删除资源中心信息表")
    public void deleteResourceCenter(
            @ApiParam(value = "资源中心信息表对象", required = true)
            @PathVariable String id){
        resourceCenterService.deleteResourceCenter(id);
    }
    @PostMapping("/deleteResourceCenterByCondition")
    @ApiOperation(value = "根据条件删除资源中心信息表")
    public void deleteResourceCenterByCondition(
            @ApiParam(value = "资源中心信息表对象")
            @RequestBody ResourceCenter resourceCenter){
        resourceCenterService.deleteResourceCenterByCondition(resourceCenter);
    }
    @PostMapping("/findOneResourceCenterByCondition")
    @ApiOperation(value = "根据条件查找单个资源中心信息表,结果必须为单条数据", notes = "返回单个资源中心信息表,没有时为空")
    public ResourceCenter findOneResourceCenterByCondition(
            @ApiParam(value = "资源中心信息表对象")
            @RequestBody ResourceCenter resourceCenter){
        return resourceCenterService.findOneResourceCenterByCondition(resourceCenter);
    }

    @PostMapping("/findTeacherCourseListBySchoolId")
    @ApiOperation(value = "根据学校id获取全校老师与所教课程信息", notes = "返回单个资源中心信息表,没有时为空")
    public List<TeacherCourse> findTeacherCourseListBySchoolId( @ApiParam(value = "资源中心信息表对象")
                                                               @RequestBody TeacherCourse teacherCourse){
        return resourceCenterService.findTeacherCourseListBySchoolId(teacherCourse);
    }

    @PostMapping("/findTeacherCourseCountBySchoolId")
    @ApiOperation(value = "根据学校id获取全校老师与所教课程信息的条数", notes = "返回单个资源中心信息表,没有时为空")
    public long findTeacherCourseCountBySchoolId( @ApiParam(value = "资源中心信息表对象")
                                                                @RequestBody TeacherCourse teacherCourse){
        return resourceCenterService.findTeacherCourseCountBySchoolId(teacherCourse);
    }


    @PostMapping("/findResourceCentersForH5ByCondition")
    @ApiOperation(value = "根据条件查找资源中心信息表列表h5", notes = "返回资源中心信息表列表")
    public List<ResourceCenter> findResourceCentersForH5ByCondition(
            @ApiParam(value = "资源中心信息表对象")
            @RequestBody ResourceCenter resourceCenter){
        return resourceCenterService.findResourceCentersForH5ByCondition(resourceCenter);
    }

    @PostMapping("/findResourceCenterCountForH5ByCondition")
    @ApiOperation(value = "根据条件查找资源中心信息表列表个数h5", notes = "返回资源中心信息表总个数")
    public long findResourceCenterCountForH5ByCondition(
            @ApiParam(value = "资源中心信息表对象")
            @RequestBody ResourceCenter resourceCenter){
        return resourceCenterService.findResourceCenterCountForH5ByCondition(resourceCenter);
    }





}
