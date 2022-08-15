package com.yice.edu.cn.jw.controller.thirdParty;

import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchoolTeacher;
import com.yice.edu.cn.jw.service.thirdParty.ApplySchoolTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applySchoolTeacher")
@Api(value = "/applySchoolTeacher",description = "绑定到学校中老师的第三方应用")
public class ApplySchoolTeacherController {
    @Autowired
    private ApplySchoolTeacherService applySchoolTeacherService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findApplySchoolTeacherById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public ApplySchoolTeacher findApplySchoolTeacherById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return applySchoolTeacherService.findApplySchoolTeacherById(id);
    }

    @PostMapping("/saveApplySchoolTeacher")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ApplySchoolTeacher saveApplySchoolTeacher(
            @ApiParam(value = "对象", required = true)
            @RequestBody ApplySchoolTeacher applySchoolTeacher){
        applySchoolTeacherService.saveApplySchoolTeacher(applySchoolTeacher);
        return applySchoolTeacher;
    }

    @PostMapping("/batchSaveApplySchoolTeacher")
    @ApiOperation(value = "批量保存")
    public void batchSaveApplySchoolTeacher(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<ApplySchoolTeacher> applySchoolTeachers){
        applySchoolTeacherService.batchSaveApplySchoolTeacher(applySchoolTeachers);
    }

    @PostMapping("/findApplySchoolTeacherListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ApplySchoolTeacher> findApplySchoolTeacherListByCondition(
            @ApiParam(value = "对象")
            @RequestBody ApplySchoolTeacher applySchoolTeacher){
        return applySchoolTeacherService.findApplySchoolTeacherListByCondition(applySchoolTeacher);
    }
    @PostMapping("/findApplySchoolTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findApplySchoolTeacherCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody ApplySchoolTeacher applySchoolTeacher){
        return applySchoolTeacherService.findApplySchoolTeacherCountByCondition(applySchoolTeacher);
    }

    @PostMapping("/updateApplySchoolTeacher")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateApplySchoolTeacher(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody ApplySchoolTeacher applySchoolTeacher){
        applySchoolTeacherService.updateApplySchoolTeacher(applySchoolTeacher);
    }
    @PostMapping("/updateApplySchoolTeacherForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateApplySchoolTeacherForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody ApplySchoolTeacher applySchoolTeacher){
        applySchoolTeacherService.updateApplySchoolTeacherForAll(applySchoolTeacher);
    }

    @GetMapping("/deleteApplySchoolTeacher/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteApplySchoolTeacher(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        applySchoolTeacherService.deleteApplySchoolTeacher(id);
    }
    @PostMapping("/deleteApplySchoolTeacherByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteApplySchoolTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody ApplySchoolTeacher applySchoolTeacher){
        applySchoolTeacherService.deleteApplySchoolTeacherByCondition(applySchoolTeacher);
    }
    @PostMapping("/findOneApplySchoolTeacherByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public ApplySchoolTeacher findOneApplySchoolTeacherByCondition(
            @ApiParam(value = "对象")
            @RequestBody ApplySchoolTeacher applySchoolTeacher){
        return applySchoolTeacherService.findOneApplySchoolTeacherByCondition(applySchoolTeacher);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
