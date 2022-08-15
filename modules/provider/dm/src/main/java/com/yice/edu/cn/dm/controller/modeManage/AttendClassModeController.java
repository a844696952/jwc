package com.yice.edu.cn.dm.controller.modeManage;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamTask;
import com.yice.edu.cn.dm.service.modeManage.AttendClassModeService;
import com.yice.edu.cn.dm.service.modeManage.ModeTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attendClassMode")
@Api(value = "/attendClassMode",description = "上课模式模块")
public class AttendClassModeController {

    @Autowired
    private AttendClassModeService attendClassModeService;

    @Autowired
    private ModeTaskService modeTaskService;

    @GetMapping("/openAttendClassMode/{id}")
    @ApiOperation(value = "打开班牌上课班级模式", notes = "打开班牌上课班级模式")
    public List<String> openAttendClassMode(
            @ApiParam(value = "学校id", required = true)
            @PathVariable("id") String id){
        return attendClassModeService.openAttendClassMode(id);
    }

    @GetMapping("/closeAttendClassMode/{id}")
    @ApiOperation(value = "关闭班牌上课班级模式", notes = "关闭班牌上课班级模式")
    public List<String> closeAttendClassMode(
            @ApiParam(value = "学校id", required = true)
            @PathVariable("id") String id){
        return attendClassModeService.closeAttendClassMode(id);
    }

    @GetMapping("/findClassModeTask/{id}")
    @ApiOperation(value = "查询上课模式的状态", notes = "查询上课模式的状态")
    public ExamTask findClassModeTask(
            @ApiParam(value = "学校id", required = true)
            @PathVariable String id
    ){
        List<ExamTask> taskList = modeTaskService.findClassModeTask(id);
        if(CollUtil.isNotEmpty(taskList)){
            return taskList.get(0);
        }
        return null;
    }


}
