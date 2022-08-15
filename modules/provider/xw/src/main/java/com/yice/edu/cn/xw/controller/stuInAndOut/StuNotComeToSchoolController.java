package com.yice.edu.cn.xw.controller.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuNotComeToSchool;
import com.yice.edu.cn.xw.service.stuInAndOut.StuInAndOutStartTimeService;
import com.yice.edu.cn.xw.service.stuInAndOut.StuNotComeToSchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stuNotComeToSchool")
@Api(value = "/stuNotComeToSchool",description = "模块")
public class StuNotComeToSchoolController {
    @Autowired
    private StuNotComeToSchoolService stuNotComeToSchoolService;
    @Autowired
    private StuInAndOutStartTimeService stuInAndOutStartTimeService;
    @GetMapping("/findStuNotComeToSchoolById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public StuNotComeToSchool findStuNotComeToSchoolById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return stuNotComeToSchoolService.findStuNotComeToSchoolById(id);
    }

    @PostMapping("/saveStuNotComeToSchool")
    @ApiOperation(value = "保存", notes = "返回对象")
    public StuNotComeToSchool saveStuNotComeToSchool(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
        stuNotComeToSchoolService.saveStuNotComeToSchool(stuNotComeToSchool);
        return stuNotComeToSchool;
    }

    @PostMapping("/findStuNotComeToSchoolListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuNotComeToSchool> findStuNotComeToSchoolListByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
        return stuNotComeToSchoolService.findStuNotComeToSchoolListByCondition(stuNotComeToSchool);
    }
    @PostMapping("/findStuNotComeToSchoolCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStuNotComeToSchoolCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
        return stuNotComeToSchoolService.findStuNotComeToSchoolCountByCondition(stuNotComeToSchool);
    }

    @PostMapping("/updateStuNotComeToSchool")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateStuNotComeToSchool(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
        stuNotComeToSchoolService.updateStuNotComeToSchool(stuNotComeToSchool);
    }

    @GetMapping("/deleteStuNotComeToSchool/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteStuNotComeToSchool(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        stuNotComeToSchoolService.deleteStuNotComeToSchool(id);
    }
    @PostMapping("/deleteStuNotComeToSchoolByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteStuNotComeToSchoolByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
        stuNotComeToSchoolService.deleteStuNotComeToSchoolByCondition(stuNotComeToSchool);
    }
    @PostMapping("/findOneStuNotComeToSchoolByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public StuNotComeToSchool findOneStuNotComeToSchoolByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuNotComeToSchool stuNotComeToSchool){
        return stuNotComeToSchoolService.findOneStuNotComeToSchoolByCondition(stuNotComeToSchool);
    }

    @GetMapping("/makeNotComeData/{schoolId}")
    @ApiOperation(value = "学校每日统计学生未到校记录", notes = "返回单个,没有时为空")
    public void makeNotComeData( @ApiParam(value = "对象", required = true)
                                        @PathVariable String schoolId){
        System.out.println("学校主动生成每日统计学生未到校记录");
        stuInAndOutStartTimeService.stuNotArriveSchool(schoolId);
    }


}
