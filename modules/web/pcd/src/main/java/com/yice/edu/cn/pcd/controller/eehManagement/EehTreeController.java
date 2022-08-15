package com.yice.edu.cn.pcd.controller.eehManagement;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import com.yice.edu.cn.pcd.interceptor.LoginInterceptor;
import com.yice.edu.cn.pcd.service.eehManagement.EehTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/eehTree")
@Api(value = "/eehTree",description = "模块")
public class EehTreeController {
    @Autowired
    private EehTreeService eehTreeService;
    @PostMapping("/saveEehTree")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=EehTree.class)
    public ResponseJson saveEehTree(
            @ApiParam(value = "对象", required = true)
            @RequestBody EehTree eehTree){
        eehTreeService.saveEehTree(eehTree);
        return new ResponseJson();
    }

    @GetMapping("/findEehTreeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=EehTree.class)
    public ResponseJson findEehTreeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        EehTree eehTree=eehTreeService.findEehTreeById(id);
        return new ResponseJson(eehTree);
    }

    @PostMapping("/updateEehTree")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateEehTree(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody EehTree eehTree){
        eehTreeService.updateEehTree(eehTree);
        return new ResponseJson();
    }

    @PostMapping("/updateEehTreeForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateEehTreeForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody EehTree eehTree){
        eehTreeService.updateEehTreeForAll(eehTree);
        return new ResponseJson();
    }


    @PostMapping("/findEehTreesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=EehTree.class)
    public ResponseJson findEehTreesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EehTree eehTree){
        List<EehTree> data=eehTreeService.findEehTreeListByCondition(eehTree);
        long count=eehTreeService.findEehTreeCountByCondition(eehTree);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneEehTreeByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=EehTree.class)
    public ResponseJson findOneEehTreeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EehTree eehTree){
        EehTree one=eehTreeService.findOneEehTreeByCondition(eehTree);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteEehTree/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteEehTree(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        eehTreeService.deleteEehTree(id);
        return new ResponseJson();
    }


    @PostMapping("/findEehTreeListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=EehTree.class)
    public ResponseJson findEehTreeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody EehTree eehTree){
        List<EehTree> data=eehTreeService.findEehTreeListByCondition(eehTree);
        return new ResponseJson(data);
    }

    @PostMapping("/findEehSchoolListNoCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=EehTree.class)
    public ResponseJson findEehSchoolListNoCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody EehTree eehTree){
        eehTree.setId(LoginInterceptor.currentEehId());
        List<EehTree> data=eehTreeService.findEehSchoolListNoCondition(eehTree);
        return new ResponseJson(data);
    }

}
