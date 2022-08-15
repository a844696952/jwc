package com.yice.edu.cn.jy.controller.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import com.yice.edu.cn.jy.service.prepLessonResource.LessonResFileMeterialItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessonResFileMeterialItem")
@Api(value = "/lessonResFileMeterialItem",description = "模块")
public class LessonResFileMeterialItemController {
    @Autowired
    private LessonResFileMeterialItemService lessonResFileMeterialItemService;

    @GetMapping("/findLessonResFileMeterialItemById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public LessonResFileMeterialItem findLessonResFileMeterialItemById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return lessonResFileMeterialItemService.findLessonResFileMeterialItemById(id);
    }

    @PostMapping("/saveLessonResFileMeterialItem")
    @ApiOperation(value = "保存", notes = "返回对象")
    public LessonResFileMeterialItem saveLessonResFileMeterialItem(
            @ApiParam(value = "对象", required = true)
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        lessonResFileMeterialItemService.saveLessonResFileMeterialItem(lessonResFileMeterialItem);
        return lessonResFileMeterialItem;
    }

    @PostMapping("/findLessonResFileMeterialItemListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<LessonResFileMeterialItem> findLessonResFileMeterialItemListByCondition(
            @ApiParam(value = "对象")
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        return lessonResFileMeterialItemService.findLessonResFileMeterialItemListByCondition(lessonResFileMeterialItem);
    }
    @PostMapping("/findLessonResFileMeterialItemCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findLessonResFileMeterialItemCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        return lessonResFileMeterialItemService.findLessonResFileMeterialItemCountByCondition(lessonResFileMeterialItem);
    }

    @PostMapping("/updateLessonResFileMeterialItem")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateLessonResFileMeterialItem(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        lessonResFileMeterialItemService.updateLessonResFileMeterialItem(lessonResFileMeterialItem);
    }

    @GetMapping("/delete/deleteLessonResFileMeterialItem/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteLessonResFileMeterialItem(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        lessonResFileMeterialItemService.deleteLessonResFileMeterialItem(id);
    }
    @PostMapping("/delete/deleteLessonResFileMeterialItemByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteLessonResFileMeterialItemByCondition(
            @ApiParam(value = "对象")
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        lessonResFileMeterialItemService.deleteLessonResFileMeterialItemByCondition(lessonResFileMeterialItem);
    }
    @PostMapping("/findOneLessonResFileMeterialItemByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public LessonResFileMeterialItem findOneLessonResFileMeterialItemByCondition(
            @ApiParam(value = "对象")
            @RequestBody LessonResFileMeterialItem lessonResFileMeterialItem){
        return lessonResFileMeterialItemService.findOneLessonResFileMeterialItemByCondition(lessonResFileMeterialItem);
    }
}
