package com.yice.edu.cn.xw.controller.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqMonthStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.xw.service.kq.KqDailyStatisticalService;
import com.yice.edu.cn.xw.service.kq.KqOriginalDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kqDailyStatistical")
@Api(value = "/kqDailyStatistical", description = "考勤日统计表模块")
public class KqDailyStatisticalController {
    @Autowired
    private KqDailyStatisticalService kqDailyStatisticalService;
    @Autowired
    private KqOriginalDataService kqOriginalDataService;

    @GetMapping("/findKqDailyStatisticalById/{id}")
    @ApiOperation(value = "通过id查找考勤日统计表", notes = "返回考勤日统计表对象")
    public KqDailyStatistical findKqDailyStatisticalById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return kqDailyStatisticalService.findKqDailyStatisticalById(id);
    }

    @PostMapping("/saveKqDailyStatistical")
    @ApiOperation(value = "保存考勤日统计表", notes = "返回考勤日统计表对象")
    public KqDailyStatistical saveKqDailyStatistical(
            @ApiParam(value = "考勤日统计表对象", required = true)
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        kqDailyStatisticalService.saveKqDailyStatistical(kqDailyStatistical);
        return kqDailyStatistical;
    }

    @PostMapping("/findKqDailyStatisticalListByCondition")
    @ApiOperation(value = "根据条件查找考勤日统计表列表", notes = "返回考勤日统计表列表")
    public List<KqDailyStatistical> findKqDailyStatisticalListByCondition(
            @ApiParam(value = "考勤日统计表对象")
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalService.findKqDailyStatisticalListByCondition(kqDailyStatistical);
    }

    @PostMapping("/findKqDailyStatisticalCountByCondition")
    @ApiOperation(value = "根据条件查找考勤日统计表列表个数", notes = "返回考勤日统计表总个数")
    public long findKqDailyStatisticalCountByCondition(
            @ApiParam(value = "考勤日统计表对象")
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalService.findKqDailyStatisticalCountByCondition(kqDailyStatistical);
    }

    @PostMapping("/updateKqDailyStatistical")
    @ApiOperation(value = "修改考勤日统计表", notes = "考勤日统计表对象必传")
    public void updateKqDailyStatistical(
            @ApiParam(value = "考勤日统计表对象,对象属性不为空则修改", required = true)
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        kqDailyStatisticalService.updateKqDailyStatistical(kqDailyStatistical);
    }

    @GetMapping("/deleteKqDailyStatistical/{id}")
    @ApiOperation(value = "通过id删除考勤日统计表")
    public void deleteKqDailyStatistical(
            @ApiParam(value = "考勤日统计表对象", required = true)
            @PathVariable String id) {
        kqDailyStatisticalService.deleteKqDailyStatistical(id);
    }

    @PostMapping("/deleteKqDailyStatisticalByCondition")
    @ApiOperation(value = "根据条件删除考勤日统计表")
    public void deleteKqDailyStatisticalByCondition(
            @ApiParam(value = "考勤日统计表对象")
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        kqDailyStatisticalService.deleteKqDailyStatisticalByCondition(kqDailyStatistical);
    }

    @PostMapping("/findOneKqDailyStatisticalByCondition")
    @ApiOperation(value = "根据条件查找单个考勤日统计表,结果必须为单条数据", notes = "返回单个考勤日统计表,没有时为空")
    public KqDailyStatistical findOneKqDailyStatisticalByCondition(
            @ApiParam(value = "考勤日统计表对象")
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalService.findOneKqDailyStatisticalByCondition(kqDailyStatistical);
    }

    @PostMapping("/findKqMonthStatisticalListByCondition")
    @ApiOperation(value = "月统计", notes = "返回考勤月统计表列表")
    public List<KqMonthStatistical> findKqMonthStatisticalListByCondition(
            @ApiParam(value = "考勤日统计表对象")
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalService.findKqMonthStatisticalListByCondition(kqDailyStatistical);
    }

    //findKqMonthStatisticalListAll
    @PostMapping("/findKqMonthStatisticalListAll")
    @ApiOperation(value = "月统计其他信息", notes = "返回考勤月统计表列表")
    public List<KqMonthStatistical> findKqMonthStatisticalListAll(
            @ApiParam(value = "考勤日统计表对象")
            @RequestBody KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalService.findKqMonthStatisticalListAll(kqDailyStatistical);
    }

    @PostMapping("/inTimeCountByRules")
    @ApiOperation(value = "即时统计", notes = "必传学校id  1特殊 2 普通")
    public KqDailyStatistical inTimeCountByRules(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        KqDailyStatistical kqDailyStatistical = new KqDailyStatistical();
        Integer i = kqOriginalDataService.judgeBasicOrSpecialIsTrue(kqOriginalData.getSchoolId(), 0);
        if (i == 1) {
            return kqOriginalDataService.inTimeSpecialCount(kqOriginalData);
        } else if (i == 2) {
            return kqOriginalDataService.inTimeBasicCount(kqOriginalData);
        }
        return kqDailyStatistical;
    }

    /*@PostMapping("/inTimeCountByRulesForClass")
    @ApiOperation(value = "即时统计  班級", notes = "必传学校id  1特殊 2 普通")
    public List<KqDailyStatistical> inTimeCountByRulesForClass(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        List<KqDailyStatistical> kqDailyStatisticals=new ArrayList<>();
        Integer i = kqOriginalDataService.judgeBasicOrSpecialIsTrue(kqOriginalData.getSchoolId(), 0);
        if (i == 1) {
            return  kqOriginalDataService.InTimeSpecialByClass(kqOriginalData);
        } else if (i == 2) {
            return kqOriginalDataService.InTimeBasicByClass(kqOriginalData);
        }
        return kqDailyStatisticals;
    }
*/
}
