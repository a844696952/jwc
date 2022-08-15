package com.yice.edu.cn.osp.controller.xw.checkedDetail;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.osp.service.xw.checkedDetail.CheckedDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/checkedDetail")
@Api(value = "/checkedDetail",description = "签到明细模块")
public class CheckedDetailController {
    @Autowired
    private CheckedDetailService checkedDetailService;

    @PostMapping("/saveCheckedDetail")
    @ApiOperation(value = "保存签到明细对象", notes = "返回保存好的签到明细对象", response=CheckedDetail.class)
    public ResponseJson saveCheckedDetail(
            @ApiParam(value = "签到明细对象", required = true)
            @RequestBody CheckedDetail checkedDetail){
       checkedDetail.setSchoolId(mySchoolId());
        CheckedDetail s=checkedDetailService.saveCheckedDetail(checkedDetail);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findCheckedDetailById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找签到明细", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        CheckedDetail checkedDetail=checkedDetailService.findCheckedDetailById(id);
        return new ResponseJson(checkedDetail);
    }

    @PostMapping("/update/updateCheckedDetail")
    @ApiOperation(value = "修改签到明细对象", notes = "返回响应对象")
    public ResponseJson updateCheckedDetail(
            @ApiParam(value = "被修改的签到明细对象,对象属性不为空则修改", required = true)
            @RequestBody CheckedDetail checkedDetail){
        checkedDetailService.updateCheckedDetail(checkedDetail);
        return new ResponseJson();
    }

    @GetMapping("/look/lookCheckedDetailById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找签到明细", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson lookCheckedDetailById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        CheckedDetail checkedDetail=checkedDetailService.findCheckedDetailById(id);
        return new ResponseJson(checkedDetail);
    }

    @PostMapping("/findCheckedDetailsByCondition")
    @ApiOperation(value = "根据条件查找签到明细", notes = "返回响应对象", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CheckedDetail checkedDetail){
       checkedDetail.setSchoolId(mySchoolId());
        List<CheckedDetail> data=checkedDetailService.findCheckedDetailListByCondition(checkedDetail);
        long count=checkedDetailService.findCheckedDetailCountByCondition(checkedDetail);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneCheckedDetailByCondition")
    @ApiOperation(value = "根据条件查找单个签到明细,结果必须为单条数据", notes = "没有时返回空", response=CheckedDetail.class)
    public ResponseJson findOneCheckedDetailByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody CheckedDetail checkedDetail){
        CheckedDetail one=checkedDetailService.findOneCheckedDetailByCondition(checkedDetail);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteCheckedDetail/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteCheckedDetail(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        checkedDetailService.deleteCheckedDetail(id);
        return new ResponseJson();
    }


    @PostMapping("/findCheckedDetailListByCondition")
    @ApiOperation(value = "根据条件查找签到明细列表", notes = "返回响应对象,不包含总条数", response=CheckedDetail.class)
    public ResponseJson findCheckedDetailListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody CheckedDetail checkedDetail){
       checkedDetail.setSchoolId(mySchoolId());
        List<CheckedDetail> data=checkedDetailService.findCheckedDetailListByCondition(checkedDetail);
        return new ResponseJson(data);
    }



}
