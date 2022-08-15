package com.yice.edu.cn.jw.controller.qusBankResource;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourcesByDay;
import com.yice.edu.cn.jw.service.qusBankResource.SchoolQusBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolQusBank")
@Api(value = "/schoolQusBank",description = "模块")
public class SchoolQusBankController {
    @Autowired
    private SchoolQusBankService schoolQusBankService;

    @GetMapping("/findSchoolQusBankById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public SchoolQusBank findSchoolQusBankById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return schoolQusBankService.findSchoolQusBankById(id);
    }

    @PostMapping("/saveSchoolQusBank")
    @ApiOperation(value = "保存", notes = "返回对象")
    public SchoolQusBank saveSchoolQusBank(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolQusBank schoolQusBank){
        schoolQusBankService.saveSchoolQusBank(schoolQusBank);
        return schoolQusBank;
    }

    @PostMapping("/findSchoolQusBankListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<SchoolQusBank> findSchoolQusBankListByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolQusBank schoolQusBank){
        return schoolQusBankService.findSchoolQusBankListByCondition(schoolQusBank);
    }
    @PostMapping("/findSchoolQusBankCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findSchoolQusBankCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolQusBank schoolQusBank){
        return schoolQusBankService.findSchoolQusBankCountByCondition(schoolQusBank);
    }

    @PostMapping("/updateSchoolQusBank")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateSchoolQusBank(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolQusBank schoolQusBank){
        schoolQusBankService.updateSchoolQusBank(schoolQusBank);
    }

    @GetMapping("/deleteSchoolQusBank/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteSchoolQusBank(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        schoolQusBankService.deleteSchoolQusBank(id);
    }
    @PostMapping("/deleteSchoolQusBankByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteSchoolQusBankByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolQusBank schoolQusBank){
        schoolQusBankService.deleteSchoolQusBankByCondition(schoolQusBank);
    }
    @PostMapping("/findOneSchoolQusBankByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public SchoolQusBank findOneSchoolQusBankByCondition(
            @ApiParam(value = "对象")
            @RequestBody SchoolQusBank schoolQusBank){
        return schoolQusBankService.findOneSchoolQusBankByCondition(schoolQusBank);
    }

    @PostMapping("/findSchoolQusNumByCreateTimeZone")
    @ApiOperation(value = "根据时间区间查找区间内每日创建题目数量", notes = "返回单个,没有时为空")
    public List<JySchoolResourcesByDay> findSchoolQusNumByCreateTimeZone(
            @ApiParam(value = "对象")
            @RequestBody SchoolQusBank schoolQusBank){
        return schoolQusBankService.findSchoolQusNumByCreateTimeZone(schoolQusBank);
    }

    @PostMapping("/copyTopicToSchoolQusBank")
    @ApiOperation(value = "将平台题库的题目添加到校本题库")
    public void copyTopicToSchoolQusBank(
            @ApiParam(value = "对象")
            @RequestBody SchoolQusBank schoolQusBank){
        schoolQusBankService.copyTopicToSchoolQusBank(schoolQusBank);
    }
}
