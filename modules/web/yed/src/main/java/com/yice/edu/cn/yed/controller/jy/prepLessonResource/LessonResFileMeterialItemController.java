package com.yice.edu.cn.yed.controller.jy.prepLessonResource;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import com.yice.edu.cn.yed.service.jy.prepLessonResource.LessonResFileMeterialItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/lessonResFileMeterialItem")
@Api(value = "/lessonResFileMeterialItem",description = "模块")
public class LessonResFileMeterialItemController {
    @Autowired
    private LessonResFileMeterialItemService lessonResFileMeterialItemService;

    @PostMapping("/saveLessonResFileMeterialItem")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveLessonResFileMeterialItem(
            @ApiParam(value = "对象", required = true)
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        LessonResFileMeterialItem s=lessonResFileMeterialItemService.saveLessonResFileMeterialItem(lessonResFileMeterialItem);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findLessonResFileMeterialItemById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findLessonResFileMeterialItemById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        LessonResFileMeterialItem lessonResFileMeterialItem=lessonResFileMeterialItemService.findLessonResFileMeterialItemById(id);
        return new ResponseJson(lessonResFileMeterialItem);
    }

    @PostMapping("/update/updateLessonResFileMeterialItem")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateLessonResFileMeterialItem(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        lessonResFileMeterialItemService.updateLessonResFileMeterialItem(lessonResFileMeterialItem);
        return new ResponseJson();
    }

    @GetMapping("/look/lookLessonResFileMeterialItemById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookLessonResFileMeterialItemById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        LessonResFileMeterialItem lessonResFileMeterialItem=lessonResFileMeterialItemService.findLessonResFileMeterialItemById(id);
        return new ResponseJson(lessonResFileMeterialItem);
    }

    @PostMapping("/findLessonResFileMeterialItemsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findLessonResFileMeterialItemsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        List<LessonResFileMeterialItem> data=lessonResFileMeterialItemService.findLessonResFileMeterialItemListByCondition(lessonResFileMeterialItem);
        long count=lessonResFileMeterialItemService.findLessonResFileMeterialItemCountByCondition(lessonResFileMeterialItem);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneLessonResFileMeterialItemByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneLessonResFileMeterialItemByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        LessonResFileMeterialItem one=lessonResFileMeterialItemService.findOneLessonResFileMeterialItemByCondition(lessonResFileMeterialItem);
        return new ResponseJson(one);
    }
    @GetMapping("/delete/deleteLessonResFileMeterialItem/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteLessonResFileMeterialItem(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        lessonResFileMeterialItemService.deleteLessonResFileMeterialItem(id);
        return new ResponseJson();
    }


    @PostMapping("/findLessonResFileMeterialItemListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findLessonResFileMeterialItemListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        List<LessonResFileMeterialItem> data=lessonResFileMeterialItemService.findLessonResFileMeterialItemListByCondition(lessonResFileMeterialItem);
        return new ResponseJson(data);
    }

    @PostMapping("/delete/deleteLessonResFileMeterialItemByCondition2")
    @ApiOperation(value = "根据条件删除,若被其他章节关联，则做更新操作")
    public void deleteLessonResFileMeterialItemByCondition2(
            @ApiParam(value = "对象")
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
      lessonResFileMeterialItemService.deleteLessonResFileMeterialItemByCondition2(lessonResFileMeterialItem);
    }
}
