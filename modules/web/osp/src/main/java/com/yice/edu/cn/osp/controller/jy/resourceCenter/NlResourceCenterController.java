package com.yice.edu.cn.osp.controller.jy.resourceCenter;


import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenter;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.jy.resourceCenter.ResourceCenterService;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import com.yice.edu.cn.osp.service.jy.resourceCenter.ResourceCenterConditionService;
import com.yice.edu.cn.osp.service.jy.resourceCenter.ResourceCenterTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/h5nl/resourceCenter")
@Api(value = "/h5nl/resourceCenter",description = "H5资源中心")
public class NlResourceCenterController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ResourceCenterConditionService resourceCenterConditionService;
    @Autowired
    private ResourceCenterTypeService resourceCenterTypeService;
    @Autowired
    private ResourceCenterService resourceCenterService;

    @PostMapping("/findResourceCentersForH5ByCondition")
    @ApiOperation(value = "根据条件查找资源中心列表(conditionIdList:条件id集合，schoolId,releaseStatus:发布状态：1、未发布  2、已发布)", notes = "返回响应对象", response=ResourceCenter.class)
    public ResponseJson findResourceCentersForH5ByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenter resourceCenter){
        List<ResourceCenter> data=resourceCenterService.findResourceCentersForH5ByCondition(resourceCenter);
        long count=resourceCenterService.findResourceCenterCountForH5ByCondition(resourceCenter);
        return new ResponseJson(data,count);
    }

    @PostMapping("/look/lookResourceCenterTypeById/findTypeAll")
    @ApiOperation(value = "查询分类", notes = "返回响应对象,不包含总条数", response= ResourceCenterCondition.class)
    public ResponseJson findTypeAndCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ResourceCenterType resourceCenterType){
        //resourceCenterType.setSchoolId(mySchoolId());
        List<ResourceCenterType> type =
                resourceCenterTypeService.findResourceCenterTypeListByCondition(resourceCenterType);
        return new ResponseJson(type);
    }


    @PostMapping("/look/findTypeCondtionMap")
    @ApiOperation(value = "查询分类和条件", notes = "返回响应对象,不包含总条数", response= ResourceCenterCondition.class)
    public ResponseJson findTypeCondtionMap(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ResourceCenterType resourceCenterType){
        //resourceCenterType.setSchoolId(mySchoolId());
        List<ResourceCenterType> type =
                resourceCenterTypeService.findResourceCenterTypeListByConditionMap(resourceCenterType);
        return new ResponseJson(type);
    }

    @PostMapping("/look/lookResourceCenterTypeById/findCondtionAll")
    @ApiOperation(value = "查询条件", notes = "返回响应对象,不包含总条数", response=ResourceCenterCondition.class)
    public ResponseJson findCondtionAll(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ResourceCenterType resourceCenterType){
        ResourceCenterCondition resourceCenterCondition = new ResourceCenterCondition();
        resourceCenterCondition.setTypeId(resourceCenterType.getId());
        List<ResourceCenterCondition> condition =
                resourceCenterConditionService.findResourceCenterConditionListByCondition(resourceCenterCondition);
        return new ResponseJson(condition);
    }

    @GetMapping("/look/findTeacherById/{id}")
    @ApiOperation(value = "根据教师id查找教师", notes = "返回响应对象")
    public ResponseJson findTeacherById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Teacher teacher = teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }

    @GetMapping("/look/lookResourceCenterById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资源中心信息表", notes = "返回响应对象", response=ResourceCenter.class)
    public ResponseJson lookResourceCenterById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ResourceCenter resourceCenter=resourceCenterService.findResourceCenterById(id);
        return new ResponseJson(resourceCenter);
    }
}
