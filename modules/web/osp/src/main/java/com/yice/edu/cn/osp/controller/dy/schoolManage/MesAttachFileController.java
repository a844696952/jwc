package com.yice.edu.cn.osp.controller.dy.schoolManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import com.yice.edu.cn.osp.service.dy.schoolManage.MesAttachFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/mesAttachFile")
@Api(value = "/mesAttachFile",description = "附件表模块")
public class MesAttachFileController {
    @Autowired
    private MesAttachFileService mesAttachFileService;

    @PostMapping("/saveMesAttachFile")
    @ApiOperation(value = "保存附件表对象", notes = "返回保存好的附件表对象", response= MesAttachFile.class)
    public ResponseJson saveMesAttachFile(
            @ApiParam(value = "附件表对象", required = true)
            @RequestBody MesAttachFile mesAttachFile){
       mesAttachFile.setSchoolId(mySchoolId());
        MesAttachFile s=mesAttachFileService.saveMesAttachFile(mesAttachFile);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findMesAttachFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找附件表", notes = "返回响应对象", response=MesAttachFile.class)
    public ResponseJson findMesAttachFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAttachFile mesAttachFile=mesAttachFileService.findMesAttachFileById(id);
        return new ResponseJson(mesAttachFile);
    }

    @PostMapping("/update/updateMesAttachFile")
    @ApiOperation(value = "修改附件表对象", notes = "返回响应对象")
    public ResponseJson updateMesAttachFile(
            @ApiParam(value = "被修改的附件表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAttachFile mesAttachFile){
        mesAttachFileService.updateMesAttachFile(mesAttachFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookMesAttachFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找附件表", notes = "返回响应对象", response=MesAttachFile.class)
    public ResponseJson lookMesAttachFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        MesAttachFile mesAttachFile=mesAttachFileService.findMesAttachFileById(id);
        return new ResponseJson(mesAttachFile);
    }

    @PostMapping("/findMesAttachFilesByCondition")
    @ApiOperation(value = "根据条件查找附件表", notes = "返回响应对象", response=MesAttachFile.class)
    public ResponseJson findMesAttachFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAttachFile mesAttachFile){
       mesAttachFile.setSchoolId(mySchoolId());
        List<MesAttachFile> data=mesAttachFileService.findMesAttachFileListByCondition(mesAttachFile);
        long count=mesAttachFileService.findMesAttachFileCountByCondition(mesAttachFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneMesAttachFileByCondition")
    @ApiOperation(value = "根据条件查找单个附件表,结果必须为单条数据", notes = "没有时返回空", response=MesAttachFile.class)
    public ResponseJson findOneMesAttachFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody MesAttachFile mesAttachFile){
        MesAttachFile one=mesAttachFileService.findOneMesAttachFileByCondition(mesAttachFile);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteMesAttachFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteMesAttachFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        mesAttachFileService.deleteMesAttachFile(id);
        return new ResponseJson();
    }


    @PostMapping("/findMesAttachFileListByCondition")
    @ApiOperation(value = "根据条件查找附件表列表", notes = "返回响应对象,不包含总条数", response=MesAttachFile.class)
    public ResponseJson findMesAttachFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody MesAttachFile mesAttachFile){
       mesAttachFile.setSchoolId(mySchoolId());
        List<MesAttachFile> data=mesAttachFileService.findMesAttachFileListByCondition(mesAttachFile);
        return new ResponseJson(data);
    }



}
