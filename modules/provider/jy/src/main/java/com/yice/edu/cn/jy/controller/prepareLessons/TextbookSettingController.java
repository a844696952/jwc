package com.yice.edu.cn.jy.controller.prepareLessons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.prepareLessons.TextbookSetting;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.jy.service.prepareLessons.TextbookSettingService;


@RestController
@RequestMapping("/textbookSetting")
@Api(value = "/textbookSetting",description = "模块")
public class TextbookSettingController {
    @Autowired
    private TextbookSettingService textbookSettingService;

    @GetMapping("/findTextbookSettingById/{id}")
    @ApiOperation(value = "通过教师id查找", notes = "返回对象")
    public List<TextbookSetting> findTextbookSettingByTeacherId(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return textbookSettingService.findTextbookSettingByTeacherId(id);
    }

    @PostMapping("/saveTextbookSetting")
    @ApiOperation(value = "保存", notes = "返回对象")
    public ResponseJson saveTextbookSetting(
            @ApiParam(value = "对象", required = true)
            @RequestBody @Validated(value = GroupOne.class) TextbookSetting textbookSetting){
    	
        int successRow = textbookSettingService.saveTextbookSetting(textbookSetting);
        return new ResponseJson(successRow==1, successRow==1?"保存成功":"保存失败");
    }
    
    
    @GetMapping("/deleteTextbookSetting/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteTextbookSetting(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        textbookSettingService.deleteTextbookSetting(id);
    }
    
    @GetMapping("/findLastSettingbyTeacherId/{teacherId}")
    @ApiOperation(value = "通过老师id获取最近设置的教材")
    public TextbookSetting findLastSettingbyTeacherId(
            @ApiParam(value = "对象", required = true)
            @PathVariable("teacherId") String teacherId){
        return textbookSettingService.findLastSetting(teacherId);
    }
    
    
}
