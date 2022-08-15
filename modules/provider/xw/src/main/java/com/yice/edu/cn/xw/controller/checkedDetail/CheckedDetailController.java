package com.yice.edu.cn.xw.controller.checkedDetail;

import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.xw.service.checkedDetail.CheckedDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkedDetail")
@Api(value = "/checkedDetail",description = "签到明细模块")
public class CheckedDetailController {
    @Autowired
    private CheckedDetailService checkedDetailService;

    @GetMapping("/findCheckedDetailById/{id}")
    @ApiOperation(value = "通过id查找签到明细", notes = "返回签到明细对象")
    public CheckedDetail findCheckedDetailById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return checkedDetailService.findCheckedDetailById(id);
    }

    @PostMapping("/saveCheckedDetail")
    @ApiOperation(value = "保存签到明细", notes = "返回签到明细对象")
    public CheckedDetail saveCheckedDetail(
            @ApiParam(value = "签到明细对象", required = true)
            @RequestBody CheckedDetail checkedDetail){
        checkedDetailService.saveCheckedDetail(checkedDetail);
        return checkedDetail;
    }

    @PostMapping("/findCheckedDetailListByCondition")
    @ApiOperation(value = "根据条件查找签到明细列表", notes = "返回签到明细列表")
    public List<CheckedDetail> findCheckedDetailListByCondition(
            @ApiParam(value = "签到明细对象")
            @RequestBody CheckedDetail checkedDetail){
        return checkedDetailService.findCheckedDetailListByCondition(checkedDetail);
    }

    @PostMapping("/findCheckedDetailListByCondition4like")
    @ApiOperation(value = "根据条件查找签到明细列表", notes = "返回签到明细列表")
    public List<CheckedDetail> findCheckedDetailListByCondition4like(
            @ApiParam(value = "签到明细对象")
            @RequestBody CheckedDetail checkedDetail){
        return checkedDetailService.findCheckedDetailListByCondition4like(checkedDetail);
    }
    @PostMapping("/findCheckedDetailCountByCondition")
    @ApiOperation(value = "根据条件查找签到明细列表个数", notes = "返回签到明细总个数")
    public long findCheckedDetailCountByCondition(
            @ApiParam(value = "签到明细对象")
            @RequestBody CheckedDetail checkedDetail){
        return checkedDetailService.findCheckedDetailCountByCondition(checkedDetail);
    }

    @PostMapping("/updateCheckedDetail")
    @ApiOperation(value = "修改签到明细", notes = "签到明细对象必传")
    public void updateCheckedDetail(
            @ApiParam(value = "签到明细对象,对象属性不为空则修改", required = true)
            @RequestBody CheckedDetail checkedDetail){
        checkedDetailService.updateCheckedDetail(checkedDetail);
    }

    @PostMapping("/updateCheckedDetail4Like")
    @ApiOperation(value = "变更签到明细", notes = "签到明细对象必传")
    public CheckedDetail updateCheckedDetail4Like(
            @ApiParam(value = "签到明细对象,对象属性不为空则修改", required = true)
            @RequestBody CheckedDetail checkedDetail){
        checkedDetailService.updateCheckedDetail4Like(checkedDetail);
        return checkedDetail;
    }

    @PostMapping("/updateTochangeCheckedDetail4Like")
    @ApiOperation(value = "修改签到明细", notes = "签到明细对象必传")
    public CheckedDetail updateTochangeCheckedDetail4Like(
            @ApiParam(value = "签到明细对象,对象属性不为空则修改", required = true)
            @RequestBody CheckedDetail checkedDetail){
        checkedDetailService.updateTochangeCheckedDetail4Like(checkedDetail);
        return checkedDetail;
    }

    @GetMapping("/deleteCheckedDetail/{id}")
    @ApiOperation(value = "通过id删除签到明细")
    public void deleteCheckedDetail(
            @ApiParam(value = "签到明细对象", required = true)
            @PathVariable String id){
        checkedDetailService.deleteCheckedDetail(id);
    }
    @PostMapping("/deleteCheckedDetailByCondition")
    @ApiOperation(value = "根据条件删除签到明细")
    public void deleteCheckedDetailByCondition(
            @ApiParam(value = "签到明细对象")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetailService.deleteCheckedDetailByCondition(checkedDetail);
    }

    @PostMapping("/deleteCheckedDetailByCondition4Like")
    @ApiOperation(value = "根据条件删除签到明细")
    public CheckedDetail deleteCheckedDetailByCondition4Like(
            @ApiParam(value = "签到明细对象")
            @RequestBody CheckedDetail checkedDetail){
        checkedDetailService.deleteCheckedDetailByCondition4Like(checkedDetail);
        return checkedDetail;
    }

    @PostMapping("/findOneCheckedDetailByCondition")
    @ApiOperation(value = "根据条件查找单个签到明细,结果必须为单条数据", notes = "返回单个签到明细,没有时为空")
    public CheckedDetail findOneCheckedDetailByCondition(
            @ApiParam(value = "签到明细对象")
            @RequestBody CheckedDetail checkedDetail){
        return checkedDetailService.findOneCheckedDetailByCondition(checkedDetail);
    }

    @PostMapping("/findCheckedDetailListForAppTapByCondition")
    @ApiOperation(value = "根据条件查询签到明细列表,普通教师查询", notes = "返回列表")
    public List<CheckedDetail> findCheckedDetailListForAppTapByCondition(
            @ApiParam(value = "签到明细对象")
            @RequestBody CheckedDetail checkedDetail){
        return checkedDetailService.findCheckedDetailListForAppTapByCondition(checkedDetail);
    }

    @PostMapping("/findCheckedDetailsForAppTapByConditionNewPrincipal")
    @ApiOperation(value = "根据条件查询签到明细列表,校长查询", notes = "返回列表")
    public List<CheckedDetail> findCheckedDetailsForAppTapByConditionNewPrincipal(
            @ApiParam(value = "签到明细对象")
            @RequestBody CheckedDetail checkedDetail){
        return checkedDetailService.findCheckedDetailsForAppTapByConditionNewPrincipal(checkedDetail);
    }

    @PostMapping("/findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal")
    @ApiOperation(value = "根据条件查询签到明细列表,校长查询", notes = "返回列表")
    public List<CheckedDetail> findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal(
            @ApiParam(value = "签到明细对象")
            @RequestBody CheckedDetail checkedDetail){
        return checkedDetailService.findCheckedDetailsForAppTapNameOrTelByConditionNewPrincipal(checkedDetail);
    }

    @PostMapping("/updateCheckedDetailForTapNewTeacher")
    @ApiOperation(value = "修改签到明细", notes = "签到明细对象必传")
    public void updateCheckedDetailForTapNewTeacher(
            @ApiParam(value = "签到明细对象,对象属性不为空则修改", required = true)
            @RequestBody CheckedDetail checkedDetail){
        checkedDetailService.updateCheckedDetailForTapNewTeacher(checkedDetail);
    }


}
