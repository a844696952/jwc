package com.yice.edu.cn.jw.controller.exam.buildPaper.answerSheet;

import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet.AnswerSheet;
import com.yice.edu.cn.jw.service.exam.buildPaper.answerSheet.AnswerSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answerSheet")
@Api(value = "/answerSheet",description = "答题卡模块")
public class AnswerSheetController {
    @Autowired
    private AnswerSheetService answerSheetService;

    @GetMapping("/findAnswerSheetById/{id}")
    @ApiOperation(value = "通过id查找答题卡", notes = "返回答题卡对象")
    public AnswerSheet findAnswerSheetById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return answerSheetService.findAnswerSheetById(id);
    }

    @PostMapping("/saveAnswerSheet")
    @ApiOperation(value = "保存答题卡", notes = "返回答题卡对象")
    public AnswerSheet saveAnswerSheet(
            @ApiParam(value = "答题卡对象", required = true)
            @RequestBody AnswerSheet answerSheet){
        answerSheetService.saveAnswerSheet(answerSheet);
        return answerSheet;
    }

    @PostMapping("/findAnswerSheetListByCondition")
    @ApiOperation(value = "根据条件查找答题卡列表", notes = "返回答题卡列表")
    public List<AnswerSheet> findAnswerSheetListByCondition(
            @ApiParam(value = "答题卡对象")
            @RequestBody AnswerSheet answerSheet){
        return answerSheetService.findAnswerSheetListByCondition(answerSheet);
    }
    @PostMapping("/findAnswerSheetCountByCondition")
    @ApiOperation(value = "根据条件查找答题卡列表个数", notes = "返回答题卡总个数")
    public long findAnswerSheetCountByCondition(
            @ApiParam(value = "答题卡对象")
            @RequestBody AnswerSheet answerSheet){
        return answerSheetService.findAnswerSheetCountByCondition(answerSheet);
    }

    @PostMapping("/updateAnswerSheet")
    @ApiOperation(value = "修改答题卡", notes = "答题卡对象必传")
    public void updateAnswerSheet(
            @ApiParam(value = "答题卡对象,对象属性不为空则修改", required = true)
            @RequestBody AnswerSheet answerSheet){
        answerSheetService.updateAnswerSheet(answerSheet);
    }

    @GetMapping("/deleteAnswerSheet/{id}")
    @ApiOperation(value = "通过id删除答题卡")
    public void deleteAnswerSheet(
            @ApiParam(value = "答题卡对象", required = true)
            @PathVariable String id){
        answerSheetService.deleteAnswerSheet(id);
    }
    @PostMapping("/deleteAnswerSheetByCondition")
    @ApiOperation(value = "根据条件删除答题卡")
    public void deleteAnswerSheetByCondition(
            @ApiParam(value = "答题卡对象")
            @RequestBody AnswerSheet answerSheet){
        answerSheetService.deleteAnswerSheetByCondition(answerSheet);
    }
    @PostMapping("/findOneAnswerSheetByCondition")
    @ApiOperation(value = "根据条件查找单个答题卡,结果必须为单条数据", notes = "返回单个答题卡,没有时为空")
    public AnswerSheet findOneAnswerSheetByCondition(
            @ApiParam(value = "答题卡对象")
            @RequestBody AnswerSheet answerSheet){
        return answerSheetService.findOneAnswerSheetByCondition(answerSheet);
    }
}
