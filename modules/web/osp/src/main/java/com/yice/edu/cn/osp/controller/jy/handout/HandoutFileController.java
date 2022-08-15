package com.yice.edu.cn.osp.controller.jy.handout;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.handout.HandoutFile;
import com.yice.edu.cn.osp.service.jy.handout.HandoutFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/handoutFile")
@Api(value = "/handoutFile",description = "模块")
public class HandoutFileController {
    @Autowired
    private HandoutFileService handoutFileService;

    @PostMapping("/saveHandoutFile")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    public ResponseJson saveHandoutFile(
            @ApiParam(value = "对象", required = true)
            @RequestBody HandoutFile handoutFile){
        handoutFile.setSchoolId(mySchoolId());
       HandoutFile s=handoutFileService.saveHandoutFile(handoutFile);
        return new ResponseJson("请求成功");
    }

    @GetMapping("/update/findHandoutFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findHandoutFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        HandoutFile handoutFile=handoutFileService.findHandoutFileById(id);
        return new ResponseJson(handoutFile);
    }

    @PostMapping("/update/updateHandoutFile")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateHandoutFile(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody HandoutFile handoutFile){
        handoutFile.setSchoolId(mySchoolId());
        handoutFileService.updateHandoutFile(handoutFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHandoutFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookHandoutFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        HandoutFile handoutFile=handoutFileService.findHandoutFileById(id);
        return new ResponseJson(handoutFile);
    }

    @PostMapping("/findHandoutFilesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findHandoutFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HandoutFile handoutFile){
        List<HandoutFile> data=handoutFileService.findHandoutFileListByCondition(handoutFile);
        long count=handoutFileService.findHandoutFileCountByCondition(handoutFile);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneHandoutFileByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneHandoutFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody HandoutFile handoutFile){
        HandoutFile one=handoutFileService.findOneHandoutFileByCondition(handoutFile);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteHandoutFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHandoutFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        handoutFileService.deleteHandoutFile(id);
        return new ResponseJson();
    }


    @PostMapping("/findHandoutFileListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findHandoutFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HandoutFile handoutFile){
        List<HandoutFile> data=handoutFileService.findHandoutFileListByCondition(handoutFile);
        return new ResponseJson(data);
    }



}
