package com.yice.edu.cn.xw.controller.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageType;
import com.yice.edu.cn.common.pojo.xw.wage.WageTypeTeacher;
import com.yice.edu.cn.xw.service.wage.WageTypeTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wageTypeTeacher")
@Api(value = "/wageTypeTeacher",description = "模块")
public class WageTypeTeacherController {
    @Autowired
    private WageTypeTeacherService wageTypeTeacherService;

    @GetMapping("/findWageTypeTeacherById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public WageTypeTeacher findWageTypeTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return wageTypeTeacherService.findWageTypeTeacherById(id);
    }

    @PostMapping("/saveWageTypeTeacher")
    @ApiOperation(value = "保存", notes = "返回对象")
    public WageTypeTeacher saveWageTypeTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        wageTypeTeacherService.saveWageTypeTeacher(wageTypeTeacher);
        return wageTypeTeacher;
    }

    @PostMapping("/findWageTypeTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageTypeTeacher> findWageTypeTeacherListByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageTypeTeacher wageTypeTeacher){
        return wageTypeTeacherService.findWageTypeTeacherListByCondition(wageTypeTeacher);
    }
    @PostMapping("/findWageTypeTeacherListByWorkNum")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageTypeTeacher> findWageTypeTeacherListByWorkNum(
            @ApiParam(value = "对象")
            @RequestBody WageTypeTeacher wageTypeTeacher){
        return wageTypeTeacherService.findWageTypeTeacherListByWorkNum(wageTypeTeacher);
    }
    @PostMapping("/findWageTypeTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findWageTypeTeacherCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageTypeTeacher wageTypeTeacher){
        return wageTypeTeacherService.findWageTypeTeacherCountByCondition(wageTypeTeacher);
    }

    @PostMapping("/updateWageTypeTeacher")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateWageTypeTeacher(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        wageTypeTeacherService.updateWageTypeTeacher(wageTypeTeacher);
    }

    @GetMapping("/deleteWageTypeTeacher/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteWageTypeTeacher(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        wageTypeTeacherService.deleteWageTypeTeacher(id);
    }
    @PostMapping("/deleteWageTypeTeacherByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteWageTypeTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageTypeTeacher wageTypeTeacher){
        wageTypeTeacherService.deleteWageTypeTeacherByCondition(wageTypeTeacher);
    }
    @PostMapping("/findOneWageTypeTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public WageTypeTeacher findOneWageTypeTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody WageTypeTeacher wageTypeTeacher){
        return wageTypeTeacherService.findOneWageTypeTeacherByCondition(wageTypeTeacher);
    }

    @PostMapping("/saveWageTypeValue")
    @ApiOperation(value = "保存", notes = "返回对象")
    public WageTypeTeacher saveWageTypeValue(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        wageTypeTeacherService.saveWageTypeValue(wageTypeTeacher);
        return wageTypeTeacher;
    }

    @PostMapping("/findWageValueByTypeId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Map<String, String>> findWageValueByTypeId(
           @RequestBody Map<String,Object> wageTypeTeacherMap){
        return wageTypeTeacherService.findWageValueByTypeId(wageTypeTeacherMap);
    }
    @PostMapping("/findWageValueByRecordId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Map<String, Object>> findWageValueByRecordId(
            @RequestBody Map<String,Object> wageTypeRecordMap){
        return wageTypeTeacherService.findWageValueByRecordId(wageTypeRecordMap);
    }
    @PostMapping("/findWageValueByTeacherId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Map<String, Object>> findWageValueByTeacherId(
            @RequestBody Map<String,Object> wageTypeTeacherMap){
        return wageTypeTeacherService.findWageValueByTeacherId(wageTypeTeacherMap);
    }

    @PostMapping("/findWageAttributeListByRecordId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageTypeTeacher> findWageAttributeListByRecordId(
            @RequestBody WageTypeTeacher wageTypeTeacher){
        return wageTypeTeacherService.findWageAttributeListByRecordId(wageTypeTeacher);
    }

    @PostMapping("/findWageAttributeListByTeacherId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageTypeTeacher> findWageAttributeListByTeacherId(
            @RequestBody WageTypeTeacher wageTypeTeacher){
        return wageTypeTeacherService.findWageAttributeListByTeacherId(wageTypeTeacher);
    }

    @PostMapping("/findWageAttributeNameByTeacherId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<WageTypeTeacher> findWageAttributeNameByTeacherId(
            @RequestBody WageTypeTeacher wageTypeTeacher){
        return wageTypeTeacherService.findWageAttributeNameByTeacherId(wageTypeTeacher);
    }

    @PostMapping("/updateWageTypeValue")
    @ApiOperation(value = "保存", notes = "返回对象")
    public WageTypeTeacher updateWageTypeValue(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        wageTypeTeacherService.updateWageTypeValue(wageTypeTeacher);
        return wageTypeTeacher;
    }

    @GetMapping("/deleteWageValueByRecordId/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteWageValueByRecordId(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        wageTypeTeacherService.deleteWageValueByRecordId(id);
    }

    @PostMapping("/updateWageTypeTeacherReleaseState")
    @ApiOperation(value = "保存", notes = "返回对象")
    public void updateWageTypeTeacherReleaseState(
            @ApiParam(value = "对象", required = true)
            @RequestBody WageTypeTeacher wageTypeTeacher){
        wageTypeTeacherService.updateWageTypeTeacherReleaseState(wageTypeTeacher);
    }


    @PostMapping("/findWageValueByTypeIdCount")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findWageValueByTypeIdCount(
            @ApiParam(value = "对象")
            @RequestBody WageTypeTeacher wageTypeTeacher){
        return wageTypeTeacherService.findWageValueByTypeIdCount(wageTypeTeacher);
    }

    @PostMapping("/batchSaveWageTypeTeacher")
    public Map<String,Object> batchSaveWageTypeTeacher(@RequestBody List<WageTypeTeacher> wageTypeTeacherList){
        return wageTypeTeacherService.batchSaveWageTypeTeacher(wageTypeTeacherList);
    }
}
