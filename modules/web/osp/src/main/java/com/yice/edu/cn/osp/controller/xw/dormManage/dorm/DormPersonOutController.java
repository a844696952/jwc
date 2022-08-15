package com.yice.edu.cn.osp.controller.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonOut;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormPersonOutService;
import io.lettuce.core.GeoArgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dormPersonOut")
@Api(value = "/dormPersonOut",description = "模块")
public class DormPersonOutController {
    @Autowired
    private DormPersonOutService dormPersonOutService;

    @PostMapping("/saveDormPersonOut")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=DormPersonOut.class)
    public ResponseJson saveDormPersonOut(
            @ApiParam(value = "对象", required = true)
            @RequestBody DormPersonOut dormPersonOut){
       dormPersonOut.setSchoolId(mySchoolId());
        DormPersonOut s=dormPersonOutService.saveDormPersonOut(dormPersonOut);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDormPersonOutById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=DormPersonOut.class)
    public ResponseJson findDormPersonOutById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DormPersonOut dormPersonOut=dormPersonOutService.findDormPersonOutById(id);
        return new ResponseJson(dormPersonOut);
    }

    @PostMapping("/update/updateDormPersonOut")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDormPersonOut(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DormPersonOut dormPersonOut){
        dormPersonOutService.updateDormPersonOut(dormPersonOut);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDormPersonOutById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=DormPersonOut.class)
    public ResponseJson lookDormPersonOutById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DormPersonOut dormPersonOut=dormPersonOutService.findDormPersonOutById(id);
        return new ResponseJson(dormPersonOut);
    }

    @PostMapping("/findDormPersonOutsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=DormPersonOut.class)
    public ResponseJson findDormPersonOutsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormPersonOut dormPersonOut){
       dormPersonOut.setSchoolId(mySchoolId());
        List<DormPersonOut> data=dormPersonOutService.findDormPersonOutListByCondition(dormPersonOut);
        long count=dormPersonOutService.findDormPersonOutCountByCondition(dormPersonOut);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDormPersonOutByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=DormPersonOut.class)
    public ResponseJson findOneDormPersonOutByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DormPersonOut dormPersonOut){
        DormPersonOut one=dormPersonOutService.findOneDormPersonOutByCondition(dormPersonOut);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDormPersonOut/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDormPersonOut(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dormPersonOutService.deleteDormPersonOut(id);
        return new ResponseJson();
    }


    @PostMapping("/findDormPersonOutListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=DormPersonOut.class)
    public ResponseJson findDormPersonOutListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormPersonOut dormPersonOut){
       dormPersonOut.setSchoolId(mySchoolId());
        List<DormPersonOut> data=dormPersonOutService.findDormPersonOutListByCondition(dormPersonOut);
        return new ResponseJson(data);
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    @PostMapping("/out/findDormPersonOutListByConditionWithStudent")
    @ApiOperation(value = "根据条件查找退宿学生人员列表", notes = "返回响应对象,含总条数", response=DormPersonLog.class)
    public ResponseJson findDormPersonOutListByConditionWithStudent(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormPersonOut dormPersonOut){
        dormPersonOut.setSchoolId(mySchoolId());
        dormPersonOut.setPersonType("1");
        Pager pager = dormPersonOut.getPager();
        pager.setSortField("outTime");
        pager.setSortOrder(Pager.DESC);
        dormPersonOut.setPager(pager);
        List<DormPersonOut> data=dormPersonOutService.findDormPersonOutListByConditionAndPersonType(dormPersonOut);
        long count = dormPersonOutService.findDormPersonOutCountByConditionAndPersonType(dormPersonOut);
        return new ResponseJson(data,count);
    }

    @PostMapping("/out/findDormPersonOutListByConditionWithTeacher")
    @ApiOperation(value = "根据条件查找退宿教师及非教职工人员列表", notes = "返回响应对象,含总条数", response=DormPersonLog.class)
    public ResponseJson findDormPersonOutListByConditionWithTeacher(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormPersonOut dormPersonOut){
        dormPersonOut.setSchoolId(mySchoolId());
        dormPersonOut.setPersonType("2");
        Pager pager = dormPersonOut.getPager();
        pager.setSortField("outTime");
        pager.setSortOrder(Pager.DESC);
        dormPersonOut.setPager(pager);
        List<DormPersonOut> data=dormPersonOutService.findDormPersonOutListByConditionAndPersonType(dormPersonOut);
        long count = dormPersonOutService.findDormPersonOutCountByConditionAndPersonType(dormPersonOut);
        return new ResponseJson(data,count);
    }




}
