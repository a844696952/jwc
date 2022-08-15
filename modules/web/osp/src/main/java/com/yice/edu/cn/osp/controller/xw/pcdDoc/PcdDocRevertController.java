package com.yice.edu.cn.osp.controller.xw.pcdDoc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRevert;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.xw.pcdDoc.PcdDocRevertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pcdDocRevert")
@Api(value = "/pcdDocRevert",description = "公文回复模块")
public class PcdDocRevertController {
    @Autowired
    private PcdDocRevertService pcdDocRevertService;
    @PostMapping("/savePcdDocRevert")
    @ApiOperation(value = "保存公文回复对象", notes = "返回保存好的公文回复对象", response=PcdDocRevert.class)
    public ResponseJson savePcdDocRevert(
            @ApiParam(value = "公文回复对象", required = true)
            @RequestBody PcdDocRevert pcdDocRevert){
        pcdDocRevert.setEehId(LoginInterceptor.mySchoolId());
        pcdDocRevert.setEehName(LoginInterceptor.currentTeacher().getSchoolName());
        pcdDocRevert.setName(LoginInterceptor.currentTeacher().getName());
        pcdDocRevert.setCreateUserId(LoginInterceptor.myId());
        pcdDocRevertService.savePcdDocRevertKong(pcdDocRevert);
        return new ResponseJson();
    }

    @GetMapping("/findPcdDocRevertById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找公文回复", notes = "返回响应对象", response=PcdDocRevert.class)
    public ResponseJson findPcdDocRevertById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        PcdDocRevert pcdDocRevert=pcdDocRevertService.findAndUpdatePcdDocRevertById(id);
        return new ResponseJson(pcdDocRevert);
    }

    @PostMapping("/updatePcdDocRevert")
    @ApiOperation(value = "修改公文回复对象非空字段", notes = "返回响应对象")
    public ResponseJson updatePcdDocRevert(
            @ApiParam(value = "被修改的公文回复对象,对象属性不为空则修改", required = true)
            @RequestBody PcdDocRevert pcdDocRevert){
        pcdDocRevertService.updatePcdDocRevert(pcdDocRevert);
        return new ResponseJson();
    }

    @PostMapping("/updatePcdDocRevertForAll")
    @ApiOperation(value = "修改公文回复对象所有字段", notes = "返回响应对象")
    public ResponseJson updatePcdDocRevertForAll(
            @ApiParam(value = "被修改的公文回复对象", required = true)
            @RequestBody PcdDocRevert pcdDocRevert){
        pcdDocRevertService.updatePcdDocRevertForAll(pcdDocRevert);
        return new ResponseJson();
    }


    @PostMapping("/findPcdDocRevertsByCondition")
    @ApiOperation(value = "根据条件查找公文回复", notes = "返回响应对象", response=PcdDocRevert.class)
    public ResponseJson findPcdDocRevertsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdDocRevert pcdDocRevert){
        List<PcdDocRevert> data=pcdDocRevertService.findPcdDocRevertListByCondition(pcdDocRevert);
        long count=pcdDocRevertService.findPcdDocRevertCountByCondition(pcdDocRevert);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOnePcdDocRevertByCondition")
    @ApiOperation(value = "根据条件查找单个公文回复,结果必须为单条数据", notes = "没有时返回空", response=PcdDocRevert.class)
    public ResponseJson findOnePcdDocRevertByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PcdDocRevert pcdDocRevert){
        PcdDocRevert one=pcdDocRevertService.findOnePcdDocRevertByCondition(pcdDocRevert);
        return new ResponseJson(one);
    }
    @GetMapping("/deletePcdDocRevert/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePcdDocRevert(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        pcdDocRevertService.deletePcdDocRevert(id);
        return new ResponseJson();
    }


    @PostMapping("/findPcdDocRevertListByCondition")
    @ApiOperation(value = "根据条件查找公文回复列表", notes = "返回响应对象,不包含总条数", response=PcdDocRevert.class)
    public ResponseJson findPcdDocRevertListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PcdDocRevert pcdDocRevert){
        List<PcdDocRevert> data=pcdDocRevertService.findPcdDocRevertListByCondition(pcdDocRevert);
        return new ResponseJson(data);
    }



}
