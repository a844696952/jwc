package com.yice.edu.cn.jy.controller.questionItem;

import com.yice.edu.cn.common.pojo.jy.questionItem.QuestionItem;
import com.yice.edu.cn.jy.service.questionItem.QuestionItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionItem")
@Api(value = "/questionItem",description = "题目类型模块")
public class QuestionItemController {
    @Autowired
    private QuestionItemService questionItemService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findQuestionItemById/{id}")
    @ApiOperation(value = "通过id查找题目类型", notes = "返回题目类型对象")
    public QuestionItem findQuestionItemById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return questionItemService.findQuestionItemById(id);
    }

    @PostMapping("/saveQuestionItem")
    @ApiOperation(value = "保存题目类型", notes = "返回题目类型对象")
    public QuestionItem saveQuestionItem(
            @ApiParam(value = "题目类型对象", required = true)
            @RequestBody QuestionItem questionItem){
        questionItemService.saveQuestionItem(questionItem);
        return questionItem;
    }

    @PostMapping("/batchSaveQuestionItem")
    @ApiOperation(value = "批量保存题目类型")
    public void batchSaveQuestionItem(
            @ApiParam(value = "题目类型对象集合", required = true)
            @RequestBody List<QuestionItem> questionItems){
        questionItemService.batchSaveQuestionItem(questionItems);
    }

    @PostMapping("/findQuestionItemListByCondition")
    @ApiOperation(value = "根据条件查找题目类型列表", notes = "返回题目类型列表")
    public List<QuestionItem> findQuestionItemListByCondition(
            @ApiParam(value = "题目类型对象")
            @RequestBody QuestionItem questionItem){
        return questionItemService.findQuestionItemListByCondition(questionItem);
    }
    @PostMapping("/findQuestionItemCountByCondition")
    @ApiOperation(value = "根据条件查找题目类型列表个数", notes = "返回题目类型总个数")
    public long findQuestionItemCountByCondition(
            @ApiParam(value = "题目类型对象")
            @RequestBody QuestionItem questionItem){
        return questionItemService.findQuestionItemCountByCondition(questionItem);
    }

    @PostMapping("/updateQuestionItem")
    @ApiOperation(value = "修改题目类型有值的字段", notes = "题目类型对象必传")
    public void updateQuestionItem(
            @ApiParam(value = "题目类型对象,对象属性不为空则修改", required = true)
            @RequestBody QuestionItem questionItem){
        questionItemService.updateQuestionItem(questionItem);
    }
    @PostMapping("/updateQuestionItemForAll")
    @ApiOperation(value = "修改题目类型所有字段", notes = "题目类型对象必传")
    public void updateQuestionItemForAll(
            @ApiParam(value = "题目类型对象", required = true)
            @RequestBody QuestionItem questionItem){
        questionItemService.updateQuestionItemForAll(questionItem);
    }

    @GetMapping("/deleteQuestionItem/{id}")
    @ApiOperation(value = "通过id删除题目类型")
    public void deleteQuestionItem(
            @ApiParam(value = "题目类型对象", required = true)
            @PathVariable String id){
        questionItemService.deleteQuestionItem(id);
    }
    @PostMapping("/deleteQuestionItemByCondition")
    @ApiOperation(value = "根据条件删除题目类型")
    public void deleteQuestionItemByCondition(
            @ApiParam(value = "题目类型对象")
            @RequestBody QuestionItem questionItem){
        questionItemService.deleteQuestionItemByCondition(questionItem);
    }
    @PostMapping("/findOneQuestionItemByCondition")
    @ApiOperation(value = "根据条件查找单个题目类型,结果必须为单条数据", notes = "返回单个题目类型,没有时为空")
    public QuestionItem findOneQuestionItemByCondition(
            @ApiParam(value = "题目类型对象")
            @RequestBody QuestionItem questionItem){
        return questionItemService.findOneQuestionItemByCondition(questionItem);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
