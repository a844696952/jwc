package com.yice.edu.cn.jw.controller.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveySubmit;
import com.yice.edu.cn.jw.service.qusSurvey.QusSurveySubmitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qusSurveySubmit")
@Api(value = "/qusSurveySubmit",description = "提交整个问卷记录的表模块")
public class QusSurveySubmitController {
    @Autowired
    private QusSurveySubmitService qusSurveySubmitService;

    @GetMapping("/findQusSurveySubmitById/{id}")
    @ApiOperation(value = "通过id查找提交整个问卷记录的表", notes = "返回提交整个问卷记录的表对象")
    public QusSurveySubmit findQusSurveySubmitById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return qusSurveySubmitService.findQusSurveySubmitById(id);
    }

    @PostMapping("/saveQusSurveySubmit")
    @ApiOperation(value = "保存提交整个问卷记录的表", notes = "返回提交整个问卷记录的表对象")
    public QusSurveySubmit saveQusSurveySubmit(
            @ApiParam(value = "提交整个问卷记录的表对象", required = true)
            @RequestBody QusSurveySubmit qusSurveySubmit){
        qusSurveySubmitService.saveQusSurveySubmit(qusSurveySubmit);
        return qusSurveySubmit;
    }

    @PostMapping("/findQusSurveySubmitListByCondition")
    @ApiOperation(value = "根据条件查找提交整个问卷记录的表列表", notes = "返回提交整个问卷记录的表列表")
    public List<QusSurveySubmit> findQusSurveySubmitListByCondition(
            @ApiParam(value = "提交整个问卷记录的表对象")
            @RequestBody QusSurveySubmit qusSurveySubmit){
        return qusSurveySubmitService.findQusSurveySubmitListByCondition(qusSurveySubmit);
    }
    @PostMapping("/findQusSurveySubmitCountByCondition")
    @ApiOperation(value = "根据条件查找提交整个问卷记录的表列表个数", notes = "返回提交整个问卷记录的表总个数")
    public long findQusSurveySubmitCountByCondition(
            @ApiParam(value = "提交整个问卷记录的表对象")
            @RequestBody QusSurveySubmit qusSurveySubmit){
        return qusSurveySubmitService.findQusSurveySubmitCountByCondition(qusSurveySubmit);
    }

    @PostMapping("/updateQusSurveySubmit")
    @ApiOperation(value = "修改提交整个问卷记录的表", notes = "提交整个问卷记录的表对象必传")
    public void updateQusSurveySubmit(
            @ApiParam(value = "提交整个问卷记录的表对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveySubmit qusSurveySubmit){
        qusSurveySubmitService.updateQusSurveySubmit(qusSurveySubmit);
    }

    @GetMapping("/deleteQusSurveySubmit/{id}")
    @ApiOperation(value = "通过id删除提交整个问卷记录的表")
    public void deleteQusSurveySubmit(
            @ApiParam(value = "提交整个问卷记录的表对象", required = true)
            @PathVariable String id){
        qusSurveySubmitService.deleteQusSurveySubmit(id);
    }
    @PostMapping("/deleteQusSurveySubmitByCondition")
    @ApiOperation(value = "根据条件删除提交整个问卷记录的表")
    public void deleteQusSurveySubmitByCondition(
            @ApiParam(value = "提交整个问卷记录的表对象")
            @RequestBody QusSurveySubmit qusSurveySubmit){
        qusSurveySubmitService.deleteQusSurveySubmitByCondition(qusSurveySubmit);
    }
    @PostMapping("/findOneQusSurveySubmitByCondition")
    @ApiOperation(value = "根据条件查找单个提交整个问卷记录的表,结果必须为单条数据", notes = "返回单个提交整个问卷记录的表,没有时为空")
    public QusSurveySubmit findOneQusSurveySubmitByCondition(
            @ApiParam(value = "提交整个问卷记录的表对象")
            @RequestBody QusSurveySubmit qusSurveySubmit){
        return qusSurveySubmitService.findOneQusSurveySubmitByCondition(qusSurveySubmit);
    }

    @PostMapping("/updateSubmit")
    @ApiOperation(value = "提交教师互评", notes = "返回响应对象")
    public void updateSubmit(
            @ApiParam(value = "被修改的提交整个问卷记录的表对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveySubmit qusSurveySubmit){
        qusSurveySubmitService.updateSubmit(qusSurveySubmit);
    }
}
