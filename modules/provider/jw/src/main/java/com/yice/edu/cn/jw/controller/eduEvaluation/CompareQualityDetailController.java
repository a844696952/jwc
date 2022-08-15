package com.yice.edu.cn.jw.controller.eduEvaluation;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import com.yice.edu.cn.jw.service.eduEvaluation.CompareQualityDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compareQualityDetail")
@Api(value = "/compareQualityDetail",description = "模块")
public class CompareQualityDetailController {
    @Autowired
    private CompareQualityDetailService compareQualityDetailService;

    @GetMapping("/findCompareQualityDetailById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public CompareQualityDetail findCompareQualityDetailById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return compareQualityDetailService.findCompareQualityDetailById(id);
    }

    @PostMapping("/saveCompareQualityDetail")
    @ApiOperation(value = "保存", notes = "返回对象")
    public CompareQualityDetail saveCompareQualityDetail(
            @ApiParam(value = "对象", required = true)
            @RequestBody CompareQualityDetail compareQualityDetail){
        compareQualityDetail.setCreateTime(DateUtil.now());
        compareQualityDetailService.saveCompareQualityDetail(compareQualityDetail);
        return compareQualityDetail;
    }

    @PostMapping("/findCompareQualityDetailListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<CompareQualityDetail> findCompareQualityDetailListByCondition(
            @ApiParam(value = "对象")
            @RequestBody CompareQualityDetail compareQualityDetail){
        return compareQualityDetailService.findCompareQualityDetailListByCondition(compareQualityDetail);
    }
    @PostMapping("/findCompareQualityDetailCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findCompareQualityDetailCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody CompareQualityDetail compareQualityDetail){
        return compareQualityDetailService.findCompareQualityDetailCountByCondition(compareQualityDetail);
    }

    @PostMapping("/updateCompareQualityDetail")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateCompareQualityDetail(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody CompareQualityDetail compareQualityDetail){
        compareQualityDetailService.updateCompareQualityDetail(compareQualityDetail);
    }

    @GetMapping("/deleteCompareQualityDetail/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteCompareQualityDetail(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        compareQualityDetailService.deleteCompareQualityDetail(id);
    }
    @PostMapping("/deleteCompareQualityDetailByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteCompareQualityDetailByCondition(
            @ApiParam(value = "对象")
            @RequestBody CompareQualityDetail compareQualityDetail){
        compareQualityDetailService.deleteCompareQualityDetailByCondition(compareQualityDetail);
    }
    @PostMapping("/findOneCompareQualityDetailByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public CompareQualityDetail findOneCompareQualityDetailByCondition(
            @ApiParam(value = "对象")
            @RequestBody CompareQualityDetail compareQualityDetail){
        return compareQualityDetailService.findOneCompareQualityDetailByCondition(compareQualityDetail);
    }

    @PostMapping("/deleteCompareQualityDetailByIdList")
    @ApiOperation(value = "批量删除数据")
    public void  deleteCompareQualityDetailByIdList(
            @ApiParam(value = "综合素质表集合对象")
            @RequestBody List<String> idList){
        compareQualityDetailService.deleteCompareQualityDetailByIdList(idList);
    }

    @PostMapping("/getClassTypeList")
    public List<String> getClassTypeList(@RequestBody CompareQualityDetail compareQualityDetail){
      return   compareQualityDetailService.getClassTypeList(compareQualityDetail);

    }

    @PostMapping("/findCompareQualityForStu")
    public  List<CompareQualityDetail> findCompareQualityForStu(@RequestBody CompareQualityDetail compareQualityDetail){
        return compareQualityDetailService.findCompareQualityDetailListByCondition(compareQualityDetail);

    }


}
