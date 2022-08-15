package com.yice.edu.cn.jw.controller.eehManagement;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import com.yice.edu.cn.jw.service.eehManagement.EehSchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eehSchool")
@Api(value = "/eehSchool",description = "模块")
public class EehSchoolController {
    @Autowired
    private EehSchoolService eehSchoolService;

    @GetMapping("/findEehSchoolById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public EehSchool findEehSchoolById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return eehSchoolService.findEehSchoolById(id);
    }

    @PostMapping("/saveEehSchool")
    @ApiOperation(value = "保存", notes = "返回对象")
    public EehSchool saveEehSchool(
            @ApiParam(value = "对象", required = true)
            @RequestBody EehSchool eehSchool){
        eehSchoolService.saveEehSchool(eehSchool);
        return eehSchool;
    }

    @PostMapping("/findEehSchoolListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EehSchool> findEehSchoolListByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehSchool eehSchool){
        return eehSchoolService.findEehSchoolListByCondition(eehSchool);
    }
    @PostMapping("/findEehSchoolCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findEehSchoolCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehSchool eehSchool){
        return eehSchoolService.findEehSchoolCountByCondition(eehSchool);
    }

    @PostMapping("/updateEehSchool")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateEehSchool(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody EehSchool eehSchool){
        eehSchoolService.updateEehSchool(eehSchool);
    }

    @GetMapping("/deleteEehSchool/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteEehSchool(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        eehSchoolService.deleteEehSchool(id);
    }
    @PostMapping("/deleteEehSchoolByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteEehSchoolByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehSchool eehSchool){
        eehSchoolService.deleteEehSchoolByCondition(eehSchool);
    }
    @PostMapping("/findOneEehSchoolByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public EehSchool findOneEehSchoolByCondition(
            @ApiParam(value = "对象")
            @RequestBody EehSchool eehSchool){
        return eehSchoolService.findOneEehSchoolByCondition(eehSchool);
    }

    @PostMapping("/saveEehSchoolNew")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=EehSchool.class)
    public void saveEehSchoolNew(
            @ApiParam(value = "对象", required = true)
            @RequestBody EehSchool eehSchool){
        eehSchoolService.saveEehSchoolNew(eehSchool);
    }

    @GetMapping("/findCheckEehSchoolListById/{id}")
    public List<EehSchool> findCheckEehSchoolListById(@PathVariable String id){
        return  eehSchoolService.findCheckEehSchoolListById(id);
    }

    @PostMapping("/findEehSchoolListByEehIds")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<EehSchool> findEehSchoolListByEehIds(
            @ApiParam(value = "对象")
            @RequestBody List<String> eehids){
        return eehSchoolService.findEehSchoolListByEehIds(eehids);
    }
}
