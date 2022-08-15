package com.yice.edu.cn.jw.controller.eduEvaluation;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQuality;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import com.yice.edu.cn.jw.service.eduEvaluation.CompareQualityDetailService;
import com.yice.edu.cn.jw.service.eduEvaluation.CompareQualityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/compareQuality")
@Api(value = "/compareQuality",description = "综合素质表模块")
public class CompareQualityController {
    @Autowired
    private CompareQualityService compareQualityService;
    @Autowired
    private CompareQualityDetailService compareQualityDetailService;

    @GetMapping("/findCompareQualityById/{id}")
    @ApiOperation(value = "通过id查找综合素质表", notes = "返回综合素质表对象")
    public CompareQuality findCompareQualityById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return compareQualityService.findCompareQualityById(id);
    }

    @PostMapping("/saveCompareQuality")
    @ApiOperation(value = "保存综合素质表", notes = "返回综合素质表对象")
    public CompareQuality saveCompareQuality(
            @ApiParam(value = "综合素质表对象", required = true)
            @RequestBody CompareQuality compareQuality){
        compareQuality.setCreateTime(DateUtil.now());
        compareQualityService.saveCompareQuality(compareQuality);
        return compareQuality;
    }

    @PostMapping("/findCompareQualityListByCondition")
    @ApiOperation(value = "根据条件查找综合素质表列表", notes = "返回综合素质表列表")
    public List<CompareQuality> findCompareQualityListByCondition(
            @ApiParam(value = "综合素质表对象")
            @RequestBody CompareQuality compareQuality){
        return compareQualityService.findCompareQualityListByCondition(compareQuality);
    }
    @PostMapping("/findCompareQualityCountByCondition")
    @ApiOperation(value = "根据条件查找综合素质表列表个数", notes = "返回综合素质表总个数")
    public long findCompareQualityCountByCondition(
            @ApiParam(value = "综合素质表对象")
            @RequestBody CompareQuality compareQuality){
        return compareQualityService.findCompareQualityCountByCondition(compareQuality);
    }

    @PostMapping("/updateCompareQuality")
    @ApiOperation(value = "修改综合素质表", notes = "综合素质表对象必传")
    public void updateCompareQuality(
            @ApiParam(value = "综合素质表对象,对象属性不为空则修改", required = true)
            @RequestBody CompareQuality compareQuality){
        compareQualityService.updateCompareQuality(compareQuality);
    }

    @GetMapping("/deleteCompareQuality/{id}")
    @ApiOperation(value = "通过id删除综合素质表")
    public void deleteCompareQuality(
            @ApiParam(value = "综合素质表对象", required = true)
            @PathVariable String id){
        compareQualityService.deleteCompareQuality(id);
    }
    @PostMapping("/deleteCompareQualityByCondition")
    @ApiOperation(value = "根据条件删除综合素质表")
    public void deleteCompareQualityByCondition(
            @ApiParam(value = "综合素质表对象")
            @RequestBody CompareQuality compareQuality){
        compareQualityService.deleteCompareQualityByCondition(compareQuality);
    }
    @PostMapping("/findOneCompareQualityByCondition")
    @ApiOperation(value = "根据条件查找单个综合素质表,结果必须为单条数据", notes = "返回单个综合素质表,没有时为空")
    public CompareQuality findOneCompareQualityByCondition(
            @ApiParam(value = "综合素质表对象")
            @RequestBody CompareQuality compareQuality){
        return compareQualityService.findOneCompareQualityByCondition(compareQuality);
    }

    @PostMapping("/batchSaveCompareQuality")
    @ApiOperation(value = "批量插入数据", notes = "返回单个综合素质表,没有时为空")
    public Map<String,Object> batchSaveCompareQuality(
            @ApiParam(value = "综合素质表集合对象")
            @RequestBody List<CompareQualityDetail> list){
        return compareQualityDetailService.batchSaveCompareQualityDetail(list);
    }


    @PostMapping("/deleteCompareQualityByIdList")
    @ApiOperation(value = "批量删除数据")
    public void  deleteCompareQualityByIdList(
            @ApiParam(value = "综合素质表集合对象")
            @RequestBody List<String> idList){
        compareQualityService.deleteCompareQualityByIdList(idList);
    }


    @PostMapping("/moveCompareQualityDataToHistory")
    @ApiOperation(value = "根据班级id移动综合素质成绩的数据到历史记录表")
    public void  moveCompareQualityDataToHistory(
            @ApiParam(value = "综合素质表集合对象")
            @RequestBody List<String> classIdList){
        compareQualityService.moveCompareQualityDataToHistory(classIdList);
    }


}
