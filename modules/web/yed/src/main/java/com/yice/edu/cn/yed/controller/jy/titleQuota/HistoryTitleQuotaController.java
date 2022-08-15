package com.yice.edu.cn.yed.controller.jy.titleQuota;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTitleQuota;
import com.yice.edu.cn.yed.service.jy.titleQuota.HistoryTitleQuotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historyTitleQuota")
@Api(value = "/historyTitleQuota",description = "题目额度资源操作记录表模块")
public class HistoryTitleQuotaController {
    @Autowired
    private HistoryTitleQuotaService historyTitleQuotaService;

    @PostMapping("/saveHistoryTitleQuota")
    @ApiOperation(value = "保存题目额度资源操作记录表对象", notes = "返回保存好的题目额度资源操作记录表对象", response=HistoryTitleQuota.class)
    public ResponseJson saveHistoryTitleQuota(
            @ApiParam(value = "题目额度资源操作记录表对象", required = true)
            @RequestBody HistoryTitleQuota historyTitleQuota){
        HistoryTitleQuota s=historyTitleQuotaService.saveHistoryTitleQuota(historyTitleQuota);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findHistoryTitleQuotaById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找题目额度资源操作记录表", notes = "返回响应对象", response=HistoryTitleQuota.class)
    public ResponseJson findHistoryTitleQuotaById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        HistoryTitleQuota historyTitleQuota=historyTitleQuotaService.findHistoryTitleQuotaById(id);
        return new ResponseJson(historyTitleQuota);
    }

    @PostMapping("/update/updateHistoryTitleQuota")
    @ApiOperation(value = "修改题目额度资源操作记录表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateHistoryTitleQuota(
            @ApiParam(value = "被修改的题目额度资源操作记录表对象,对象属性不为空则修改", required = true)
            @RequestBody HistoryTitleQuota historyTitleQuota){
        historyTitleQuotaService.updateHistoryTitleQuota(historyTitleQuota);
        return new ResponseJson();
    }

    @PostMapping("/update/updateHistoryTitleQuotaForAll")
    @ApiOperation(value = "修改题目额度资源操作记录表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateHistoryTitleQuotaForAll(
            @ApiParam(value = "被修改的题目额度资源操作记录表对象", required = true)
            @RequestBody HistoryTitleQuota historyTitleQuota){
        historyTitleQuotaService.updateHistoryTitleQuotaForAll(historyTitleQuota);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHistoryTitleQuotaById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找题目额度资源操作记录表", notes = "返回响应对象", response=HistoryTitleQuota.class)
    public ResponseJson lookHistoryTitleQuotaById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        HistoryTitleQuota historyTitleQuota=historyTitleQuotaService.findHistoryTitleQuotaById(id);
        return new ResponseJson(historyTitleQuota);
    }

    @PostMapping("/findHistoryTitleQuotasByCondition")
    @ApiOperation(value = "根据条件查找题目额度资源操作记录表", notes = "返回响应对象", response=HistoryTitleQuota.class)
    public ResponseJson findHistoryTitleQuotasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HistoryTitleQuota historyTitleQuota){
        List<HistoryTitleQuota> data=historyTitleQuotaService.findHistoryTitleQuotaListByCondition4Like(historyTitleQuota);
        historyTitleQuota.setPager(null);
        List<HistoryTitleQuota> data1=historyTitleQuotaService.findHistoryTitleQuotaListByCondition4Like(historyTitleQuota);
        long count= 0;
        if(data1!=null){
            count = data1.size();
        }
        return new ResponseJson(data,count);
    }

    @PostMapping("/findHistoryTitleQuotasByConditionNoSchoolId")
    @ApiOperation(value = "根据条件查找题目额度资源操作记录表", notes = "返回响应对象", response=HistoryTitleQuota.class)
    public ResponseJson findHistoryTitleQuotasByConditionNoSchoolId(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HistoryTitleQuota historyTitleQuota){
        List<HistoryTitleQuota> data=historyTitleQuotaService.findHistoryTitleQuotasByConditionNoSchoolId(historyTitleQuota);
        long count= 0;
        if(data!=null){
            count = data.size();
        }
        return new ResponseJson(data,count);
    }

    @PostMapping("/findOneHistoryTitleQuotaByCondition")
    @ApiOperation(value = "根据条件查找单个题目额度资源操作记录表,结果必须为单条数据", notes = "没有时返回空", response=HistoryTitleQuota.class)
    public ResponseJson findOneHistoryTitleQuotaByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody HistoryTitleQuota historyTitleQuota){
        HistoryTitleQuota one=historyTitleQuotaService.findOneHistoryTitleQuotaByCondition(historyTitleQuota);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteHistoryTitleQuota/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHistoryTitleQuota(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        historyTitleQuotaService.deleteHistoryTitleQuota(id);
        return new ResponseJson();
    }


    @PostMapping("/findHistoryTitleQuotaListByCondition")
    @ApiOperation(value = "根据条件查找题目额度资源操作记录表列表", notes = "返回响应对象,不包含总条数", response=HistoryTitleQuota.class)
    public ResponseJson findHistoryTitleQuotaListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HistoryTitleQuota historyTitleQuota){
        List<HistoryTitleQuota> data=historyTitleQuotaService.findHistoryTitleQuotaListByCondition(historyTitleQuota);
        return new ResponseJson(data);
    }



}
