package com.yice.edu.cn.jy.controller.knowledgePoint;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.jy.service.knowledgePoint.KnowledgePointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/knowledgePoint")
@Api(value = "/knowledgePoint",description = "知识点表模块")
public class KnowledgePointController {
    @Autowired
    private KnowledgePointService knowledgePointService;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findKnowledgePointById/{id}")
    @ApiOperation(value = "通过id查找知识点表", notes = "返回知识点表对象")
    public KnowledgePoint findKnowledgePointById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return knowledgePointService.findKnowledgePointById(id);
    }

    @PostMapping("/saveKnowledgePoint")
    @ApiOperation(value = "保存知识点表", notes = "返回知识点表对象")
    public KnowledgePoint saveKnowledgePoint(
            @ApiParam(value = "知识点表对象", required = true)
            @RequestBody KnowledgePoint knowledgePoint){
        knowledgePointService.saveKnowledgePoint(knowledgePoint);
        return knowledgePoint;
    }

    @PostMapping("/findKnowledgePointListByCondition")
    @ApiOperation(value = "根据条件查找知识点表列表", notes = "返回知识点表列表")
    public List<KnowledgePoint> findKnowledgePointListByCondition(
            @ApiParam(value = "知识点表对象")
            @RequestBody KnowledgePoint knowledgePoint){
        return knowledgePointService.findKnowledgePointListByCondition(knowledgePoint);
    }
    @PostMapping("/findKnowledgePointCountByCondition")
    @ApiOperation(value = "根据条件查找知识点表列表个数", notes = "返回知识点表总个数")
    public long findKnowledgePointCountByCondition(
            @ApiParam(value = "知识点表对象")
            @RequestBody KnowledgePoint knowledgePoint){
        return knowledgePointService.findKnowledgePointCountByCondition(knowledgePoint);
    }

    @PostMapping("/updateKnowledgePoint")
    @ApiOperation(value = "修改知识点表有值的字段", notes = "知识点表对象必传")
    public void updateKnowledgePoint(
            @ApiParam(value = "知识点表对象,对象属性不为空则修改", required = true)
            @RequestBody KnowledgePoint knowledgePoint){
        knowledgePointService.updateKnowledgePoint(knowledgePoint);
    }
    @PostMapping("/updateKnowledgePointForAll")
    @ApiOperation(value = "修改知识点表所有字段", notes = "知识点表对象必传")
    public void updateKnowledgePointForAll(
            @ApiParam(value = "知识点表对象", required = true)
            @RequestBody KnowledgePoint knowledgePoint){
        knowledgePointService.updateKnowledgePointForAll(knowledgePoint);
    }

    @GetMapping("/deleteKnowledgePoint/{id}")
    @ApiOperation(value = "通过id删除知识点表")
    public void deleteKnowledgePoint(
            @ApiParam(value = "知识点表对象", required = true)
            @PathVariable String id){
        knowledgePointService.deleteKnowledgePoint(id);
    }
    @PostMapping("/findOneKnowledgePointByCondition")
    @ApiOperation(value = "根据条件查找单个知识点表,结果必须为单条数据", notes = "返回单个知识点表,没有时为空")
    public KnowledgePoint findOneKnowledgePointByCondition(
            @ApiParam(value = "知识点表对象")
            @RequestBody KnowledgePoint knowledgePoint){
        return knowledgePointService.findOneKnowledgePointByCondition(knowledgePoint);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/uploadKnowledgePoint")
    @ApiOperation(value = "批量导入知识点", notes = "返回成功或者异常提示")
    public Map<String,Object> uploadKnowledgePoint(@RequestBody List<KnowledgePoint> knowledgePoints){
        return knowledgePointService.uploadKnowledgePoint(knowledgePoints);
    }
    @PostMapping("/batchSaveKnowledgePoint")
    @ApiOperation(value = "批量添加知识点")
    public void batchSaveKnowledgePoint(@RequestBody List<KnowledgePoint> knowledgePoints){
        knowledgePointService.batchSaveKnowledgePoint(knowledgePoints);
    }
}
