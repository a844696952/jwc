package com.yice.edu.cn.xw.controller.pcdDoc;

import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDoc;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdDocRevert;
import com.yice.edu.cn.common.pojo.xw.pcdDoc.PcdSend;
import com.yice.edu.cn.xw.service.pcdDoc.PcdSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pcdSend")
@Api(value = "/pcdSend",description = "区公文发送对象公文表模块")
public class PcdSendController {
    @Autowired
    private PcdSendService pcdSendService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPcdSendById/{id}")
    @ApiOperation(value = "通过id查找区公文发送对象公文表", notes = "返回区公文发送对象公文表对象")
    public PcdSend findPcdSendById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pcdSendService.findPcdSendById(id);
    }

    @PostMapping("/savePcdSend")
    @ApiOperation(value = "保存区公文发送对象公文表", notes = "返回区公文发送对象公文表对象")
    public PcdSend savePcdSend(
            @ApiParam(value = "区公文发送对象公文表对象", required = true)
            @RequestBody PcdSend pcdSend){
        pcdSendService.savePcdSend(pcdSend);
        return pcdSend;
    }

    @PostMapping("/batchSavePcdSend")
    @ApiOperation(value = "批量保存区公文发送对象公文表")
    public void batchSavePcdSend(
            @ApiParam(value = "区公文发送对象公文表对象集合", required = true)
            @RequestBody List<PcdSend> pcdSends){
        pcdSendService.batchSavePcdSend(pcdSends);
    }

    @PostMapping("/findPcdSendListByCondition")
    @ApiOperation(value = "根据条件查找区公文发送对象公文表列表", notes = "返回区公文发送对象公文表列表")
    public List<PcdSend> findPcdSendListByCondition(
            @ApiParam(value = "区公文发送对象公文表对象")
            @RequestBody PcdSend pcdSend){
        return pcdSendService.findPcdSendListByCondition(pcdSend);
    }
    @PostMapping("/findPcdSendCountByCondition")
    @ApiOperation(value = "根据条件查找区公文发送对象公文表列表个数", notes = "返回区公文发送对象公文表总个数")
    public long findPcdSendCountByCondition(
            @ApiParam(value = "区公文发送对象公文表对象")
            @RequestBody PcdSend pcdSend){
        return pcdSendService.findPcdSendCountByCondition(pcdSend);
    }

    @PostMapping("/updatePcdSend")
    @ApiOperation(value = "修改区公文发送对象公文表有值的字段", notes = "区公文发送对象公文表对象必传")
    public void updatePcdSend(
            @ApiParam(value = "区公文发送对象公文表对象,对象属性不为空则修改", required = true)
            @RequestBody PcdSend pcdSend){
        pcdSendService.updatePcdSend(pcdSend);
    }
    @PostMapping("/updatePcdSendForAll")
    @ApiOperation(value = "修改区公文发送对象公文表所有字段", notes = "区公文发送对象公文表对象必传")
    public void updatePcdSendForAll(
            @ApiParam(value = "区公文发送对象公文表对象", required = true)
            @RequestBody PcdSend pcdSend){
        pcdSendService.updatePcdSendForAll(pcdSend);
    }

    @GetMapping("/deletePcdSend/{id}")
    @ApiOperation(value = "通过id删除区公文发送对象公文表")
    public void deletePcdSend(
            @ApiParam(value = "区公文发送对象公文表对象", required = true)
            @PathVariable String id){
        pcdSendService.deletePcdSend(id);
    }
    @PostMapping("/deletePcdSendByCondition")
    @ApiOperation(value = "根据条件删除区公文发送对象公文表")
    public void deletePcdSendByCondition(
            @ApiParam(value = "区公文发送对象公文表对象")
            @RequestBody PcdSend pcdSend){
        pcdSendService.deletePcdSendByCondition(pcdSend);
    }
    @PostMapping("/findOnePcdSendByCondition")
    @ApiOperation(value = "根据条件查找单个区公文发送对象公文表,结果必须为单条数据", notes = "返回单个区公文发送对象公文表,没有时为空")
    public PcdSend findOnePcdSendByCondition(
            @ApiParam(value = "区公文发送对象公文表对象")
            @RequestBody PcdSend pcdSend){
        return pcdSendService.findOnePcdSendByCondition(pcdSend);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/findOnePcdSendByPcdSend")
    public PcdDoc findOnePcdSendByPcdSend(@RequestBody PcdDocRevert pcdDocRevert){
        return pcdSendService.findOnePcdSendByPcdSend(pcdDocRevert);
    }

    @PostMapping("/findPcdSendByListKong")
    public List<PcdSend> findPcdSendByListKong(@RequestBody PcdSend pcdSend){
        return pcdSendService.findPcdSendByListKong(pcdSend);
    }

    @PostMapping("/findPcdSendByCountKong")
    public long findPcdSendByCountKong(@RequestBody PcdSend pcdSend){
        return pcdSendService.findPcdSendByCountKong(pcdSend);
    }
}
