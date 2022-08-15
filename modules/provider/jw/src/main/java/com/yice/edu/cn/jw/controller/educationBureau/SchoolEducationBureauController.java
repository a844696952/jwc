package com.yice.edu.cn.jw.controller.educationBureau;

import com.yice.edu.cn.common.pojo.jw.educationBureau.SchoolEducationBureau;
import com.yice.edu.cn.jw.service.educationBureau.SchoolEducationBureauService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolEducationBureau")
@Api(value = "/schoolEducationBureau",description = "学校教育局关联表模块")
public class SchoolEducationBureauController {
    @Autowired
    private SchoolEducationBureauService schoolEducationBureauService;

    @GetMapping("/findSchoolEducationBureauById/{id}")
    @ApiOperation(value = "通过id查找学校教育局关联表", notes = "返回学校教育局关联表对象")
    public SchoolEducationBureau findSchoolEducationBureauById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolEducationBureauService.findSchoolEducationBureauById(id);
    }

    @PostMapping("/saveSchoolEducationBureau")
    @ApiOperation(value = "保存学校教育局关联表", notes = "返回学校教育局关联表对象")
    public SchoolEducationBureau saveSchoolEducationBureau(
            @ApiParam(value = "学校教育局关联表对象", required = true)
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        schoolEducationBureauService.saveSchoolEducationBureau(schoolEducationBureau);
        return schoolEducationBureau;
    }

    @PostMapping("/findSchoolEducationBureauListByCondition")
    @ApiOperation(value = "根据条件查找学校教育局关联表列表", notes = "返回学校教育局关联表列表")
    public List<SchoolEducationBureau> findSchoolEducationBureauListByCondition(
            @ApiParam(value = "学校教育局关联表对象")
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        return schoolEducationBureauService.findSchoolEducationBureauListByCondition(schoolEducationBureau);
    }
    @PostMapping("/findSchoolEducationBureauCountByCondition")
    @ApiOperation(value = "根据条件查找学校教育局关联表列表个数", notes = "返回学校教育局关联表总个数")
    public long findSchoolEducationBureauCountByCondition(
            @ApiParam(value = "学校教育局关联表对象")
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        return schoolEducationBureauService.findSchoolEducationBureauCountByCondition(schoolEducationBureau);
    }

    @PostMapping("/updateSchoolEducationBureau")
    @ApiOperation(value = "修改学校教育局关联表", notes = "学校教育局关联表对象必传")
    public void updateSchoolEducationBureau(
            @ApiParam(value = "学校教育局关联表对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        schoolEducationBureauService.updateSchoolEducationBureau(schoolEducationBureau);
    }

    @GetMapping("/deleteSchoolEducationBureau/{id}")
    @ApiOperation(value = "通过id删除学校教育局关联表")
    public void deleteSchoolEducationBureau(
            @ApiParam(value = "学校教育局关联表对象", required = true)
            @PathVariable String id){
        schoolEducationBureauService.deleteSchoolEducationBureau(id);
    }
    @PostMapping("/deleteSchoolEducationBureauByCondition")
    @ApiOperation(value = "根据条件删除学校教育局关联表")
    public void deleteSchoolEducationBureauByCondition(
            @ApiParam(value = "学校教育局关联表对象")
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        schoolEducationBureauService.deleteSchoolEducationBureauByCondition(schoolEducationBureau);
    }
    @PostMapping("/findOneSchoolEducationBureauByCondition")
    @ApiOperation(value = "根据条件查找单个学校教育局关联表,结果必须为单条数据", notes = "返回单个学校教育局关联表,没有时为空")
    public SchoolEducationBureau findOneSchoolEducationBureauByCondition(
            @ApiParam(value = "学校教育局关联表对象")
            @RequestBody SchoolEducationBureau schoolEducationBureau){
        return schoolEducationBureauService.findOneSchoolEducationBureauByCondition(schoolEducationBureau);
    }

    @PostMapping("/resetSchoolEducationBureaus")
    public void resetSchoolEducationBureaus(@RequestBody SchoolEducationBureau schoolEducationBureau){
        schoolEducationBureauService.resetSchoolEducationBureaus(schoolEducationBureau);
    }
}
