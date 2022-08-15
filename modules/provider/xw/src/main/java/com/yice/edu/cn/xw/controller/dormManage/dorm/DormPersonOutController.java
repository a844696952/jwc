package com.yice.edu.cn.xw.controller.dormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonOut;
import com.yice.edu.cn.xw.service.dormManage.dorm.DormPersonOutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dormPersonOut")
@Api(value = "/dormPersonOut",description = "模块")
public class DormPersonOutController {
    @Autowired
    private DormPersonOutService dormPersonOutService;

    @GetMapping("/findDormPersonOutById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public DormPersonOut findDormPersonOutById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return dormPersonOutService.findDormPersonOutById(id);
    }

    @PostMapping("/saveDormPersonOut")
    @ApiOperation(value = "保存", notes = "返回对象")
    public DormPersonOut saveDormPersonOut(
            @ApiParam(value = "对象", required = true)
            @RequestBody DormPersonOut dormPersonOut){
        dormPersonOutService.saveDormPersonOut(dormPersonOut);
        return dormPersonOut;
    }

    @PostMapping("/findDormPersonOutListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<DormPersonOut> findDormPersonOutListByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPersonOut dormPersonOut){
        return dormPersonOutService.findDormPersonOutListByCondition(dormPersonOut);
    }
    @PostMapping("/findDormPersonOutCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findDormPersonOutCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPersonOut dormPersonOut){
        return dormPersonOutService.findDormPersonOutCountByCondition(dormPersonOut);
    }

    @PostMapping("/updateDormPersonOut")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateDormPersonOut(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody DormPersonOut dormPersonOut){
        dormPersonOutService.updateDormPersonOut(dormPersonOut);
    }

    @GetMapping("/deleteDormPersonOut/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteDormPersonOut(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        dormPersonOutService.deleteDormPersonOut(id);
    }
    @PostMapping("/deleteDormPersonOutByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteDormPersonOutByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPersonOut dormPersonOut){
        dormPersonOutService.deleteDormPersonOutByCondition(dormPersonOut);
    }
    @PostMapping("/findOneDormPersonOutByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public DormPersonOut findOneDormPersonOutByCondition(
            @ApiParam(value = "对象")
            @RequestBody DormPersonOut dormPersonOut){
        return dormPersonOutService.findOneDormPersonOutByCondition(dormPersonOut);
    }


    /*------------------------------------------------------------------------------------------*/
    @PostMapping("/findDormPersonOutListByConditionAndPersonType")
    public List<DormPersonOut> findDormPersonOutListByConditionWithStudent(
            @ApiParam(value = "对象")
            @RequestBody DormPersonOut dormPersonOut){
        return dormPersonOutService.findDormPersonOutListByConditionAndPersonType(dormPersonOut);
    }
    @PostMapping("/findDormPersonOutCountByConditionAndPersonType")
    public long findDormPersonOutCountByConditionWithStudent(
            @ApiParam(value = "对象")
            @RequestBody DormPersonOut dormPersonOut){
        return dormPersonOutService.findDormPersonOutCountByConditionAndPersonType(dormPersonOut);
    }

    @GetMapping("/deleteDormPersonOutForStudentByTime")
    public void deleteDormPersonOutForStudentByTime(){
        dormPersonOutService.deleteDormPersonOutForStudentByTime();
    }

    @GetMapping("/deleteDormPersonLogForStudentByTime")
    public void deleteDormPersonLogForStudentByTime(){
        dormPersonOutService.deleteDormPersonLogForStudentByTime();
    }
}
