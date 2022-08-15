package com.yice.edu.cn.jy.controller.sensitiveWord;

import com.yice.edu.cn.common.pojo.jy.journal.Journal;
import com.yice.edu.cn.common.pojo.jy.sensitiveWord.SensitiveWord;
import com.yice.edu.cn.jy.service.sensitiveWord.SensitiveWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensitiveWord")
@Api(value = "/sensitiveWord",description = "模块")
public class SensitiveWordController {
    @Autowired
    private SensitiveWordService sensitiveWordService;

    @GetMapping("/findSensitiveWordById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public SensitiveWord findSensitiveWordById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return sensitiveWordService.findSensitiveWordById(id);
    }

    @PostMapping("/saveSensitiveWord")
    @ApiOperation(value = "保存", notes = "返回对象")
    public SensitiveWord saveSensitiveWord(
            @ApiParam(value = "对象", required = true)
            @RequestBody SensitiveWord sensitiveWord){
        sensitiveWordService.saveSensitiveWord(sensitiveWord);
        return sensitiveWord;
    }

    @PostMapping("/findSensitiveWordListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<SensitiveWord> findSensitiveWordListByCondition(
            @ApiParam(value = "对象")
            @RequestBody SensitiveWord sensitiveWord){
        return sensitiveWordService.findSensitiveWordListByCondition(sensitiveWord);
    }
    @PostMapping("/findSensitiveWordCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findSensitiveWordCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody SensitiveWord sensitiveWord){
        return sensitiveWordService.findSensitiveWordCountByCondition(sensitiveWord);
    }

    @PostMapping("/updateSensitiveWord")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateSensitiveWord(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody SensitiveWord sensitiveWord){
        sensitiveWordService.updateSensitiveWord(sensitiveWord);
    }

    @GetMapping("/deleteSensitiveWord/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteSensitiveWord(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        sensitiveWordService.deleteSensitiveWord(id);
    }
    @PostMapping("/deleteSensitiveWordByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteSensitiveWordByCondition(
            @ApiParam(value = "对象")
            @RequestBody SensitiveWord sensitiveWord){
        sensitiveWordService.deleteSensitiveWordByCondition(sensitiveWord);
    }
    @PostMapping("/findOneSensitiveWordByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public SensitiveWord findOneSensitiveWordByCondition(
            @ApiParam(value = "对象")
            @RequestBody SensitiveWord sensitiveWord){
        return sensitiveWordService.findOneSensitiveWordByCondition(sensitiveWord);
    }

    @PostMapping("/replaceSensitive")
    @ApiOperation(value = "根据敏感词列表把用户的日志的敏感词替换成**")
    public String replaceSensitive(@RequestParam("text") String text){
        return sensitiveWordService.replaceSensitive(text);
    }
    @PostMapping("/hasSensitiveWord")
    @ApiOperation(value = "判断字符串里是否有敏感词")
    public boolean hasSensitiveWord(@RequestBody Journal journal){
        return sensitiveWordService.hasSensitiveWord(journal.getText());
    }
}
