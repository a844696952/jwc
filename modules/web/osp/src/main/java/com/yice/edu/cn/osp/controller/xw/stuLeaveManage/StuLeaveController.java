package com.yice.edu.cn.osp.controller.xw.stuLeaveManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import com.yice.edu.cn.osp.service.xw.stuLeaveManage.StuLeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/stuLeave")
@Api(value = "/stuLeave", description = "学生请假模块")
public class StuLeaveController {
    @Autowired
    private StuLeaveService stuLeaveService;

    @PostMapping("/saveStuLeave")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = StuLeave.class)
    public ResponseJson saveStuLeave(
            @ApiParam(value = "对象", required = true)
            @RequestBody StuLeave stuLeave) {
        stuLeave.setSchoolId(mySchoolId());
        StuLeave s = stuLeaveService.saveStuLeave(stuLeave);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findStuLeaveById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = StuLeave.class)
    public ResponseJson findStuLeaveById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        StuLeave stuLeave = stuLeaveService.findStuLeaveById(id);
        return new ResponseJson(stuLeave);
    }

    @PostMapping("/update/updateStuLeave")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateStuLeave(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody StuLeave stuLeave) {
        stuLeaveService.updateStuLeave(stuLeave);
        return new ResponseJson();
    }

    @PostMapping("/update/updateStuLeaveForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateStuLeaveForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody StuLeave stuLeave) {
        stuLeaveService.updateStuLeaveForAll(stuLeave);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStuLeaveById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = StuLeave.class)
    public ResponseJson lookStuLeaveById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        StuLeave stuLeave = stuLeaveService.findStuLeaveById(id);
        return new ResponseJson(stuLeave);
    }

    @PostMapping("/findStuLeavesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = StuLeave.class)
    public ResponseJson findStuLeavesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuLeave stuLeave) {
        StuLeave s = new StuLeave();
        s.setSchoolId(mySchoolId());
        List<StuLeave> data = stuLeaveService.findStuLeaveListByCondition(s);
        if (data.size() > 0 && data != null) {
            for (StuLeave ss : data) {
                if (DateUtil.parse(DateUtil.now()).isAfterOrEquals(DateUtil.parse(ss.getEndTime() + ":00"))
                        && ss.getApplicatiorType().equals("0") && ss.getApproveStatus().equals("2")) {
                    ss.setApproveStatus("3");
                    stuLeaveService.updateStuLeave(ss);
                }
            }
        }
        stuLeave.setSchoolId(mySchoolId());
        Pager pager = stuLeave.getPager();
        if(stuLeave.getRangeTime()!=null&&stuLeave.getRangeTime().length>0){
            String[] arr = stuLeave.getRangeTime();
            arr[0] = arr[0] + " 00:00:00";
            arr[1] = arr[1] + " 23:59:59";
            pager.setRangeField("createTime");
            pager.setRangeArray(arr);
         }
        stuLeave.setRangeTime(null);
        stuLeave.getPager().setLike("student.name");
        stuLeave.getPager().setSortField("createTime");
        stuLeave.getPager().setSortOrder(Pager.DESC);
        List<StuLeave> list = stuLeaveService.findStuLeaveListByCondition(stuLeave);
        long count = stuLeaveService.findStuLeaveCountByCondition(stuLeave);
        return new ResponseJson(list, count);
    }

    @PostMapping("/findOneStuLeaveByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = StuLeave.class)
    public ResponseJson findOneStuLeaveByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuLeave stuLeave) {
        StuLeave one = stuLeaveService.findOneStuLeaveByCondition(stuLeave);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteStuLeave/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStuLeave(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        stuLeaveService.deleteStuLeave(id);
        return new ResponseJson();
    }


    @PostMapping("/findStuLeaveListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = StuLeave.class)
    public ResponseJson findStuLeaveListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody StuLeave stuLeave) {
        stuLeave.setSchoolId(mySchoolId());
        List<StuLeave> data = stuLeaveService.findStuLeaveListByCondition(stuLeave);
        return new ResponseJson(data);
    }

    @PostMapping("/findStuLeaveListByConditionForWH")
    @ApiOperation(value = "不带count", notes = "返回响应对象,不包含总条数", response = StuLeave.class)
    public ResponseJson findStuLeaveListByConditionForWH(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuLeave stuLeave) {
        stuLeave.setSchoolId(mySchoolId());
        List<StuLeave> list = stuLeaveService.findStuLeaveListByConditionForWH(stuLeave);
        return new ResponseJson(list);
    }

    @PostMapping("/findStuLeavesByConditionForWH")
    @ApiOperation(value = "带count", notes = "返回响应对象,不包含总条数", response = StuLeave.class)
    public ResponseJson findStuLeavesByConditionForWH(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StuLeave stuLeave) {
        stuLeave.setSchoolId(mySchoolId());
        List<StuLeave> list = stuLeaveService.findStuLeaveListByConditionForWH(stuLeave);
        long count = stuLeaveService.findStuLeaveCountByConditionForWH(stuLeave);
        return new ResponseJson(list, count);
    }

}
