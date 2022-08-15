package com.yice.edu.cn.osp.controller.dm.modeManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamTask;
import com.yice.edu.cn.osp.service.dm.modeManage.AttendClassModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/attendClassMode")
@Api(value = "/attendClassMode",description = "上课模式表模块")
public class AttendClassModeController {

    @Autowired
    private AttendClassModeService attendClassModeService;

    @GetMapping("/openAttendClassMode")
    @ApiOperation(value = "打开班牌上课班级模式", notes = "打开班牌上课班级模式")
    public ResponseJson openAttendClassMode(){
       try{
           List<String> equipmentNameList = attendClassModeService.openAttendClassMode(mySchoolId());
           return new ResponseJson(equipmentNameList,true);
       }catch (Exception e){
           e.printStackTrace();
           return new ResponseJson();
       }
    }

    @GetMapping("/closeAttendClassMode")
    @ApiOperation(value = "关闭班牌上课班级模式", notes = "关闭班牌上课班级模式")
    public ResponseJson closeAttendClassMode(){
        List<String> equipmentNameList = attendClassModeService.closeAttendClassMode(mySchoolId());
        return new ResponseJson(equipmentNameList,false);
    }

    @GetMapping("/findClassModeTask")
    @ApiOperation(value = "查询上课模式的状态", notes = "查询上课模式的状态")
    public ResponseJson findClassModeTask(){
        ExamTask examTask = attendClassModeService.findClassModeTask(mySchoolId());
        if(Objects.nonNull(examTask)){
            return new ResponseJson(examTask);
        }
        ExamTask resExamTask = new ExamTask();
        resExamTask.setStatus(2);
        return new ResponseJson(resExamTask);
    }


}
