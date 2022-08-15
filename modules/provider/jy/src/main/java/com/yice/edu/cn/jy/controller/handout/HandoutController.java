package com.yice.edu.cn.jy.controller.handout;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.handout.Handout;
import com.yice.edu.cn.jy.service.handout.HandoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/handout")
@Api(value = "/handout",description = "模块")
public class HandoutController {
    @Autowired
    private HandoutService handoutService;

    @GetMapping("/findHandoutById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Handout findHandoutById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return handoutService.findHandoutById(id);
    }

    @PostMapping("/saveHandout")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Handout saveHandout(
            @ApiParam(value = "对象", required = true)
            @RequestBody Handout handout){
        handoutService.saveHandout(handout);
        return handout;
    }

    @PostMapping("/findHandoutListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Handout> findHandoutListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Handout handout){
        return handoutService.findHandoutListByCondition(handout);
    }
    @PostMapping("/findHandoutCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findHandoutCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Handout handout){
        return handoutService.findHandoutCountByCondition(handout);
    }

    @PostMapping("/updateHandout")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateHandout(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Handout handout){
        handoutService.updateHandout(handout);
    }

    @GetMapping("/deleteHandout/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteHandout(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        handoutService.deleteHandout(id);
    }
    @PostMapping("/deleteHandoutByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteHandoutByCondition(
            @ApiParam(value = "对象")
            @RequestBody Handout handout){
        handoutService.deleteHandoutByCondition(handout);
    }
    @PostMapping("/findOneHandoutByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Handout findOneHandoutByCondition(
            @ApiParam(value = "对象")
            @RequestBody Handout handout){
        return handoutService.findOneHandoutByCondition(handout);
    }
    @PostMapping("/findHandoutsByCondition2")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Handout> findHandoutsByCondition2(
            @ApiParam(value = "对象")
            @RequestBody Handout handout){
        return handoutService.findHandoutsByCondition2(handout);
    }
    @PostMapping("/findHandoutCountByCondition2")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findHandoutCountByCondition2(
            @ApiParam(value = "对象")
            @RequestBody Handout handout){
        return handoutService.findHandoutCountByCondition2(handout);
    }


}
