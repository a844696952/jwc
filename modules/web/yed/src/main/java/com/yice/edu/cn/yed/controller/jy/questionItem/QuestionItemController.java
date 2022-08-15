package com.yice.edu.cn.yed.controller.jy.questionItem;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import com.yice.edu.cn.yed.service.jy.questionItem.QuestionItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/questionItem")
@Api(value = "/questionItem",description = "题目类型模块")
public class QuestionItemController {
    @Autowired
    private QuestionItemService questionItemService;

    @PostMapping("/saveQuestionItem")
    @ApiOperation(value = "保存题目类型对象", notes = "返回保存好的题目类型对象", response= QuestionItem.class)
    public ResponseJson saveQuestionItem(
            @ApiParam(value = "题目类型对象", required = true)
            @RequestBody QuestionItem questionItem){
        QuestionItem s=questionItemService.saveQuestionItem(questionItem);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findQuestionItemById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找题目类型", notes = "返回响应对象", response=QuestionItem.class)
    public ResponseJson findQuestionItemById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        QuestionItem questionItem=questionItemService.findQuestionItemById(id);
        return new ResponseJson(questionItem);
    }

    @PostMapping("/update/updateQuestionItem")
    @ApiOperation(value = "修改题目类型对象非空字段", notes = "返回响应对象")
    public ResponseJson updateQuestionItem(
            @ApiParam(value = "被修改的题目类型对象,对象属性不为空则修改", required = true)
            @RequestBody QuestionItem questionItem){
        questionItemService.updateQuestionItem(questionItem);
        return new ResponseJson();
    }

    @PostMapping("/update/updateQuestionItemForAll")
    @ApiOperation(value = "修改题目类型对象所有字段", notes = "返回响应对象")
    public ResponseJson updateQuestionItemForAll(
            @ApiParam(value = "被修改的题目类型对象", required = true)
            @RequestBody QuestionItem questionItem){
        questionItemService.updateQuestionItemForAll(questionItem);
        return new ResponseJson();
    }

    @GetMapping("/look/lookQuestionItemById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找题目类型", notes = "返回响应对象", response=QuestionItem.class)
    public ResponseJson lookQuestionItemById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        QuestionItem questionItem=questionItemService.findQuestionItemById(id);
        return new ResponseJson(questionItem);
    }

    @PostMapping("/findQuestionItemsByCondition")
    @ApiOperation(value = "根据条件查找题目类型", notes = "返回响应对象", response=QuestionItem.class)
    public ResponseJson findQuestionItemsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QuestionItem questionItem){
        List<QuestionItem> data=questionItemService.findQuestionItemListByCondition(questionItem);
        long count=questionItemService.findQuestionItemCountByCondition(questionItem);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneQuestionItemByCondition")
    @ApiOperation(value = "根据条件查找单个题目类型,结果必须为单条数据", notes = "没有时返回空", response=QuestionItem.class)
    public ResponseJson findOneQuestionItemByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody QuestionItem questionItem){
        QuestionItem one=questionItemService.findOneQuestionItemByCondition(questionItem);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteQuestionItem/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteQuestionItem(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        questionItemService.deleteQuestionItem(id);
        return new ResponseJson();
    }


    @PostMapping("/findQuestionItemListByCondition")
    @ApiOperation(value = "根据条件查找题目类型列表", notes = "返回响应对象,不包含总条数", response=QuestionItem.class)
    public ResponseJson findQuestionItemListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody QuestionItem questionItem){
        List<QuestionItem> data=questionItemService.findQuestionItemListByCondition(questionItem);
        return new ResponseJson(data);
    }
    @GetMapping("/synchronizeType")
    @ApiOperation(value = "同步21的题目题型")
    public void synchronizeType(){
        questionItemService.synchronizeType();
    }


}
