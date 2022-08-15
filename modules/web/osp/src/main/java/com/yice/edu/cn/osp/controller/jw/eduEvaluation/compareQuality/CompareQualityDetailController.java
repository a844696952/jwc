package com.yice.edu.cn.osp.controller.jw.eduEvaluation.compareQuality;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eduEvaluation.CompareQualityDetail;
import com.yice.edu.cn.osp.service.jw.eduEvaluation.CompareQualityDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/compareQualityDetail")
@Api(value = "/compareQualityDetail",description = "模块")
public class CompareQualityDetailController {
    @Autowired
    private CompareQualityDetailService compareQualityDetailService;

    @PostMapping("/saveCompareQualityDetail")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=CompareQualityDetail.class)
    public ResponseJson saveCompareQualityDetail(
            @ApiParam(value = "对象", required = true)
            @RequestBody CompareQualityDetail compareQualityDetail){
        compareQualityDetail.setSchoolId(mySchoolId());
        CompareQualityDetail s=compareQualityDetailService.saveCompareQualityDetail(compareQualityDetail);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCompareQualityDetailById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=CompareQualityDetail.class)
    public ResponseJson findCompareQualityDetailById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CompareQualityDetail compareQualityDetail=compareQualityDetailService.findCompareQualityDetailById(id);
        return new ResponseJson(compareQualityDetail);
    }

    @PostMapping("/update/updateCompareQualityDetail")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateCompareQualityDetail(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody CompareQualityDetail compareQualityDetail){
        compareQualityDetailService.updateCompareQualityDetail(compareQualityDetail);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCompareQualityDetailById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=CompareQualityDetail.class)
    public ResponseJson lookCompareQualityDetailById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CompareQualityDetail compareQualityDetail=compareQualityDetailService.findCompareQualityDetailById(id);
        return new ResponseJson(compareQualityDetail);
    }

    @PostMapping("/findCompareQualityDetailsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=CompareQualityDetail.class)
    public ResponseJson findCompareQualityDetailsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CompareQualityDetail compareQualityDetail){
        List<CompareQualityDetail> data=compareQualityDetailService.findCompareQualityDetailListByCondition(compareQualityDetail);
        long count=compareQualityDetailService.findCompareQualityDetailCountByCondition(compareQualityDetail);
        return new ResponseJson(data,count);
    }

    @PostMapping("deleteCompareQualityCheck")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=CompareQualityDetail.class)
    public ResponseJson deleteCompareQualityCheck(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CompareQualityDetail compareQualityDetail){
        ArrayList<String> nameList=new ArrayList<>();
        compareQualityDetail.getCheckIdList().forEach(data->{
                CompareQualityDetail compareQualityDetail1=new CompareQualityDetail();
                compareQualityDetail.setCompareQualityId(data.getId());
            long count=compareQualityDetailService.findCompareQualityDetailCountByCondition(compareQualityDetail);
            if(count>0){
                nameList.add(data.getName());
            }
        });

        return new ResponseJson(nameList);
    }
    @PostMapping("/findOneCompareQualityDetailByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=CompareQualityDetail.class)
    public ResponseJson findOneCompareQualityDetailByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CompareQualityDetail compareQualityDetail){
        CompareQualityDetail one=compareQualityDetailService.findOneCompareQualityDetailByCondition(compareQualityDetail);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCompareQualityDetail/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCompareQualityDetail(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        compareQualityDetailService.deleteCompareQualityDetail(id);
        return new ResponseJson();
    }


    @PostMapping("/findCompareQualityDetailListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=CompareQualityDetail.class)
    public ResponseJson findCompareQualityDetailListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CompareQualityDetail compareQualityDetail){
        List<CompareQualityDetail> data=compareQualityDetailService.findCompareQualityDetailListByCondition(compareQualityDetail);
        return new ResponseJson(data);
    }



}
