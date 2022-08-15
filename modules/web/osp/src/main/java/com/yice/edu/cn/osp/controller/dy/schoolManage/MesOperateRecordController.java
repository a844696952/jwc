package com.yice.edu.cn.osp.controller.dy.schoolManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.audit.MesOperateRecord;
import com.yice.edu.cn.osp.service.dy.schoolManage.MesOperateRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/mesOperateRecord")
@Api(value = "/mesOperateRecord",description = "操作记录表模块")
public class MesOperateRecordController {
    @Autowired
    private MesOperateRecordService mesOperateRecordService;

    @PostMapping("/saveMesOperateRecord")
    @ApiOperation(value = "保存操作记录表对象", notes = "返回保存好的操作记录表对象", response= MesOperateRecord.class)
    public ResponseJson saveMesOperateRecord(
            @ApiParam(value = "操作记录表对象", required = true)
            @RequestBody MesOperateRecord mesOperateRecord){
       mesOperateRecord.setSchoolId(mySchoolId());
        MesOperateRecord s=mesOperateRecordService.saveMesOperateRecord(mesOperateRecord);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesOperateRecordById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找操作记录表", notes = "返回响应对象", response=MesOperateRecord.class)
    public ResponseJson findMesOperateRecordById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MesOperateRecord mesOperateRecord=mesOperateRecordService.findMesOperateRecordById(id);
        return new ResponseJson(mesOperateRecord);
    }

    @PostMapping("/update/updateMesOperateRecord")
    @ApiOperation(value = "修改操作记录表对象", notes = "返回响应对象")
    public ResponseJson updateMesOperateRecord(
            @ApiParam(value = "被修改的操作记录表对象,对象属性不为空则修改", required = true)
            @RequestBody MesOperateRecord mesOperateRecord){
        mesOperateRecordService.updateMesOperateRecord(mesOperateRecord);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesOperateRecordById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找操作记录表", notes = "返回响应对象", response=MesOperateRecord.class)
    public ResponseJson lookMesOperateRecordById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesOperateRecord mesOperateRecord=mesOperateRecordService.findMesOperateRecordById(id);
        return new ResponseJson(mesOperateRecord);
    }

    @PostMapping("/findMesOperateRecordsByCondition")
    @ApiOperation(value = "根据条件查找操作记录表", notes = "返回响应对象", response=MesOperateRecord.class)
    public ResponseJson findMesOperateRecordsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesOperateRecord mesOperateRecord){
       mesOperateRecord.setSchoolId(mySchoolId());
        List<MesOperateRecord> data=mesOperateRecordService.findMesOperateRecordListByCondition(mesOperateRecord);
        long count=mesOperateRecordService.findMesOperateRecordCountByCondition(mesOperateRecord);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneMesOperateRecordByCondition")
    @ApiOperation(value = "根据条件查找单个操作记录表,结果必须为单条数据", notes = "没有时返回空", response=MesOperateRecord.class)
    public ResponseJson findOneMesOperateRecordByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesOperateRecord mesOperateRecord){
        MesOperateRecord one=mesOperateRecordService.findOneMesOperateRecordByCondition(mesOperateRecord);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteMesOperateRecord/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesOperateRecord(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        mesOperateRecordService.deleteMesOperateRecord(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesOperateRecordListByCondition")
    @ApiOperation(value = "根据条件查找操作记录表列表", notes = "返回响应对象,不包含总条数", response=MesOperateRecord.class)
    public ResponseJson findMesOperateRecordListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesOperateRecord mesOperateRecord){
       mesOperateRecord.setSchoolId(mySchoolId());
        List<MesOperateRecord> data=mesOperateRecordService.findMesOperateRecordListByCondition(mesOperateRecord);
        return new ResponseJson(data);
    }

    @PostMapping("/update/passOrRefuse")
    @ApiOperation(value = "审核通过或拒绝")
    public ResponseJson passOrRefuse(
            @ApiParam(value = "操作表对象", required = true)
            @RequestBody MesOperateRecord mesOperateRecord){
        if(mesOperateRecord.getOperateContent().length()>256){
            return new ResponseJson(false,"审核备注不能超过256个字符");
        }
        mesOperateRecord.setSchoolId(mySchoolId());
        mesOperateRecord.setOperatorName(currentTeacher().getName());
        mesOperateRecord.setOperatorId(myId());
        int row = mesOperateRecordService.passOrRefuse(mesOperateRecord);
        if(row==0){
            return new ResponseJson(false,"操作失败");
        }
        return new ResponseJson();
    }



}
