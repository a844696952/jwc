package com.yice.edu.cn.xw.controller.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqSpecialDate;
import com.yice.edu.cn.xw.service.kq.KqSpecialDateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kqSpecialDate")
@Api(value = "/kqSpecialDate", description = "考勤管理特殊日期表 模块")
public class KqSpecialDateController {
    @Autowired
    private KqSpecialDateService kqSpecialDateService;

    @GetMapping("/findKqSpecialDateById/{id}")
    @ApiOperation(value = "通过id查找考勤管理特殊日期表 ", notes = "返回考勤管理特殊日期表对象")
    public KqSpecialDate findKqSpecialDateById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return kqSpecialDateService.findKqSpecialDateById(id);
    }

    @PostMapping("/saveKqSpecialDate")
    @ApiOperation(value = "保存考勤管理特殊日期表 ", notes = " 返回考勤管理特殊日期表对象")
    public KqSpecialDate saveKqSpecialDate(
            @ApiParam(value = "考勤管理特殊日期表 对象", required = true)
            @RequestBody KqSpecialDate kqSpecialDate) {
        kqSpecialDateService.saveKqSpecialDate(kqSpecialDate);
        return kqSpecialDate;
    }

    @PostMapping("/findKqSpecialDateListByCondition")
    @ApiOperation(value = "根据条件查找考勤管理特殊日期表 列表", notes = " 返回考勤管理特殊日期表 列表")
    public List<KqSpecialDate> findKqSpecialDateListByCondition(
            @ApiParam(value = "考勤管理特殊日期表 对象")
            @RequestBody KqSpecialDate kqSpecialDate) {
        return kqSpecialDateService.findKqSpecialDateListByCondition(kqSpecialDate);
    }

    @PostMapping("/findKqSpecialDateCountByCondition")
    @ApiOperation(value = "根据条件查找考勤管理特殊日期表 列表个数", notes = " 返回考勤管理特殊日期表 总个数")
    public long findKqSpecialDateCountByCondition(@ApiParam(value = "考勤管理特殊日期表 对象") @RequestBody KqSpecialDate kqSpecialDate) {
        return kqSpecialDateService.findKqSpecialDateCountByCondition(kqSpecialDate);
    }

    @PostMapping("/updateKqSpecialDate")
    @ApiOperation(value = "修改考勤管理特殊日期表 ", notes = " 考勤管理特殊日期表对象必传")
    public void updateKqSpecialDate(
            @ApiParam(value = "考勤管理特殊日期表 对象, 对象属性不为空则修改", required = true)
            @RequestBody KqSpecialDate kqSpecialDate) {
        kqSpecialDateService.updateKqSpecialDate(kqSpecialDate);
    }

    @GetMapping("/deleteKqSpecialDate/{id}")
    @ApiOperation(value = "通过id删除考勤管理特殊日期表 ")
    public void deleteKqSpecialDate(@ApiParam(value = "考勤管理特殊日期表 对象", required = true)
                                    @PathVariable String id) {
        kqSpecialDateService.deleteKqSpecialDate(id);
    }

    @PostMapping("/deleteKqSpecialDateByCondition")
    @ApiOperation(value = "根据条件删除考勤管理特殊日期表 ")
    public void deleteKqSpecialDateByCondition(@ApiParam(value = "考勤管理特殊日期表 对象") @RequestBody KqSpecialDate kqSpecialDate) {
        kqSpecialDateService.deleteKqSpecialDateByCondition(kqSpecialDate);
    }

    @PostMapping("/findOneKqSpecialDateByCondition")
    @ApiOperation(value = "根据条件查找单个考勤管理特殊日期表, 结果必须为单条数据", notes = " 返回单个考勤管理特殊日期表, 没有时为空")
    public KqSpecialDate findOneKqSpecialDateByCondition(@ApiParam(value = "考勤管理特殊日期表 对象") @RequestBody KqSpecialDate kqSpecialDate) {
        return kqSpecialDateService.findOneKqSpecialDateByCondition(kqSpecialDate);
    }
}
