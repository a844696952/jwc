package com.yice.edu.cn.tap.controller.repairNew;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.tap.service.repairNew.RepairFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/repairFile")
@Api(value = "/repairFile", description = "新报修文件表模块")
public class RepairFileController {
    @Autowired
    private RepairFileService repairFileService;

    @PostMapping("/saveRepairFile")
    @ApiOperation(value = "保存新报修文件表对象", notes = "返回保存好的新报修文件表对象", response=RepairFile.class)
    public ResponseJson saveRepairFile(
            @ApiParam(value = "新报修文件表对象", required = true)
            @RequestBody RepairFile repairFile){
       repairFile.setSchoolId(mySchoolId());
        RepairFile s=repairFileService.saveRepairFile(repairFile);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findRepairFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找新报修文件表", notes = "返回响应对象", response=RepairFile.class)
    public ResponseJson findRepairFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        RepairFile repairFile=repairFileService.findRepairFileById(id);
        return new ResponseJson(repairFile);
    }

    @PostMapping("/update/updateRepairFile")
    @ApiOperation(value = "修改新报修文件表对象", notes = "返回响应对象")
    public ResponseJson updateRepairFile(
            @ApiParam(value = "被修改的新报修文件表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairFile repairFile){
        repairFileService.updateRepairFile(repairFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookRepairFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找新报修文件表", notes = "返回响应对象", response=RepairFile.class)
    public ResponseJson lookRepairFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        RepairFile repairFile=repairFileService.findRepairFileById(id);
        return new ResponseJson(repairFile);
    }

    @PostMapping("/findRepairFilesByCondition")
    @ApiOperation(value = "根据条件查找新报修文件表", notes = "返回响应对象", response=RepairFile.class)
    public ResponseJson findRepairFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairFile repairFile){
       repairFile.setSchoolId(mySchoolId());
        List<RepairFile> data=repairFileService.findRepairFileListByCondition(repairFile);
        long count=repairFileService.findRepairFileCountByCondition(repairFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneRepairFileByCondition")
    @ApiOperation(value = "根据条件查找单个新报修文件表,结果必须为单条数据", notes = "没有时返回空", response=RepairFile.class)
    public ResponseJson findOneRepairFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody RepairFile repairFile){
        RepairFile one=repairFileService.findOneRepairFileByCondition(repairFile);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteRepairFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteRepairFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        repairFileService.deleteRepairFile(id);
        return new ResponseJson();
    }


    @PostMapping("/findRepairFileListByCondition")
    @ApiOperation(value = "根据条件查找新报修文件表列表", notes = "返回响应对象,不包含总条数", response=RepairFile.class)
    public ResponseJson findRepairFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairFile repairFile){
       repairFile.setSchoolId(mySchoolId());
        List<RepairFile> data=repairFileService.findRepairFileListByCondition(repairFile);
        return new ResponseJson(data);
    }



}
