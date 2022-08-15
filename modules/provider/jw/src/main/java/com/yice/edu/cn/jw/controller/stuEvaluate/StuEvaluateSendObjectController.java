package com.yice.edu.cn.jw.controller.stuEvaluate;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateSendObject;
import com.yice.edu.cn.jw.service.stuEvaluate.StuEvaluateSendObjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/stuEvaluateSendObject")
@Api(value = "/stuEvaluateSendObject",description = "模块")
public class StuEvaluateSendObjectController {
    @Autowired
    private StuEvaluateSendObjectService stuEvaluateSendObjectService;

    @GetMapping("/findStuEvaluateSendObjectById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public StuEvaluateSendObject findStuEvaluateSendObjectById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return stuEvaluateSendObjectService.findStuEvaluateSendObjectById(id);
    }

    @PostMapping("/saveStuEvaluateSendObject")
    @ApiOperation(value = "保存", notes = "返回对象")
    public StuEvaluateSendObject saveStuEvaluateSendObject(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        stuEvaluateSendObjectService.saveStuEvaluateSendObject(stuEvaluateSendObject);
        return stuEvaluateSendObject;
    }

    @PostMapping("/findStuEvaluateSendObjectListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StuEvaluateSendObject> findStuEvaluateSendObjectListByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        List<StuEvaluateSendObject> list= stuEvaluateSendObjectService.findStuEvaluateSendObjectListByCondition(stuEvaluateSendObject);
        list.forEach(stuEvaluateSendObject1 -> {
            Date endTime1= DateUtil.parse(stuEvaluateSendObject1.getStuEvaluate().getEndTime());
            Date nowDate = DateUtil.date();
            if(endTime1.getTime()> nowDate.getTime()){
                stuEvaluateSendObject1.getStuEvaluate().setState("1");
            }else {
                stuEvaluateSendObject1.getStuEvaluate().setState("2");
            }
        });
        return list;
    }
    @PostMapping("/findStuEvaluateSendObjectCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStuEvaluateSendObjectCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        return stuEvaluateSendObjectService.findStuEvaluateSendObjectCountByCondition(stuEvaluateSendObject);
    }

    @PostMapping("/updateStuEvaluateSendObject")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateStuEvaluateSendObject(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        stuEvaluateSendObjectService.updateStuEvaluateSendObject(stuEvaluateSendObject);
    }

    @GetMapping("/deleteStuEvaluateSendObject/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteStuEvaluateSendObject(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        stuEvaluateSendObjectService.deleteStuEvaluateSendObject(id);
    }
    @PostMapping("/deleteStuEvaluateSendObjectByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteStuEvaluateSendObjectByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        stuEvaluateSendObjectService.deleteStuEvaluateSendObjectByCondition(stuEvaluateSendObject);
    }
    @PostMapping("/findOneStuEvaluateSendObjectByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public StuEvaluateSendObject findOneStuEvaluateSendObjectByCondition(
            @ApiParam(value = "对象")
            @RequestBody StuEvaluateSendObject stuEvaluateSendObject){
        return stuEvaluateSendObjectService.findOneStuEvaluateSendObjectByCondition(stuEvaluateSendObject);
    }
}
