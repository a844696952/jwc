package com.yice.edu.cn.jy.controller.prepareLessonsNew;

import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JySubjectMaterialExtend;
import com.yice.edu.cn.jy.service.prepareLessonsNew.JySubjectMaterialExtendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jySubjectMaterialExtend")
@Api(value = "/jySubjectMaterialExtend",description = "模块")
public class JySubjectMaterialExtendController {
    @Autowired
    private JySubjectMaterialExtendService jySubjectMaterialExtendService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findJySubjectMaterialExtendById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public JySubjectMaterialExtend findJySubjectMaterialExtendById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return jySubjectMaterialExtendService.findJySubjectMaterialExtendById(id);
    }

    @PostMapping("/saveJySubjectMaterialExtend")
    @ApiOperation(value = "保存", notes = "返回对象")
    public JySubjectMaterialExtend saveJySubjectMaterialExtend(
            @ApiParam(value = "对象", required = true)
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend){
        jySubjectMaterialExtendService.saveJySubjectMaterialExtend(jySubjectMaterialExtend);
        return jySubjectMaterialExtend;
    }

    @PostMapping("/batchSaveJySubjectMaterialExtend")
    @ApiOperation(value = "批量保存")
    public void batchSaveJySubjectMaterialExtend(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<JySubjectMaterialExtend> jySubjectMaterialExtends){
        jySubjectMaterialExtendService.batchSaveJySubjectMaterialExtend(jySubjectMaterialExtends);
    }

    @PostMapping("/findJySubjectMaterialExtendListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<JySubjectMaterialExtend> findJySubjectMaterialExtendListByCondition(
            @ApiParam(value = "对象")
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend){
        return jySubjectMaterialExtendService.findJySubjectMaterialExtendListByCondition(jySubjectMaterialExtend);
    }
    @PostMapping("/findJySubjectMaterialExtendCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findJySubjectMaterialExtendCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend){
        return jySubjectMaterialExtendService.findJySubjectMaterialExtendCountByCondition(jySubjectMaterialExtend);
    }

    @PostMapping("/updateJySubjectMaterialExtend")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateJySubjectMaterialExtend(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend){
        jySubjectMaterialExtendService.updateJySubjectMaterialExtend(jySubjectMaterialExtend);
    }

    @PostMapping("/updateJySubjectMaterialExtendSort")
    @ApiOperation(value = "排序", notes = "对象必传")
    public void updateJySubjectMaterialExtendSort(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody List<JySubjectMaterialExtend> jsmeList){
        jySubjectMaterialExtendService.updateJySubjectMaterialExtendSort(jsmeList);
    }

    @PostMapping("/updateJySubjectMaterialExtendForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateJySubjectMaterialExtendForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend){
        jySubjectMaterialExtendService.updateJySubjectMaterialExtendForAll(jySubjectMaterialExtend);
    }

    @GetMapping("/deleteJySubjectMaterialExtend/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteJySubjectMaterialExtend(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        jySubjectMaterialExtendService.deleteJySubjectMaterialExtend(id);
    }
    @PostMapping("/deleteJySubjectMaterialExtendByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteJySubjectMaterialExtendByCondition(
            @ApiParam(value = "对象")
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend){
        jySubjectMaterialExtendService.deleteJySubjectMaterialExtendByCondition(jySubjectMaterialExtend);
    }
    @PostMapping("/findOneJySubjectMaterialExtendByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public JySubjectMaterialExtend findOneJySubjectMaterialExtendByCondition(
            @ApiParam(value = "对象")
            @RequestBody JySubjectMaterialExtend jySubjectMaterialExtend){
        return jySubjectMaterialExtendService.findOneJySubjectMaterialExtendByCondition(jySubjectMaterialExtend);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
