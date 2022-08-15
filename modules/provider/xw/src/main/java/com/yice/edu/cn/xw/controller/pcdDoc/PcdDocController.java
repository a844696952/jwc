package com.yice.edu.cn.xw.controller.pcdDoc;

import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import com.yice.edu.cn.xw.service.pcdDoc.PcdDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pcdDoc")
@Api(value = "/pcdDoc",description = "区级公文模块")
public class PcdDocController {
    @Autowired
    private PcdDocService pcdDocService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdDocById/{id}")
    @ApiOperation(value = "通过id查找区级公文", notes = "返回区级公文对象")
    public PcdDoc findPcdDocById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pcdDocService.findPcdDocById(id);
    }

    @PostMapping("/savePcdDoc")
    @ApiOperation(value = "保存区级公文", notes = "返回区级公文对象")
    public PcdDoc savePcdDoc(
            @ApiParam(value = "区级公文对象", required = true)
            @RequestBody PcdDoc pcdDoc){
        pcdDocService.savePcdDoc(pcdDoc);
        return pcdDoc;
    }

    @PostMapping("/batchSavePcdDoc")
    @ApiOperation(value = "批量保存区级公文")
    public void batchSavePcdDoc(
            @ApiParam(value = "区级公文对象集合", required = true)
            @RequestBody List<PcdDoc> pcdDocs){
        pcdDocService.batchSavePcdDoc(pcdDocs);
    }

    @PostMapping("/findPcdDocListByCondition")
    @ApiOperation(value = "根据条件查找区级公文列表", notes = "返回区级公文列表")
    public List<PcdDoc> findPcdDocListByCondition(
            @ApiParam(value = "区级公文对象")
            @RequestBody PcdDoc pcdDoc){
        return pcdDocService.findPcdDocListByCondition(pcdDoc);
    }
    @PostMapping("/findPcdDocCountByCondition")
    @ApiOperation(value = "根据条件查找区级公文列表个数", notes = "返回区级公文总个数")
    public long findPcdDocCountByCondition(
            @ApiParam(value = "区级公文对象")
            @RequestBody PcdDoc pcdDoc){
        return pcdDocService.findPcdDocCountByCondition(pcdDoc);
    }

    @PostMapping("/updatePcdDoc")
    @ApiOperation(value = "修改区级公文有值的字段", notes = "区级公文对象必传")
    public void updatePcdDoc(
            @ApiParam(value = "区级公文对象,对象属性不为空则修改", required = true)
            @RequestBody PcdDoc pcdDoc){
        pcdDocService.updatePcdDoc(pcdDoc);
    }
    @PostMapping("/updatePcdDocForAll")
    @ApiOperation(value = "修改区级公文所有字段", notes = "区级公文对象必传")
    public void updatePcdDocForAll(
            @ApiParam(value = "区级公文对象", required = true)
            @RequestBody PcdDoc pcdDoc){
        pcdDocService.updatePcdDocForAll(pcdDoc);
    }

    @GetMapping("/deletePcdDoc/{id}")
    @ApiOperation(value = "通过id删除区级公文")
    public void deletePcdDoc(
            @ApiParam(value = "区级公文对象", required = true)
            @PathVariable String id){
        pcdDocService.deletePcdDoc(id);
    }
    @PostMapping("/deletePcdDocByCondition")
    @ApiOperation(value = "根据条件删除区级公文")
    public void deletePcdDocByCondition(
            @ApiParam(value = "区级公文对象")
            @RequestBody PcdDoc pcdDoc){
        pcdDocService.deletePcdDocByCondition(pcdDoc);
    }
    @PostMapping("/findOnePcdDocByCondition")
    @ApiOperation(value = "根据条件查找单个区级公文,结果必须为单条数据", notes = "返回单个区级公文,没有时为空")
    public PcdDoc findOnePcdDocByCondition(
            @ApiParam(value = "区级公文对象")
            @RequestBody PcdDoc pcdDoc){
        return pcdDocService.findOnePcdDocByCondition(pcdDoc);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findListPcdDocByPcdDocKong")
    public List<PcdDoc> findListPcdDocByPcdDocKong(@RequestBody PcdDoc pcdDoc){
        return pcdDocService.findListPcdDocByPcdDocKong(pcdDoc);
    }

    @PostMapping("/findCountPcdDocByPcdDocKong")
    public long findCountPcdDocByPcdDocKong(@RequestBody PcdDoc pcdDoc){
        return pcdDocService.findCountPcdDocByPcdDocKong(pcdDoc);
    }

    @PostMapping("/savePcdDocKong")
    public void savePcdDocKong(@RequestBody PcdDoc pcdDoc){
        pcdDocService.savePcdDocKong(pcdDoc);
    }

    @PostMapping("/saveForwardDoc")
    @ApiOperation(value = "保存区级公文", notes = "返回区级公文对象")
    public PcdDoc saveForwardDoc(
            @ApiParam(value = "区级公文对象", required = true)
            @RequestBody PcdDoc pcdDoc){
        pcdDocService.saveForwardDoc(pcdDoc);
        return pcdDoc;
    }
}
