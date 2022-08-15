package com.yice.edu.cn.jy.controller.prepareLessonsNew;

import com.yice.edu.cn.common.pojo.jy.prepareLessonsNew.JyMaterialExtend;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import com.yice.edu.cn.jy.service.prepareLessonsNew.JyMaterialExtendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jyMaterialExtend")
@Api(value = "/jyMaterialExtend", description = "模块")
public class JyMaterialExtendController {
    @Autowired
    private JyMaterialExtendService jyMaterialExtendService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findJyMaterialExtendById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public JyMaterialExtend findJyMaterialExtendById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return jyMaterialExtendService.findJyMaterialExtendById(id);
    }

    @PostMapping("/saveJyMaterialExtend")
    @ApiOperation(value = "保存", notes = "返回对象")
    public JyMaterialExtend saveJyMaterialExtend(
            @ApiParam(value = "对象", required = true)
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendService.saveJyMaterialExtend(jyMaterialExtend);
        return jyMaterialExtend;
    }

    @PostMapping("/batchSaveJyMaterialExtend")
    @ApiOperation(value = "批量保存")
    public void batchSaveJyMaterialExtend(
            @ApiParam(value = "对象集合", required = true)
            @RequestBody List<JyMaterialExtend> jyMaterialExtends) {
        jyMaterialExtendService.batchSaveJyMaterialExtend(jyMaterialExtends);
    }

    @PostMapping("/findJyMaterialExtendListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<JyMaterialExtend> findJyMaterialExtendListByCondition(
            @ApiParam(value = "对象")
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendService.findJyMaterialExtendListByCondition(jyMaterialExtend);
    }

    @PostMapping("/findJyMaterialExtendCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findJyMaterialExtendCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendService.findJyMaterialExtendCountByCondition(jyMaterialExtend);
    }

    @PostMapping("/updateJyMaterialExtend")
    @ApiOperation(value = "修改有值的字段", notes = "对象必传")
    public void updateJyMaterialExtend(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendService.updateJyMaterialExtend(jyMaterialExtend);
    }

    @PostMapping("/updateJyMaterialExtendForAll")
    @ApiOperation(value = "修改所有字段", notes = "对象必传")
    public void updateJyMaterialExtendForAll(
            @ApiParam(value = "对象", required = true)
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendService.updateJyMaterialExtendForAll(jyMaterialExtend);
    }

    @GetMapping("/deleteJyMaterialExtend/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteJyMaterialExtend(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        jyMaterialExtendService.deleteJyMaterialExtend(id);
    }

    @PostMapping("/deleteJyMaterialExtendByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteJyMaterialExtendByCondition(
            @ApiParam(value = "对象")
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        jyMaterialExtendService.deleteJyMaterialExtendByCondition(jyMaterialExtend);
    }

    @PostMapping("/findOneJyMaterialExtendByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public JyMaterialExtend findOneJyMaterialExtendByCondition(
            @ApiParam(value = "对象")
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendService.findOneJyMaterialExtendByCondition(jyMaterialExtend);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findMaterialListByCondition")
    @ApiOperation(value = "根据年段id查询教材资源", notes = "返回教材资源，包括已引用和未引用的")
    public List<Material> findMaterialListByCondition(
            @ApiParam(value = "对象")
            @RequestBody JyMaterialExtend jyMaterialExtend) {
        return jyMaterialExtendService.findMaterialListByCondition(jyMaterialExtend);
    }

}
