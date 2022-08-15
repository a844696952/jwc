package com.yice.edu.cn.dy.controller.schoolManage.attachFile;

import com.yice.edu.cn.common.pojo.mes.schoolManage.mesAttachFile.MesAttachFile;
import com.yice.edu.cn.dy.service.schoolManage.attachFile.MesAttachFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesAttachFile")
@Api(value = "/mesAttachFile",description = "附件表模块")
public class MesAttachFileController {
    @Autowired
    private MesAttachFileService mesAttachFileService;

    @GetMapping("/findMesAttachFileById/{id}")
    @ApiOperation(value = "通过id查找附件表", notes = "返回附件表对象")
    public MesAttachFile findMesAttachFileById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return mesAttachFileService.findMesAttachFileById(id);
    }

    @PostMapping("/saveMesAttachFile")
    @ApiOperation(value = "保存附件表", notes = "返回附件表对象")
    public MesAttachFile saveMesAttachFile(
            @ApiParam(value = "附件表对象", required = true)
            @RequestBody MesAttachFile mesAttachFile){
        mesAttachFileService.saveMesAttachFile(mesAttachFile);
        return mesAttachFile;
    }

    @PostMapping("/findMesAttachFileListByCondition")
    @ApiOperation(value = "根据条件查找附件表列表", notes = "返回附件表列表")
    public List<MesAttachFile> findMesAttachFileListByCondition(
            @ApiParam(value = "附件表对象")
            @RequestBody MesAttachFile mesAttachFile){
        return mesAttachFileService.findMesAttachFileListByCondition(mesAttachFile);
    }
    @PostMapping("/findMesAttachFileCountByCondition")
    @ApiOperation(value = "根据条件查找附件表列表个数", notes = "返回附件表总个数")
    public long findMesAttachFileCountByCondition(
            @ApiParam(value = "附件表对象")
            @RequestBody MesAttachFile mesAttachFile){
        return mesAttachFileService.findMesAttachFileCountByCondition(mesAttachFile);
    }

    @PostMapping("/updateMesAttachFile")
    @ApiOperation(value = "修改附件表", notes = "附件表对象必传")
    public void updateMesAttachFile(
            @ApiParam(value = "附件表对象,对象属性不为空则修改", required = true)
            @RequestBody MesAttachFile mesAttachFile){
        mesAttachFileService.updateMesAttachFile(mesAttachFile);
    }

    @GetMapping("/deleteMesAttachFile/{id}")
    @ApiOperation(value = "通过id删除附件表")
    public void deleteMesAttachFile(
            @ApiParam(value = "附件表对象", required = true)
            @PathVariable String id){
        mesAttachFileService.deleteMesAttachFile(id);
    }
    @PostMapping("/deleteMesAttachFileByCondition")
    @ApiOperation(value = "根据条件删除附件表")
    public void deleteMesAttachFileByCondition(
            @ApiParam(value = "附件表对象")
            @RequestBody MesAttachFile mesAttachFile){
        mesAttachFileService.deleteMesAttachFileByCondition(mesAttachFile);
    }
    @PostMapping("/findOneMesAttachFileByCondition")
    @ApiOperation(value = "根据条件查找单个附件表,结果必须为单条数据", notes = "返回单个附件表,没有时为空")
    public MesAttachFile findOneMesAttachFileByCondition(
            @ApiParam(value = "附件表对象")
            @RequestBody MesAttachFile mesAttachFile){
        return mesAttachFileService.findOneMesAttachFileByCondition(mesAttachFile);
    }
}
