package com.yice.edu.cn.osp.controller.jw.classSchedule;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import com.yice.edu.cn.osp.service.jw.classSchedule.ScheduleListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/scheduleList")
@Api(value = "/scheduleList",description = "课表管理列表模块")
public class ScheduleListController {
    @Autowired
    private ScheduleListService scheduleListService;
    @PostMapping("/saveScheduleList")
    @ApiOperation(value = "保存课表管理列表对象", notes = "返回保存好的课表管理列表对象", response=ScheduleList.class)
    public ResponseJson saveScheduleList(
            @ApiParam(value = "课表管理列表对象", required = true)
            @RequestBody ScheduleList scheduleList){
       scheduleList.setSchoolId(mySchoolId());
       scheduleList.setUserId(myId());
       return scheduleListService.saveScheduleListKong(scheduleList);
    }

    @GetMapping("/findScheduleListById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找课表管理列表", notes = "返回响应对象", response=ScheduleList.class)
    public ResponseJson findScheduleListById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ScheduleList scheduleList=scheduleListService.findScheduleListById(id);
        return new ResponseJson(scheduleList);
    }

    @PostMapping("/updateScheduleList")
    @ApiOperation(value = "修改课表管理列表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateScheduleList(
            @ApiParam(value = "被修改的课表管理列表对象,对象属性不为空则修改", required = true)
            @RequestBody ScheduleList scheduleList){
        return scheduleListService.issueScheduleListKong(scheduleList);
    }

    @PostMapping("/updateScheduleListForAll")
    @ApiOperation(value = "修改课表管理列表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateScheduleListForAll(
            @ApiParam(value = "被修改的课表管理列表对象", required = true)
            @RequestBody ScheduleList scheduleList){
        scheduleListService.updateScheduleListForAll(scheduleList);
        return new ResponseJson();
    }


    @PostMapping("/findScheduleListsByCondition")
    @ApiOperation(value = "根据条件查找课表管理列表", notes = "返回响应对象", response=ScheduleList.class)
    public ResponseJson findScheduleListsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ScheduleList scheduleList){
       scheduleList.setSchoolId(mySchoolId());
        List<ScheduleList> data=scheduleListService.findScheduleListListByCondition(scheduleList);
        long count=scheduleListService.findScheduleListCountByCondition(scheduleList);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneScheduleListByCondition")
    @ApiOperation(value = "根据条件查找单个课表管理列表,结果必须为单条数据", notes = "没有时返回空", response=ScheduleList.class)
    public ResponseJson findOneScheduleListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ScheduleList scheduleList){
        ScheduleList one=scheduleListService.findOneScheduleListByCondition(scheduleList);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteScheduleList/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteScheduleList(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        scheduleListService.deleteScheduleAndClassSchedule(id);
        return new ResponseJson();
    }


    @PostMapping("/findScheduleListListByCondition")
    @ApiOperation(value = "根据条件查找课表管理列表列表", notes = "返回响应对象,不包含总条数", response=ScheduleList.class)
    public ResponseJson findScheduleListListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ScheduleList scheduleList){
       scheduleList.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setIncludes("name,fromTo");
        List<ScheduleList> data=scheduleListService.findScheduleListListByCondition(scheduleList);
        return new ResponseJson(data);
    }

    @GetMapping("/updateScheduleAndSavePastSchedule")
    public ResponseJson updateScheduleAndSavePastSchedule(){
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.setSchoolId(mySchoolId());
        scheduleList.setUserId(myId());
        scheduleListService.updateScheduleAndSavePastSchedule(scheduleList);
        return new ResponseJson();
    }

    @PostMapping("/download")
    public void exportClassSchedule(
            @ApiParam(value = "课程表")
            @RequestBody ScheduleList scheduleList,
            HttpServletResponse response) {
        scheduleList.setSchoolId(mySchoolId());
        //告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=schedule.xls");

        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = scheduleListService.exportClassSchedule(scheduleList);
//            response.setContentLength();
            workbook.write(s);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
