package com.yice.edu.cn.jy.controller.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTitleQuota;
import com.yice.edu.cn.jy.service.titleQuota.HistoryTitleQuotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historyTitleQuota")
@Api(value = "/historyTitleQuota",description = "题目额度资源操作记录表模块")
public class HistoryTitleQuotaController {
    @Autowired
    private HistoryTitleQuotaService historyTitleQuotaService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findHistoryTitleQuotaById/{id}")
    @ApiOperation(value = "通过id查找题目额度资源操作记录表", notes = "返回题目额度资源操作记录表对象")
    public HistoryTitleQuota findHistoryTitleQuotaById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return historyTitleQuotaService.findHistoryTitleQuotaById(id);
    }

    @PostMapping("/saveHistoryTitleQuota")
    @ApiOperation(value = "保存题目额度资源操作记录表", notes = "返回题目额度资源操作记录表对象")
    public HistoryTitleQuota saveHistoryTitleQuota(
            @ApiParam(value = "题目额度资源操作记录表对象", required = true)
            @RequestBody HistoryTitleQuota historyTitleQuota){
        historyTitleQuotaService.saveHistoryTitleQuota(historyTitleQuota);
        return historyTitleQuota;
    }

    @PostMapping("/batchSaveHistoryTitleQuota")
    @ApiOperation(value = "批量保存题目额度资源操作记录表")
    public void batchSaveHistoryTitleQuota(
            @ApiParam(value = "题目额度资源操作记录表对象集合", required = true)
            @RequestBody List<HistoryTitleQuota> historyTitleQuotas){
        historyTitleQuotaService.batchSaveHistoryTitleQuota(historyTitleQuotas);
    }

    @PostMapping("/findHistoryTitleQuotaListByCondition")
    @ApiOperation(value = "根据条件查找题目额度资源操作记录表列表", notes = "返回题目额度资源操作记录表列表")
    public List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition(
            @ApiParam(value = "题目额度资源操作记录表对象")
            @RequestBody HistoryTitleQuota historyTitleQuota){
        return historyTitleQuotaService.findHistoryTitleQuotaListByCondition(historyTitleQuota);
    }
    @PostMapping("/findHistoryTitleQuotaCountByCondition")
    @ApiOperation(value = "根据条件查找题目额度资源操作记录表列表个数", notes = "返回题目额度资源操作记录表总个数")
    public long findHistoryTitleQuotaCountByCondition(
            @ApiParam(value = "题目额度资源操作记录表对象")
            @RequestBody HistoryTitleQuota historyTitleQuota){
        return historyTitleQuotaService.findHistoryTitleQuotaCountByCondition(historyTitleQuota);
    }

    @PostMapping("/updateHistoryTitleQuota")
    @ApiOperation(value = "修改题目额度资源操作记录表有值的字段", notes = "题目额度资源操作记录表对象必传")
    public void updateHistoryTitleQuota(
            @ApiParam(value = "题目额度资源操作记录表对象,对象属性不为空则修改", required = true)
            @RequestBody HistoryTitleQuota historyTitleQuota){
        historyTitleQuotaService.updateHistoryTitleQuota(historyTitleQuota);
    }
    @PostMapping("/updateHistoryTitleQuotaForAll")
    @ApiOperation(value = "修改题目额度资源操作记录表所有字段", notes = "题目额度资源操作记录表对象必传")
    public void updateHistoryTitleQuotaForAll(
            @ApiParam(value = "题目额度资源操作记录表对象", required = true)
            @RequestBody HistoryTitleQuota historyTitleQuota){
        historyTitleQuotaService.updateHistoryTitleQuotaForAll(historyTitleQuota);
    }

    @GetMapping("/deleteHistoryTitleQuota/{id}")
    @ApiOperation(value = "通过id删除题目额度资源操作记录表")
    public void deleteHistoryTitleQuota(
            @ApiParam(value = "题目额度资源操作记录表对象", required = true)
            @PathVariable String id){
        historyTitleQuotaService.deleteHistoryTitleQuota(id);
    }
    @PostMapping("/deleteHistoryTitleQuotaByCondition")
    @ApiOperation(value = "根据条件删除题目额度资源操作记录表")
    public void deleteHistoryTitleQuotaByCondition(
            @ApiParam(value = "题目额度资源操作记录表对象")
            @RequestBody HistoryTitleQuota historyTitleQuota){
        historyTitleQuotaService.deleteHistoryTitleQuotaByCondition(historyTitleQuota);
    }
    @PostMapping("/findOneHistoryTitleQuotaByCondition")
    @ApiOperation(value = "根据条件查找单个题目额度资源操作记录表,结果必须为单条数据", notes = "返回单个题目额度资源操作记录表,没有时为空")
    public HistoryTitleQuota findOneHistoryTitleQuotaByCondition(
            @ApiParam(value = "题目额度资源操作记录表对象")
            @RequestBody HistoryTitleQuota historyTitleQuota){
        return historyTitleQuotaService.findOneHistoryTitleQuotaByCondition(historyTitleQuota);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/findHistoryTitleQuotaListByCondition4Like")
    @ApiOperation(value = "根据条件查找题目额度资源操作记录表列表", notes = "返回题目额度资源操作记录表列表")
    public List<HistoryTitleQuota> findHistoryTitleQuotaListByCondition4Like(
            @ApiParam(value = "题目额度资源操作记录表对象")
            @RequestBody HistoryTitleQuota historyTitleQuota){
        return historyTitleQuotaService.findHistoryTitleQuotaListByCondition4Like(historyTitleQuota);
    }


    @PostMapping("/findHistoryTitleQuotasByConditionNoSchoolId")
    @ApiOperation(value = "根据条件查找题目额度资源操作记录表列表", notes = "返回题目额度资源操作记录表列表")
    public List<HistoryTitleQuota> findHistoryTitleQuotasByConditionNoSchoolId(
            @ApiParam(value = "题目额度资源操作记录表对象")
            @RequestBody HistoryTitleQuota historyTitleQuota){
        return historyTitleQuotaService.findHistoryTitleQuotasByConditionNoSchoolId(historyTitleQuota);
    }
}
