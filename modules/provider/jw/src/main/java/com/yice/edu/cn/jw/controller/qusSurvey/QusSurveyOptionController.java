package com.yice.edu.cn.jw.controller.qusSurvey;

import com.yice.edu.cn.common.pojo.jw.qusSurvey.QusSurveyOption;
import com.yice.edu.cn.jw.service.qusSurvey.QusSurveyOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qusSurveyOption")
@Api(value = "/qusSurveyOption",description = "学生提交选项保存的记录的表模块")
public class QusSurveyOptionController {
    @Autowired
    private QusSurveyOptionService qusSurveyOptionService;

    @GetMapping("/findQusSurveyOptionById/{id}")
    @ApiOperation(value = "通过id查找学生提交选项保存的记录的表", notes = "返回学生提交选项保存的记录的表对象")
    public QusSurveyOption findQusSurveyOptionById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return qusSurveyOptionService.findQusSurveyOptionById(id);
    }

    @PostMapping("/saveQusSurveyOption")
    @ApiOperation(value = "保存学生提交选项保存的记录的表", notes = "返回学生提交选项保存的记录的表对象")
    public QusSurveyOption saveQusSurveyOption(
            @ApiParam(value = "学生提交选项保存的记录的表对象", required = true)
            @RequestBody QusSurveyOption qusSurveyOption){
        qusSurveyOptionService.saveQusSurveyOption(qusSurveyOption);
        return qusSurveyOption;
    }

    @PostMapping("/findQusSurveyOptionListByCondition")
    @ApiOperation(value = "根据条件查找学生提交选项保存的记录的表列表", notes = "返回学生提交选项保存的记录的表列表")
    public List<QusSurveyOption> findQusSurveyOptionListByCondition(
            @ApiParam(value = "学生提交选项保存的记录的表对象")
            @RequestBody QusSurveyOption qusSurveyOption){
        return qusSurveyOptionService.findQusSurveyOptionListByCondition(qusSurveyOption);
    }
    @PostMapping("/findQusSurveyOptionCountByCondition")
    @ApiOperation(value = "根据条件查找学生提交选项保存的记录的表列表个数", notes = "返回学生提交选项保存的记录的表总个数")
    public long findQusSurveyOptionCountByCondition(
            @ApiParam(value = "学生提交选项保存的记录的表对象")
            @RequestBody QusSurveyOption qusSurveyOption){
        return qusSurveyOptionService.findQusSurveyOptionCountByCondition(qusSurveyOption);
    }

    @PostMapping("/updateQusSurveyOption")
    @ApiOperation(value = "修改学生提交选项保存的记录的表", notes = "学生提交选项保存的记录的表对象必传")
    public void updateQusSurveyOption(
            @ApiParam(value = "学生提交选项保存的记录的表对象,对象属性不为空则修改", required = true)
            @RequestBody QusSurveyOption qusSurveyOption){
        qusSurveyOptionService.updateQusSurveyOption(qusSurveyOption);
    }

    @GetMapping("/deleteQusSurveyOption/{id}")
    @ApiOperation(value = "通过id删除学生提交选项保存的记录的表")
    public void deleteQusSurveyOption(
            @ApiParam(value = "学生提交选项保存的记录的表对象", required = true)
            @PathVariable String id){
        qusSurveyOptionService.deleteQusSurveyOption(id);
    }
    @PostMapping("/deleteQusSurveyOptionByCondition")
    @ApiOperation(value = "根据条件删除学生提交选项保存的记录的表")
    public void deleteQusSurveyOptionByCondition(
            @ApiParam(value = "学生提交选项保存的记录的表对象")
            @RequestBody QusSurveyOption qusSurveyOption){
        qusSurveyOptionService.deleteQusSurveyOptionByCondition(qusSurveyOption);
    }
    @PostMapping("/findOneQusSurveyOptionByCondition")
    @ApiOperation(value = "根据条件查找单个学生提交选项保存的记录的表,结果必须为单条数据", notes = "返回单个学生提交选项保存的记录的表,没有时为空")
    public QusSurveyOption findOneQusSurveyOptionByCondition(
            @ApiParam(value = "学生提交选项保存的记录的表对象")
            @RequestBody QusSurveyOption qusSurveyOption){
        return qusSurveyOptionService.findOneQusSurveyOptionByCondition(qusSurveyOption);
    }

    @PostMapping("/getOptionCountList")
    @ApiOperation(value = " 获取选项次数统计的结果集", notes = "返回每道题的选项选择次数")
    public List<QusSurveyOption> getOptionCountList(
            @ApiParam(value = "问卷id、老师id")
            @RequestBody QusSurveyOption qusSurveyOption){
        return qusSurveyOptionService.getOptionCountList(qusSurveyOption);
    }
}
