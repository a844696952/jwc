package com.yice.edu.cn.jw.controller.educationBureau;

import com.yice.edu.cn.common.pojo.jw.educationBureau.EducationBureau;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.jw.service.educationBureau.EducationBureauService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/educationBureau")
@Api(value = "/educationBureau",description = "教育局模块")
public class EducationBureauController {
    @Autowired
    private EducationBureauService educationBureauService;

    @GetMapping("/findEducationBureauById/{id}")
    @ApiOperation(value = "通过id查找教育局", notes = "返回教育局对象")
    public EducationBureau findEducationBureauById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return educationBureauService.findEducationBureauById(id);
    }

    @PostMapping("/saveEducationBureau")
    @ApiOperation(value = "保存教育局", notes = "返回教育局对象")
    public EducationBureau saveEducationBureau(
            @ApiParam(value = "教育局对象", required = true)
            @RequestBody EducationBureau educationBureau){
        educationBureauService.saveEducationBureau(educationBureau);
        return educationBureau;
    }

    @PostMapping("/findEducationBureauListByCondition")
    @ApiOperation(value = "根据条件查找教育局列表", notes = "返回教育局列表")
    public List<EducationBureau> findEducationBureauListByCondition(
            @ApiParam(value = "教育局对象")
            @RequestBody EducationBureau educationBureau){
        return educationBureauService.findEducationBureauListByCondition(educationBureau);
    }
    @PostMapping("/findEducationBureauCountByCondition")
    @ApiOperation(value = "根据条件查找教育局列表个数", notes = "返回教育局总个数")
    public long findEducationBureauCountByCondition(
            @ApiParam(value = "教育局对象")
            @RequestBody EducationBureau educationBureau){
        return educationBureauService.findEducationBureauCountByCondition(educationBureau);
    }

    @PostMapping("/updateEducationBureau")
    @ApiOperation(value = "修改教育局", notes = "教育局对象必传")
    public void updateEducationBureau(
            @ApiParam(value = "教育局对象,对象属性不为空则修改", required = true)
            @RequestBody EducationBureau educationBureau){
        educationBureauService.updateEducationBureau(educationBureau);
    }

    @GetMapping("/deleteEducationBureau/{id}")
    @ApiOperation(value = "通过id删除教育局")
    public void deleteEducationBureau(
            @ApiParam(value = "教育局对象", required = true)
            @PathVariable String id){
        educationBureauService.deleteEducationBureau(id);
    }
    @PostMapping("/deleteEducationBureauByCondition")
    @ApiOperation(value = "根据条件删除教育局")
    public void deleteEducationBureauByCondition(
            @ApiParam(value = "教育局对象")
            @RequestBody EducationBureau educationBureau){
        educationBureauService.deleteEducationBureauByCondition(educationBureau);
    }
    @PostMapping("/findOneEducationBureauByCondition")
    @ApiOperation(value = "根据条件查找单个教育局,结果必须为单条数据", notes = "返回单个教育局,没有时为空")
    public EducationBureau findOneEducationBureauByCondition(
            @ApiParam(value = "教育局对象")
            @RequestBody EducationBureau educationBureau){
        return educationBureauService.findOneEducationBureauByCondition(educationBureau);
    }

    @GetMapping("/findUnSelectedSchoolsById/{educationBureauId}")
    public List<School> findUnSelectedSchoolsById(@PathVariable String educationBureauId){
        return educationBureauService.findUnSelectedSchoolsById(educationBureauId);
    }

    @GetMapping("/findSelectedSchoolsById/{educationBureauId}")
    public List<String> findSelectedSchoolsById(@PathVariable("educationBureauId")String educationBureauId){
        return educationBureauService.findSelectedSchoolsById(educationBureauId);
    }

}
