package com.yice.edu.cn.xw.controller.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.xw.service.kq.KqBasicRulesService;
import com.yice.edu.cn.xw.service.kq.KqOriginalDataService;
import com.yice.edu.cn.xw.service.kq.KqSchoolInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kqOriginalData")
@Api(value = "/kqOriginalData", description = "考勤打卡原始记录表模块")
public class KqOriginalDataController {
    @Autowired
    private KqOriginalDataService kqOriginalDataService;
    @Autowired
    private KqBasicRulesService kqBasicRulesService;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;

    @GetMapping("/findKqOriginalDataById/{id}")
    @ApiOperation(value = "通过id查找考勤打卡原始记录表", notes = "返回考勤打卡原始记录表对象")
    public KqOriginalData findKqOriginalDataById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return kqOriginalDataService.findKqOriginalDataById(id);
    }

    @PostMapping("/saveKqOriginalData")
    @ApiOperation(value = "保存考勤打卡原始记录表", notes = "返回考勤打卡原始记录表对象")
    public KqOriginalData saveKqOriginalData(
            @ApiParam(value = "考勤打卡原始记录表对象", required = true)
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalDataService.saveKqOriginalData(kqOriginalData);
        return kqOriginalData;
    }

    @PostMapping("/findKqOriginalDataListByCondition")
    @ApiOperation(value = "根据条件查找考勤打卡原始记录表列表", notes = "返回考勤打卡原始记录表列表")
    public List<KqOriginalData> findKqOriginalDataListByCondition(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        if (kqOriginalData.getPager().getSortField() == null) {
            kqOriginalData.getPager().setSortField("capturedTime");
            kqOriginalData.getPager().setSortOrder("desc");
        }
        return kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
    }

    @PostMapping("/findKqOriginalDataCountByCondition")
    @ApiOperation(value = "根据条件查找考勤打卡原始记录表列表个数", notes = "返回考勤打卡原始记录表总个数")
    public long findKqOriginalDataCountByCondition(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        return kqOriginalDataService.findKqOriginalDataCountByCondition(kqOriginalData);
    }

    @PostMapping("/updateKqOriginalData")
    @ApiOperation(value = "修改考勤打卡原始记录表", notes = "考勤打卡原始记录表对象必传")
    public void updateKqOriginalData(
            @ApiParam(value = "考勤打卡原始记录表对象,对象属性不为空则修改", required = true)
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalDataService.updateKqOriginalData(kqOriginalData);
    }

    @GetMapping("/deleteKqOriginalData/{id}")
    @ApiOperation(value = "通过id删除考勤打卡原始记录表")
    public void deleteKqOriginalData(
            @ApiParam(value = "考勤打卡原始记录表对象", required = true)
            @PathVariable String id) {
        kqOriginalDataService.deleteKqOriginalData(id);
    }

    @PostMapping("/deleteKqOriginalDataByCondition")
    @ApiOperation(value = "根据条件删除考勤打卡原始记录表")
    public void deleteKqOriginalDataByCondition(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalDataService.deleteKqOriginalDataByCondition(kqOriginalData);
    }

    @PostMapping("/findOneKqOriginalDataByCondition")
    @ApiOperation(value = "根据条件查找单个考勤打卡原始记录表,结果必须为单条数据", notes = "返回单个考勤打卡原始记录表,没有时为空")
    public KqOriginalData findOneKqOriginalDataByCondition(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        return kqOriginalDataService.findOneKqOriginalDataByCondition(kqOriginalData);
    }
    @PostMapping("/batchSaveKqOriginalData")
    @ApiOperation(value = "批量保存原始记录")
    public void batchSaveKqOriginalData(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody List<KqOriginalData> kqOriginalDatas){
        kqOriginalDataService.batchSaveKqOriginalData(kqOriginalDatas);
    }
/****************************************************************************************************************************/
    @PostMapping("/judgePunchStatusByrules")
    @ApiOperation(value = "根据特殊日期规则判断当天学生打卡状态", notes = "考勤打卡原始记录表必传")
    public KqDailyStatistical judgePunchStatusByrules(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        return kqOriginalDataService.judgePunchStatusByrules(kqOriginalData);
    }

    @PostMapping("/dayBasicRecords")
    @ApiOperation(value = "根据普通考勤规则判断当天学生打卡状态", notes = "考勤打卡原始记录表必传")
    public KqDailyStatistical dayBasicRecords(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData kqOriginalData) {
        return kqOriginalDataService.dayBasicRecords(kqOriginalData);
    }

    @PostMapping("/judgeBasicOrSpecialIsTrue")
    @ApiOperation(value = "根据学校id查询当天考勤规则", notes = "必传学校id  1特殊 2 普通 3没有规则 ")
    public Integer judgeBasicOrSpecialIsTrue(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody String schoolId) {
        return kqOriginalDataService.judgeBasicOrSpecialIsTrue(schoolId, 0);
    }

    @GetMapping("/xwStuKqDailyStatis")
    @ApiOperation(value = "每日统计，定时器调用")
    public void xwStuKqDailyStatis() {
        //启动线程进行统计
        kqOriginalDataService.xwStuKqDailyStatis();
    }

    @PostMapping("/statusOfKqOriginalData")
    @ApiOperation(value = "考勤记录的考勤状态，用于判断是否发送推送", notes = "必传学校id  1特殊 2 普通 3没有规则 ")
    public boolean statusOfKqOriginalData(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData KqOriginalData) {
        return kqOriginalDataService.statusOfKqOriginalData(KqOriginalData);
    }
    @GetMapping("/changeStuNowStatus")
    @ApiOperation(value = "每日更新，定时器调用")
    public void changeStuNowStatus() {
        //启动线程进行更新学生状态
        kqOriginalDataService.changeStuNowStatus();
    }

    @GetMapping("/deleteThreeMonthsAgoAllData")
    @ApiOperation(value = "每月1号删除三个月前所有数据，定时器调用")
    public void deleteThreeMonthsAgoAllData() {
        //启动线程进行每月1号删除三个月前所有数据
        kqOriginalDataService.deleteThreeMonthsAgoAllData();
    }
    @PostMapping("/findSomeTimeAgoKqOriginalDataListByCondition")
    @ApiOperation(value = "查找某一时刻之前的数据")
   public List<KqOriginalData> findSomeTimeAgoKqOriginalDataListByCondition(
            @ApiParam(value = "考勤打卡原始记录表对象")
            @RequestBody KqOriginalData KqOriginalData
    ){
        return kqOriginalDataService.findSomeTimeAgoKqOriginalDataListByCondition(KqOriginalData);
   }
}
