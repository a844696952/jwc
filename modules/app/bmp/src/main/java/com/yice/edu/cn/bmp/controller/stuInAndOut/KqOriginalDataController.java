package com.yice.edu.cn.bmp.controller.stuInAndOut;

import com.yice.edu.cn.bmp.service.stuInAndOut.KqOriginalDataService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@RestController
@RequestMapping("/kqOriginalData")
@Api(value = "/kqOriginalData", description = "考勤打卡原始记录表模块")
public class KqOriginalDataController {
    @Autowired
    private KqOriginalDataService kqOriginalDataService;

/*    @PostMapping("/saveKqOriginalData")
    @ApiOperation(value = "保存考勤打卡原始记录表对象", notes = "返回保存好的考勤打卡原始记录表对象", response = KqOriginalData.class)
    public ResponseJson saveKqOriginalData(
            @ApiParam(value = "考勤打卡原始记录表对象", required = true)
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        KqOriginalData s = kqOriginalDataService.saveKqOriginalData(kqOriginalData);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findKqOriginalDataById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findKqOriginalDataById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqOriginalData kqOriginalData = kqOriginalDataService.findKqOriginalDataById(id);
        return new ResponseJson(kqOriginalData);
    }

    @PostMapping("/update/updateKqOriginalData")
    @ApiOperation(value = "修改考勤打卡原始记录表对象", notes = "返回响应对象")
    public ResponseJson updateKqOriginalData(
            @ApiParam(value = "被修改的考勤打卡原始记录表对象,对象属性不为空则修改", required = true)
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalDataService.updateKqOriginalData(kqOriginalData);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqOriginalDataById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson lookKqOriginalDataById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqOriginalData kqOriginalData = kqOriginalDataService.findKqOriginalDataById(id);
        return new ResponseJson(kqOriginalData);
    }

    @PostMapping("/find/findKqOriginalDatasByCondition")
    @ApiOperation(value = "根据条件查找考勤打卡原始记录表", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findKqOriginalDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
       if (kqOriginalData.getTimeZone()!=null&&kqOriginalData.getTimeZone().length==2){
            kqOriginalData.getPager().setRangeField("capturedTime");
            kqOriginalData.getTimeZone()[0] = kqOriginalData.getTimeZone()[0]+" 00:00:00";
            kqOriginalData.getTimeZone()[1] = kqOriginalData.getTimeZone()[1]+" 23:59:59";
            kqOriginalData.getPager().setRangeArray(kqOriginalData.getTimeZone());
            kqOriginalData.setTimeZone(null);
        }else {
           kqOriginalData.setTimeZone(null);
       }

        kqOriginalData.setSchoolId(mySchoolId());
        List<KqOriginalData> data = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        long count = kqOriginalDataService.findKqOriginalDataCountByCondition(kqOriginalData);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findOneKqOriginalDataByCondition")
    @ApiOperation(value = "根据条件查找单个考勤打卡原始记录表,结果必须为单条数据", notes = "没有时返回空", response = KqOriginalData.class)
    public ResponseJson findOneKqOriginalDataByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqOriginalData kqOriginalData) {
        KqOriginalData one = kqOriginalDataService.findOneKqOriginalDataByCondition(kqOriginalData);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteKqOriginalData/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqOriginalData(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqOriginalDataService.deleteKqOriginalData(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqOriginalDataListByCondition")
    @ApiOperation(value = "根据条件查找考勤打卡原始记录表列表", notes = "返回响应对象,不包含总条数", response = KqOriginalData.class)
    public ResponseJson findKqOriginalDataListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        List<KqOriginalData> data = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        return new ResponseJson(data);
    }

    @PostMapping("/judgePunchStatusByrules")
    @ApiOperation(value = "根据特殊日期考勤规则判断学生打卡状态", notes = "返回响应对象,不包含总条数", response = KqDailyStatistical.class)
    public ResponseJson judgePunchStatusByrules(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        KqDailyStatistical data = kqOriginalDataService.judgePunchStatusByrules(kqOriginalData);
        return new ResponseJson(data);
    }

    @PostMapping("/dayBasicRecords")
    @ApiOperation(value = "根据普通考勤规则判断学生打卡状态", notes = "返回响应对象,不包含总条数", response = KqDailyStatistical.class)
    public ResponseJson dayBasicRecords(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        KqDailyStatistical data = kqOriginalDataService.dayBasicRecords(kqOriginalData);
        return new ResponseJson(data);
    }

    @PostMapping("/find/inTimeCountByRules")
    @ApiOperation(value = "即时统计", notes = "返回响应对象,不包含总条数", response = KqOriginalData.class)
    public ResponseJson inTimeCountByRules(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setSchoolId(mySchoolId());
        KqDailyStatistical data = kqOriginalDataService.inTimeCountByRules(kqOriginalData);
        return new ResponseJson(data);
    }*/

    @PostMapping("/find/findKqStudentOriginalDatasByCondition")
    @ApiOperation(value = "出入校记录", notes = "返回响应对象", response = KqOriginalData.class)
    public ResponseJson findKqStudentOriginalDatasByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqOriginalData kqOriginalData) {
        kqOriginalData.setUserType("2,4");
        if (kqOriginalData.getTimeZone()!=null&&kqOriginalData.getTimeZone().length==2){
            kqOriginalData.getPager().setRangeField("capturedTime");
            kqOriginalData.getTimeZone()[0] = kqOriginalData.getTimeZone()[0]+" 00:00:00";
            kqOriginalData.getTimeZone()[1] = kqOriginalData.getTimeZone()[1]+" 23:59:59";
            kqOriginalData.getPager().setRangeArray(kqOriginalData.getTimeZone());
            kqOriginalData.setTimeZone(null);
        }else {
            kqOriginalData.setTimeZone(null);
        }
        if (kqOriginalData.getPassStatus()==null) {
            kqOriginalData.setPassStatus("-1");
        }
        kqOriginalData.setStudentId(myStudentId());
        kqOriginalData.setSchoolId(mySchoolId());
        kqOriginalData.getPager().setPaging(false);
       //kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
        List<KqOriginalData> data = kqOriginalDataService.findKqOriginalDataListByCondition(kqOriginalData);
        long count = kqOriginalDataService.findKqOriginalDataCountByCondition(kqOriginalData);
        return new ResponseJson(data, count);
    }


}
