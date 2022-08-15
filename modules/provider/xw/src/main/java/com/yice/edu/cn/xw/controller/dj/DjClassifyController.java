package com.yice.edu.cn.xw.controller.dj;

import com.yice.edu.cn.common.pojo.xw.dj.DjClassify;
import com.yice.edu.cn.xw.service.dj.DjClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/djClassify")
@Api(value = "/djClassify",description = "党建公用类型表模块")
public class DjClassifyController {
    @Autowired
    private DjClassifyService djClassifyService;

    @GetMapping("/findDjClassifyById/{id}")
    @ApiOperation(value = "通过id查找党建公用类型表", notes = "返回党建公用类型表对象")
    public DjClassify findDjClassifyById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return djClassifyService.findDjClassifyById(id);
    }

    @GetMapping("/findActivityDjClassify")
    @ApiOperation(value = "查找所有党建活动类型", notes = "返回所有党建活动类型")
    public List<DjClassify> findActivityDjClassify(){
        return djClassifyService.findActivityDjClassify();
    }

    @PostMapping("/saveDjClassify")
    @ApiOperation(value = "保存党建公用类型表", notes = "返回党建公用类型表对象")
    public DjClassify saveDjClassify(
            @ApiParam(value = "党建公用类型表对象", required = true)
            @RequestBody DjClassify djClassify){
        djClassifyService.saveDjClassify(djClassify);
        return djClassify;
    }

    @PostMapping("/findDjClassifyListByCondition")
    @ApiOperation(value = "根据条件查找党建公用类型表列表", notes = "返回党建公用类型表列表")
    public List<DjClassify> findDjClassifyListByCondition(
            @ApiParam(value = "党建公用类型表对象")
            @RequestBody DjClassify djClassify){
        return djClassifyService.findDjClassifyListByCondition(djClassify);
    }
    @PostMapping("/findDjClassifyCountByCondition")
    @ApiOperation(value = "根据条件查找党建公用类型表列表个数", notes = "返回党建公用类型表总个数")
    public long findDjClassifyCountByCondition(
            @ApiParam(value = "党建公用类型表对象")
            @RequestBody DjClassify djClassify){
        return djClassifyService.findDjClassifyCountByCondition(djClassify);
    }

    @PostMapping("/updateDjClassify")
    @ApiOperation(value = "修改党建公用类型表", notes = "党建公用类型表对象必传")
    public void updateDjClassify(
            @ApiParam(value = "党建公用类型表对象,对象属性不为空则修改", required = true)
            @RequestBody DjClassify djClassify){
        djClassifyService.updateDjClassify(djClassify);
    }

    @GetMapping("/deleteDjClassify/{id}")
    @ApiOperation(value = "通过id删除党建公用类型表")
    public void deleteDjClassify(
            @ApiParam(value = "党建公用类型表对象", required = true)
            @PathVariable String id){
        djClassifyService.deleteDjClassify(id);
    }
    @GetMapping("/deleteDjClassifyNotEnpty/{id}")
    @ApiOperation(value = "通过id删除党建公用类型表")
    public boolean deleteDjClassifyNotEnpty(
            @ApiParam(value = "党建公用类型表对象", required = true)
            @PathVariable String id){
        return djClassifyService.deleteDjClassifyNotEnpty(id);
    }
    @PostMapping("/deleteDjClassifyByCondition")
    @ApiOperation(value = "根据条件删除党建公用类型表")
    public void deleteDjClassifyByCondition(
            @ApiParam(value = "党建公用类型表对象")
            @RequestBody DjClassify djClassify){
        djClassifyService.deleteDjClassifyByCondition(djClassify);
    }
    @PostMapping("/findOneDjClassifyByCondition")
    @ApiOperation(value = "根据条件查找单个党建公用类型表,结果必须为单条数据", notes = "返回单个党建公用类型表,没有时为空")
    public DjClassify findOneDjClassifyByCondition(
            @ApiParam(value = "党建公用类型表对象")
            @RequestBody DjClassify djClassify){
        return djClassifyService.findOneDjClassifyByCondition(djClassify);
    }

    @PostMapping("/selectClassifyListByType")
    @ApiOperation(value = "根据类型查找党建公用类型下拉框", notes = "返回党建公用类型下拉框")
    public List<DjClassify> selectClassifyListByType(
            @ApiParam(value = "党建公用类型表对象")
            @RequestBody DjClassify djClassify){
        return djClassifyService.selectClassifyListByType(djClassify);
    }

    @PostMapping("/findClassifyListByType")
    @ApiOperation(value = "查询该类型下拉框是否已经存在", notes = "返回党建公用类型下拉框")
    public List<DjClassify> findClassifyListByType(
            @ApiParam(value = "党建公用类型表对象")
            @RequestBody DjClassify djClassify){
        return djClassifyService.findClassifyListByType(djClassify);
    }
}
