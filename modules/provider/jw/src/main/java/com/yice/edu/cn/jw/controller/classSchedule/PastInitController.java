package com.yice.edu.cn.jw.controller.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.PastInit;
import com.yice.edu.cn.jw.service.classSchedule.PastInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pastInit")
@Api(value = "/pastInit",description = "往期课表的节数模块")
public class PastInitController {
    @Autowired
    private PastInitService pastInitService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findPastInitById/{id}")
    @ApiOperation(value = "通过id查找往期课表的节数", notes = "返回往期课表的节数对象")
    public PastInit findPastInitById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return pastInitService.findPastInitById(id);
    }

    @PostMapping("/savePastInit")
    @ApiOperation(value = "保存往期课表的节数", notes = "返回往期课表的节数对象")
    public PastInit savePastInit(
            @ApiParam(value = "往期课表的节数对象", required = true)
            @RequestBody PastInit pastInit){
        pastInitService.savePastInit(pastInit);
        return pastInit;
    }

    @PostMapping("/batchSavePastInit")
    @ApiOperation(value = "批量保存往期课表的节数")
    public void batchSavePastInit(
            @ApiParam(value = "往期课表的节数对象集合", required = true)
            @RequestBody List<PastInit> pastInits){
        pastInitService.batchSavePastInit(pastInits);
    }

    @PostMapping("/findPastInitListByCondition")
    @ApiOperation(value = "根据条件查找往期课表的节数列表", notes = "返回往期课表的节数列表")
    public List<PastInit> findPastInitListByCondition(
            @ApiParam(value = "往期课表的节数对象")
            @RequestBody PastInit pastInit){
        return pastInitService.findPastInitListByCondition(pastInit);
    }
    @PostMapping("/findPastInitCountByCondition")
    @ApiOperation(value = "根据条件查找往期课表的节数列表个数", notes = "返回往期课表的节数总个数")
    public long findPastInitCountByCondition(
            @ApiParam(value = "往期课表的节数对象")
            @RequestBody PastInit pastInit){
        return pastInitService.findPastInitCountByCondition(pastInit);
    }

    @PostMapping("/updatePastInit")
    @ApiOperation(value = "修改往期课表的节数有值的字段", notes = "往期课表的节数对象必传")
    public void updatePastInit(
            @ApiParam(value = "往期课表的节数对象,对象属性不为空则修改", required = true)
            @RequestBody PastInit pastInit){
        pastInitService.updatePastInit(pastInit);
    }
    @PostMapping("/updatePastInitForAll")
    @ApiOperation(value = "修改往期课表的节数所有字段", notes = "往期课表的节数对象必传")
    public void updatePastInitForAll(
            @ApiParam(value = "往期课表的节数对象", required = true)
            @RequestBody PastInit pastInit){
        pastInitService.updatePastInitForAll(pastInit);
    }

    @GetMapping("/deletePastInit/{id}")
    @ApiOperation(value = "通过id删除往期课表的节数")
    public void deletePastInit(
            @ApiParam(value = "往期课表的节数对象", required = true)
            @PathVariable String id){
        pastInitService.deletePastInit(id);
    }
    @PostMapping("/deletePastInitByCondition")
    @ApiOperation(value = "根据条件删除往期课表的节数")
    public void deletePastInitByCondition(
            @ApiParam(value = "往期课表的节数对象")
            @RequestBody PastInit pastInit){
        pastInitService.deletePastInitByCondition(pastInit);
    }
    @PostMapping("/findOnePastInitByCondition")
    @ApiOperation(value = "根据条件查找单个往期课表的节数,结果必须为单条数据", notes = "返回单个往期课表的节数,没有时为空")
    public PastInit findOnePastInitByCondition(
            @ApiParam(value = "往期课表的节数对象")
            @RequestBody PastInit pastInit){
        return pastInitService.findOnePastInitByCondition(pastInit);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

}
