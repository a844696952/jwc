package com.yice.edu.cn.ewb.controller.classSchedule;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.ewb.service.classSchedule.ClassScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classSchedule")
@Api(value = "/classSchedule", description = "学校班级课表模块")
public class ClassScheduleController {
    @Autowired
    private ClassScheduleService classScheduleService;

    @PostMapping("/findList")
    @ApiOperation(value = "根据班级编号，获取班级的课表", notes = "返回响应对象")
    public ResponseJson findList(
            @ApiParam(value = "通过班级编号，classId即可查询当前班级的课表信息")
            @RequestBody  ClassSchedule classSchedule) {
        if(StringUtils.isBlank(classSchedule.getClassId())){
            return new ResponseJson(false,"班级编号不能为空");
        }else{
            try{
                List<List<ClassSchedule>> list = classScheduleService.findList(classSchedule);
                return new ResponseJson(list);
            }catch (Exception e){
                return new ResponseJson(false,"数据异常");
            }
        }

    }
    @PostMapping("/todayAndTomorrowClassSchede")
    @ApiOperation(value = "根据班级编号，获取班级今天和明天的课表", notes = "返回响应对象，data 今天课程，data2明天课程")
    public ResponseJson todayAndTomorrowClassSchede(
            @ApiParam(value = "通过班级编号，classId即可查询当前班级今天和明天的课表信息")
            @RequestBody  ClassSchedule classSchedule) {
        if(StringUtils.isBlank(classSchedule.getClassId())){
            return new ResponseJson(false,"班级编号不能为空");
        }else{
            try{
                return classScheduleService.todayAndTomorrowClassSchede(classSchedule);
            }catch (Exception e){
                return new ResponseJson(false,"数据异常");
            }
        }

    }
}
