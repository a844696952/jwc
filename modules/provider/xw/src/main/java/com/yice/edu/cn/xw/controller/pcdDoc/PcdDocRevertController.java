package com.yice.edu.cn.xw.controller.pcdDoc;

import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRevert;
import com.yice.edu.cn.xw.service.pcdDoc.PcdDocRevertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pcdDocRevert")
@Api(value = "/pcdDocRevert",description = "公文回复模块")
public class PcdDocRevertController {
    @Autowired
    private PcdDocRevertService pcdDocRevertService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdDocRevertById/{id}")
    @ApiOperation(value = "通过id查找公文回复", notes = "返回公文回复对象")
    public PcdDocRevert findPcdDocRevertById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pcdDocRevertService.findPcdDocRevertById(id);
    }

    @PostMapping("/savePcdDocRevert")
    @ApiOperation(value = "保存公文回复", notes = "返回公文回复对象")
    public PcdDocRevert savePcdDocRevert(
            @ApiParam(value = "公文回复对象", required = true)
            @RequestBody PcdDocRevert pcdDocRevert){
        pcdDocRevertService.savePcdDocRevert(pcdDocRevert);
        return pcdDocRevert;
    }

    @PostMapping("/batchSavePcdDocRevert")
    @ApiOperation(value = "批量保存公文回复")
    public void batchSavePcdDocRevert(
            @ApiParam(value = "公文回复对象集合", required = true)
            @RequestBody List<PcdDocRevert> pcdDocReverts){
        pcdDocRevertService.batchSavePcdDocRevert(pcdDocReverts);
    }

    @PostMapping("/findPcdDocRevertListByCondition")
    @ApiOperation(value = "根据条件查找公文回复列表", notes = "返回公文回复列表")
    public List<PcdDocRevert> findPcdDocRevertListByCondition(
            @ApiParam(value = "公文回复对象")
            @RequestBody PcdDocRevert pcdDocRevert){
        return pcdDocRevertService.findPcdDocRevertListByCondition(pcdDocRevert);
    }
    @PostMapping("/findPcdDocRevertCountByCondition")
    @ApiOperation(value = "根据条件查找公文回复列表个数", notes = "返回公文回复总个数")
    public long findPcdDocRevertCountByCondition(
            @ApiParam(value = "公文回复对象")
            @RequestBody PcdDocRevert pcdDocRevert){
        return pcdDocRevertService.findPcdDocRevertCountByCondition(pcdDocRevert);
    }

    @PostMapping("/updatePcdDocRevert")
    @ApiOperation(value = "修改公文回复有值的字段", notes = "公文回复对象必传")
    public void updatePcdDocRevert(
            @ApiParam(value = "公文回复对象,对象属性不为空则修改", required = true)
            @RequestBody PcdDocRevert pcdDocRevert){
        pcdDocRevertService.updatePcdDocRevert(pcdDocRevert);
    }
    @PostMapping("/updatePcdDocRevertForAll")
    @ApiOperation(value = "修改公文回复所有字段", notes = "公文回复对象必传")
    public void updatePcdDocRevertForAll(
            @ApiParam(value = "公文回复对象", required = true)
            @RequestBody PcdDocRevert pcdDocRevert){
        pcdDocRevertService.updatePcdDocRevertForAll(pcdDocRevert);
    }

    @GetMapping("/deletePcdDocRevert/{id}")
    @ApiOperation(value = "通过id删除公文回复")
    public void deletePcdDocRevert(
            @ApiParam(value = "公文回复对象", required = true)
            @PathVariable String id){
        pcdDocRevertService.deletePcdDocRevert(id);
    }
    @PostMapping("/deletePcdDocRevertByCondition")
    @ApiOperation(value = "根据条件删除公文回复")
    public void deletePcdDocRevertByCondition(
            @ApiParam(value = "公文回复对象")
            @RequestBody PcdDocRevert pcdDocRevert){
        pcdDocRevertService.deletePcdDocRevertByCondition(pcdDocRevert);
    }
    @PostMapping("/findOnePcdDocRevertByCondition")
    @ApiOperation(value = "根据条件查找单个公文回复,结果必须为单条数据", notes = "返回单个公文回复,没有时为空")
    public PcdDocRevert findOnePcdDocRevertByCondition(
            @ApiParam(value = "公文回复对象")
            @RequestBody PcdDocRevert pcdDocRevert){
        return pcdDocRevertService.findOnePcdDocRevertByCondition(pcdDocRevert);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/savePcdDocRevertKong")
    public void savePcdDocRevertKong(@RequestBody PcdDocRevert pcdDocRevert){
         pcdDocRevertService.savePcdDocRevertKong(pcdDocRevert);
    }

    @PostMapping("/findAndUpdatePcdDocRevertById")
    public List<PcdDocRevert> findAndUpdatePcdDocRevertById(@RequestBody PcdDocRevert pcdDocRevert){
        return pcdDocRevertService.findAndUpdatePcdDocRevertById(pcdDocRevert);
    }

    @PostMapping("/findPcdDocRevertByPcdDocRevertListKong")
    public List<PcdDocRevert> findPcdDocRevertByPcdDocRevertListKong(@RequestBody PcdDocRevert pcdDocRevert){
        return pcdDocRevertService.findPcdDocRevertByPcdDocRevertListKong(pcdDocRevert);
    }

    @PostMapping("/findPcdDocRevertByPcdDocRevertLongKong")
    public long findPcdDocRevertByPcdDocRevertLongKong(@RequestBody PcdDocRevert pcdDocRevert){
        return pcdDocRevertService.findPcdDocRevertByPcdDocRevertLongKong(pcdDocRevert);
    }
}
