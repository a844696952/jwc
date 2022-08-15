package com.yice.edu.cn.osp.controller.xw.stuInAndOut;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.ClassTime;
import com.yice.edu.cn.osp.service.xw.stuInAndOut.ClassTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.Comparator;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/classTime")
@Api(value = "/classTime",description = "模块")
public class ClassTimeController {
    @Autowired
    private ClassTimeService classTimeService;

    @PostMapping("/saveClassTime")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=ClassTime.class)
    public ResponseJson saveClassTime(
            @ApiParam(value = "对象", required = true)
            @RequestBody ClassTime classTime){
        ClassTime classTime1=new ClassTime();
        classTime1.setSchoolId(mySchoolId());
        List<ClassTime> list = classTimeService.findClassTimeListByCondition(classTime1);
        if(list.size()==0){
            classTime.setSchoolId(mySchoolId());
            ClassTime s=classTimeService.saveClassTime(classTime);
            return new ResponseJson(s);
        }else {
            ClassTime ct = list.stream().filter(c -> c.getSchoolId().equals(mySchoolId())).sorted(Comparator.comparing(ClassTime::getCreateTime).reversed()).findFirst().get();
        if(DateUtil.timeToSecond(ct.getEndTime())>=DateUtil.timeToSecond(classTime.getStartTime())){
            return new ResponseJson(false,"新增的开始时间要大于上一条的结束时间");
        }  classTime.setSchoolId(mySchoolId());
            ClassTime s=classTimeService.saveClassTime(classTime);
            return new ResponseJson(s);
        }
    }

    @GetMapping("/update/findClassTimeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=ClassTime.class)
    public ResponseJson findClassTimeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ClassTime classTime=classTimeService.findClassTimeById(id);
        return new ResponseJson(classTime);
    }

    @PostMapping("/update/updateClassTime")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateClassTime(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody ClassTime classTime){
        classTimeService.updateClassTime(classTime);
        return new ResponseJson();
    }

    @GetMapping("/look/lookClassTimeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=ClassTime.class)
    public ResponseJson lookClassTimeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ClassTime classTime=classTimeService.findClassTimeById(id);
        return new ResponseJson(classTime);
    }

    @PostMapping("/findClassTimesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=ClassTime.class)
    public ResponseJson findClassTimesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClassTime classTime){
        classTime.setSchoolId(mySchoolId());
        List<ClassTime> data=classTimeService.findClassTimeListByCondition(classTime);
        long count=classTimeService.findClassTimeCountByCondition(classTime);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneClassTimeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=ClassTime.class)
    public ResponseJson findOneClassTimeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ClassTime classTime){
        ClassTime one=classTimeService.findOneClassTimeByCondition(classTime);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteClassTime/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteClassTime(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        classTimeService.deleteClassTime(id);
        return new ResponseJson();
    }


    @PostMapping("/findClassTimeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=ClassTime.class)
    public ResponseJson findClassTimeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ClassTime classTime){
        classTime.setSchoolId(mySchoolId());
        classTime.getPager().setSortField("startTime");
        classTime.getPager().setSortOrder("ASC");
        List<ClassTime> data=classTimeService.findClassTimeListByCondition(classTime);
        return new ResponseJson(data);
    }

}
