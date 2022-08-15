package com.yice.edu.cn.jw.controller.dd;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.jw.service.dd.DdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dd")
@Api(value = "/dd",description = "数据字典系统维护字段模块")
public class DdController {
    @Autowired
    private DdService ddService;

    @GetMapping("/findDdById/{id}")
    @ApiOperation(value = "通过id查找数据字典系统维护字段", notes = "返回数据字典系统维护字段对象")
    public Dd findDdById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return ddService.findDdById(id);
    }

    @PostMapping("/saveDd")
    @ApiOperation(value = "保存数据字典系统维护字段", notes = "返回数据字典系统维护字段对象")
    public Dd saveDd(
            @ApiParam(value = "数据字典系统维护字段对象", required = true)
            @RequestBody Dd dd){
        ddService.saveDd(dd);
        return dd;
    }

    @PostMapping("/findDdListByCondition")
    @ApiOperation(value = "根据条件查找数据字典系统维护字段列表", notes = "返回数据字典系统维护字段列表")
    public List<Dd> findDdListByCondition(
            @ApiParam(value = "数据字典系统维护字段对象")
            @RequestBody Dd dd){
        return ddService.findDdListByCondition(dd);
    }
    @PostMapping("/findDdCountByCondition")
    @ApiOperation(value = "根据条件查找数据字典系统维护字段列表个数", notes = "返回数据字典系统维护字段总个数")
    public long findDdCountByCondition(
            @ApiParam(value = "数据字典系统维护字段对象")
            @RequestBody Dd dd){
        return ddService.findDdCountByCondition(dd);
    }

    @PostMapping("/updateDd")
    @ApiOperation(value = "修改数据字典系统维护字段", notes = "数据字典系统维护字段对象必传")
    public void updateDd(
            @ApiParam(value = "数据字典系统维护字段对象,对象属性不为空则修改", required = true)
            @RequestBody Dd dd){
        ddService.updateDd(dd);
    }

    @GetMapping("/deleteDd/{id}")
    @ApiOperation(value = "通过id删除数据字典系统维护字段")
    public void deleteDd(
            @ApiParam(value = "数据字典系统维护字段对象", required = true)
            @PathVariable String id){
        ddService.deleteDd(id);
    }


    @PostMapping("/findJwClassesList")
    @ApiOperation(value = "根据年级查询班级树结构", notes = "根据年级查询班级树结构")
    public List<Dd> findJwClassesList(
            @ApiParam(value = "数据字典系统维护字段对象")
            @RequestBody Dd dd){
        return ddService.findJwClassesList(dd);
    }

    @PostMapping("/findDdListBySchoolId/{schoolId}")
    @ApiOperation(value = "根据学校id查找数据字典系统维护字段列表", notes = "返回数据字典系统维护字段列表")
    public List<Dd> findDdListBySchoolId(
            @ApiParam(value = "学校id")
            @PathVariable("schoolId") String schoolId){
        return ddService.findDdListBySchoolId(schoolId);
    }
}
