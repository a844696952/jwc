package com.yice.edu.cn.jw.controller.practice;

import com.yice.edu.cn.common.pojo.jw.practice.Practice;
import com.yice.edu.cn.jw.service.practice.PracticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/practice")
@Api(value = "/practice", description = "模块")
public class PracticeController {
    @Autowired
    private PracticeService practiceService;

    @GetMapping("/findPracticeById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Practice findPracticeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return practiceService.findPracticeById(id);
    }

    @PostMapping("/savePractice")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Practice savePractice(
            @ApiParam(value = "对象", required = true)
            @RequestBody Practice practice) {
        practiceService.savePractice(practice);
        return practice;
    }

    @PostMapping("/findPracticeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Practice> findPracticeListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Practice practice) {
        return practiceService.findPracticeListByCondition(practice);
    }

    @PostMapping("/findPracticeListByCondition1")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Practice> findPracticeListByCondition1(
            @ApiParam(value = "对象")
            @RequestBody Practice practice) {
        return practiceService.findPracticeListByCondition1(practice);
    }

    @PostMapping("/findPracticeListByTeacherId")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Practice> findPracticeListByTeacherId(
            @ApiParam(value = "对象") @RequestBody Practice practice) {
        return practiceService.findPracticeListByTeacherId(practice);
    }

    @PostMapping("/findPracticeCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPracticeCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Practice practice) {
        return practiceService.findPracticeCountByCondition(practice);
    }
    @PostMapping("/findPracticeCountByCondition1")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPracticeCountByCondition1(
            @ApiParam(value = "对象")
            @RequestBody Practice practice) {
        return practiceService.findPracticeCountByCondition1(practice);
    }


    @PostMapping("/findPracticeCountByTeacherId")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findPracticeCountByTeacherId(
            @ApiParam(value = "对象")
            @RequestBody Practice practice) {
        return practiceService.findPracticeCountByTeacherId(practice);
    }


    @PostMapping("/updatePractice")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updatePractice(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Practice practice) {
        practiceService.updatePractice(practice);
    }

    @GetMapping("/deletePractice/{id}")
    @ApiOperation(value = "通过id删除")
    public void deletePractice(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        practiceService.deletePractice(id);
    }

    @PostMapping("/deletePracticeByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deletePracticeByCondition(
            @ApiParam(value = "对象")
            @RequestBody Practice practice) {
        practiceService.deletePracticeByCondition(practice);
    }

    @PostMapping("/findOnePracticeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Practice findOnePracticeByCondition(
            @ApiParam(value = "对象")
            @RequestBody Practice practice) {
        return practiceService.findOnePracticeByCondition(practice);
    }
}
