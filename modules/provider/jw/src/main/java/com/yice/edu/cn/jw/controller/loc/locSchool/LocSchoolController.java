package com.yice.edu.cn.jw.controller.loc.locSchool;

import com.yice.edu.cn.common.pojo.loc.LocSchool;
import com.yice.edu.cn.common.pojo.loc.LocSchoolExt;
import com.yice.edu.cn.jw.service.loc.locSchool.LocSchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locSchool")
@Api(value = "/locSchool",description = "学校模块")
public class LocSchoolController {
    @Autowired
    private LocSchoolService locSchoolService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findLocSchoolById/{id}")
    @ApiOperation(value = "通过id查找学校", notes = "返回学校对象")
    public LocSchool findLocSchoolById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return locSchoolService.findLocSchoolById(id);
    }

    @PostMapping("/saveLocSchool")
    @ApiOperation(value = "保存学校", notes = "返回学校对象")
    public LocSchool saveLocSchool(
            @ApiParam(value = "学校对象", required = true)
            @RequestBody LocSchool locSchool){
        locSchoolService.saveLocSchool(locSchool);
        return locSchool;
    }

    @PostMapping("/batchSaveLocSchool")
    @ApiOperation(value = "批量保存学校")
    public void batchSaveLocSchool(
            @ApiParam(value = "学校对象集合", required = true)
            @RequestBody List<LocSchool> locSchools){
        locSchoolService.batchSaveLocSchool(locSchools);
    }

    @PostMapping("/findLocSchoolListByCondition")
    @ApiOperation(value = "根据条件查找学校列表", notes = "返回学校列表")
    public List<LocSchool> findLocSchoolListByCondition(
            @ApiParam(value = "学校对象")
            @RequestBody LocSchool locSchool){
        return locSchoolService.findLocSchoolListByCondition(locSchool);
    }
    @PostMapping("/findLocSchoolCountByCondition")
    @ApiOperation(value = "根据条件查找学校列表个数", notes = "返回学校总个数")
    public long findLocSchoolCountByCondition(
            @ApiParam(value = "学校对象")
            @RequestBody LocSchool locSchool){
        return locSchoolService.findLocSchoolCountByCondition(locSchool);
    }

    @PostMapping("/updateLocSchool")
    @ApiOperation(value = "修改学校有值的字段", notes = "学校对象必传")
    public void updateLocSchool(
            @ApiParam(value = "学校对象,对象属性不为空则修改", required = true)
            @RequestBody LocSchool locSchool){
        locSchoolService.updateLocSchool(locSchool);
    }
    @PostMapping("/updateLocSchoolForAll")
    @ApiOperation(value = "修改学校所有字段", notes = "学校对象必传")
    public void updateLocSchoolForAll(
            @ApiParam(value = "学校对象", required = true)
            @RequestBody LocSchool locSchool){
        locSchoolService.updateLocSchoolForAll(locSchool);
    }

    @GetMapping("/deleteLocSchool/{id}")
    @ApiOperation(value = "通过id删除学校")
    public void deleteLocSchool(
            @ApiParam(value = "学校对象", required = true)
            @PathVariable String id){
        locSchoolService.deleteLocSchool(id);
    }
    @PostMapping("/deleteLocSchoolByCondition")
    @ApiOperation(value = "根据条件删除学校")
    public void deleteLocSchoolByCondition(
            @ApiParam(value = "学校对象")
            @RequestBody LocSchool locSchool){
        locSchoolService.deleteLocSchoolByCondition(locSchool);
    }
    @PostMapping("/findOneLocSchoolByCondition")
    @ApiOperation(value = "根据条件查找单个学校,结果必须为单条数据", notes = "返回单个学校,没有时为空")
    public LocSchool findOneLocSchoolByCondition(
            @ApiParam(value = "学校对象")
            @RequestBody LocSchool locSchool){
        return locSchoolService.findOneLocSchoolByCondition(locSchool);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/saveLocSchoolAndSaveLocSchoolYear")
    public void saveLocSchoolAndSaveLocSchoolYear(
            @RequestBody LocSchoolExt locSchoolExt
            ){
        locSchoolService.saveLocSchoolAndSaveLocSchoolYear(locSchoolExt);
    }
    @GetMapping("/deleteLocSchoolAndLocSchoolYear/{id}")
    public void deleteLocSchoolAndLocSchoolYear(@PathVariable("id")String id){
        locSchoolService.deleteLocSchoolAndLocSchoolYear(id);
    }

    @PostMapping("/findIpRepetitionCount")
    public long findIpRepetitionCount(@RequestBody LocSchool locSchool){
        return locSchoolService.findIpRepetitionCount(locSchool);
    }
}
