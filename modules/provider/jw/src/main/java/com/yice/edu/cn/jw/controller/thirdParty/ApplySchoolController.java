package com.yice.edu.cn.jw.controller.thirdParty;

import com.yice.edu.cn.common.pojo.api.thirdParty.ApplySchool;
import com.yice.edu.cn.jw.service.thirdParty.ApplySchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applySchool")
@Api(value = "/applySchool",description = "绑定到学校的第三方应用")
public class ApplySchoolController {
    @Autowired
    private ApplySchoolService applySchoolService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findApplySchoolById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public ApplySchool findApplySchoolById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return applySchoolService.findApplySchoolById(id);
    }

    @PostMapping("/saveApplySchool")
    @ApiOperation(value = "保存", notes = "返回对象")
    public List<ApplySchool> saveApplySchool(
            @ApiParam(value = "对象", required = true)
            @RequestBody List<ApplySchool> applySchoolList){
        applySchoolService.saveApplySchool(applySchoolList);
        return applySchoolList;
    }

    @PostMapping("/batchSaveApplySchool")
    @ApiOperation(value = "批量保存")
    public void batchSaveApplySchool(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<ApplySchool> applySchools){
        applySchoolService.batchSaveApplySchool(applySchools);
    }

    @PostMapping("/findApplySchoolListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<ApplySchool> findApplySchoolListByCondition(
            @ApiParam(value = "对象")
            @RequestBody ApplySchool applySchool){
        return applySchoolService.findApplySchoolListByCondition(applySchool);
    }
    @PostMapping("/findApplySchoolCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findApplySchoolCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody ApplySchool applySchool){
        return applySchoolService.findApplySchoolCountByCondition(applySchool);
    }

    @PostMapping("/updateApplySchool")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateApplySchool(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody ApplySchool applySchool){
        applySchoolService.updateApplySchool(applySchool);
    }
    @PostMapping("/updateApplySchoolForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateApplySchoolForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody ApplySchool applySchool){
        applySchoolService.updateApplySchoolForAll(applySchool);
    }

    @GetMapping("/deleteApplySchool/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteApplySchool(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        applySchoolService.deleteApplySchool(id);
    }
    @PostMapping("/deleteApplySchoolByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteApplySchoolByCondition(
            @ApiParam(value = "对象")
            @RequestBody ApplySchool applySchool){
        applySchoolService.deleteApplySchoolByCondition(applySchool);
    }
    @PostMapping("/findOneApplySchoolByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public ApplySchool findOneApplySchoolByCondition(
            @ApiParam(value = "对象")
            @RequestBody ApplySchool applySchool){
        return applySchoolService.findOneApplySchoolByCondition(applySchool);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
