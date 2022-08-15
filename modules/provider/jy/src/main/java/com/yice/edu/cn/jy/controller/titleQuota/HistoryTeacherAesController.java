package com.yice.edu.cn.jy.controller.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTeacherAes;
import com.yice.edu.cn.jy.service.titleQuota.HistoryTeacherAesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/historyTeacherAes")
@Api(value = "/historyTeacherAes",description = "题目额度教师使用记录表模块")
public class HistoryTeacherAesController {
    @Autowired
    private HistoryTeacherAesService historyTeacherAesService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findHistoryTeacherAesById/{id}")
    @ApiOperation(value = "通过id查找题目额度教师使用记录表", notes = "返回题目额度教师使用记录表对象")
    public HistoryTeacherAes findHistoryTeacherAesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return historyTeacherAesService.findHistoryTeacherAesById(id);
    }

    @PostMapping("/saveHistoryTeacherAes")
    @ApiOperation(value = "保存题目额度教师使用记录表", notes = "返回题目额度教师使用记录表对象")
    public HistoryTeacherAes saveHistoryTeacherAes(
            @ApiParam(value = "题目额度教师使用记录表对象", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAesService.saveHistoryTeacherAes(historyTeacherAes);
        return historyTeacherAes;
    }

    @PostMapping("/batchSaveHistoryTeacherAes")
    @ApiOperation(value = "批量保存题目额度教师使用记录表")
    public void batchSaveHistoryTeacherAes(
            @ApiParam(value = "题目额度教师使用记录表对象集合", required = true)
            @RequestBody List<HistoryTeacherAes> historyTeacherAess){
        historyTeacherAesService.batchSaveHistoryTeacherAes(historyTeacherAess);
    }

    @PostMapping("/findHistoryTeacherAesListByCondition")
    @ApiOperation(value = "根据条件查找题目额度教师使用记录表列表", notes = "返回题目额度教师使用记录表列表")
    public List<HistoryTeacherAes> findHistoryTeacherAesListByCondition(
            @ApiParam(value = "题目额度教师使用记录表对象")
            @RequestBody HistoryTeacherAes historyTeacherAes){
        return historyTeacherAesService.findHistoryTeacherAesListByCondition(historyTeacherAes);
    }
    @PostMapping("/findHistoryTeacherAesCountByCondition")
    @ApiOperation(value = "根据条件查找题目额度教师使用记录表列表个数", notes = "返回题目额度教师使用记录表总个数")
    public long findHistoryTeacherAesCountByCondition(
            @ApiParam(value = "题目额度教师使用记录表对象")
            @RequestBody HistoryTeacherAes historyTeacherAes){
        return historyTeacherAesService.findHistoryTeacherAesCountByCondition(historyTeacherAes);
    }

    @PostMapping("/updateHistoryTeacherAes")
    @ApiOperation(value = "修改题目额度教师使用记录表有值的字段", notes = "题目额度教师使用记录表对象必传")
    public void updateHistoryTeacherAes(
            @ApiParam(value = "题目额度教师使用记录表对象,对象属性不为空则修改", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAesService.updateHistoryTeacherAes(historyTeacherAes);
    }
    @PostMapping("/updateHistoryTeacherAesForAll")
    @ApiOperation(value = "修改题目额度教师使用记录表所有字段", notes = "题目额度教师使用记录表对象必传")
    public void updateHistoryTeacherAesForAll(
            @ApiParam(value = "题目额度教师使用记录表对象", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAesService.updateHistoryTeacherAesForAll(historyTeacherAes);
    }

    @GetMapping("/deleteHistoryTeacherAes/{id}")
    @ApiOperation(value = "通过id删除题目额度教师使用记录表")
    public void deleteHistoryTeacherAes(
            @ApiParam(value = "题目额度教师使用记录表对象", required = true)
            @PathVariable String id){
        historyTeacherAesService.deleteHistoryTeacherAes(id);
    }
    @PostMapping("/deleteHistoryTeacherAesByCondition")
    @ApiOperation(value = "根据条件删除题目额度教师使用记录表")
    public void deleteHistoryTeacherAesByCondition(
            @ApiParam(value = "题目额度教师使用记录表对象")
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAesService.deleteHistoryTeacherAesByCondition(historyTeacherAes);
    }
    @PostMapping("/findOneHistoryTeacherAesByCondition")
    @ApiOperation(value = "根据条件查找单个题目额度教师使用记录表,结果必须为单条数据", notes = "返回单个题目额度教师使用记录表,没有时为空")
    public HistoryTeacherAes findOneHistoryTeacherAesByCondition(
            @ApiParam(value = "题目额度教师使用记录表对象")
            @RequestBody HistoryTeacherAes historyTeacherAes){
        return historyTeacherAesService.findOneHistoryTeacherAesByCondition(historyTeacherAes);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/findIsExist")
    @ApiOperation(value = "查询题目额度教师使用记录表是否存在记录topicId,schoolId", notes = "返回题目额度教师使用记录表对象")
    public HistoryTeacherAes findIsExist(
            @ApiParam(value = "题目额度教师使用记录表对象", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAes =historyTeacherAesService.findIsExist(historyTeacherAes);
        return historyTeacherAes;
    }


    @PostMapping("/findIsDownload")
    @ApiOperation(value = "能否继续下载题", notes = "返回题目额度教师使用记录表对象")
    public HistoryTeacherAes findIsDownload(
            @ApiParam(value = "题目额度教师使用记录表对象topicId,schoolId,teacherId", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAes =historyTeacherAesService.findIsDownload(historyTeacherAes);
        return historyTeacherAes;
    }


    @PostMapping("/findByTeacherIdVist")
    @ApiOperation(value = "老师每次访问+1处理", notes = "返回题目额度教师使用记录表对象")
    public HistoryTeacherAes findByTeacherIdVist(
            @ApiParam(value = "题目额度教师使用记录表对象topicId,schoolId,teacherId", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAes =historyTeacherAesService.findByTeacherIdVist(historyTeacherAes);
        return historyTeacherAes;
    }

    @PostMapping("/findHistoryTeacherAesCountByCondition4Like")
    public List<HistoryTeacherAes> findHistoryTeacherAesCountByCondition4Like( @RequestBody HistoryTeacherAes historyTeacherAes) {
        return  historyTeacherAesService.findHistoryTeacherAesCountByCondition4Like(historyTeacherAes);
    }

    @PostMapping("/findHistoryTeacherAesListByCondition4Like")
    public List<Map<String, Object>> findHistoryTeacherAesListByCondition4Like(@RequestBody HistoryTeacherAes historyTeacherAes) {
        return historyTeacherAesService.findHistoryTeacherAesListByCondition4Like(historyTeacherAes);
    }

}
