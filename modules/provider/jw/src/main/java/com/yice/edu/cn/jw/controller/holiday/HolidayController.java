package com.yice.edu.cn.jw.controller.holiday;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.holiday.Holiday;
import com.yice.edu.cn.common.pojo.jw.holiday.HolidayDate;
import com.yice.edu.cn.jw.service.holiday.HolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/holiday")
@Api(value = "/holiday", description = "节假日模块")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;
    @CreateCache(name = Constant.Redis.HOLIDAY, expire = Constant.Redis.HOLIDAY_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String, List<Holiday>> codeCache;

    @GetMapping("/findHolidayById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public Holiday findHolidayById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id) {
        return holidayService.findHolidayById(id);
    }

    @PostMapping("/saveHoliday")
    @ApiOperation(value = "保存", notes = "返回对象")
    public Holiday saveHoliday(
            @ApiParam(value = "对象", required = true)
            @RequestBody Holiday holiday) {
        holidayService.saveHoliday(holiday);
        if (codeCache.get("key") != null&&codeCache.get("key").size() > 0 ) {
            codeCache.remove("key");
        }
        return holiday;
    }

    @PostMapping("/findHolidayListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<Holiday> findHolidayListByCondition(
            @ApiParam(value = "对象")
            @RequestBody Holiday holiday) {
        List<Holiday> list = holidayService.findHolidayListByCondition(holiday);
        codeCache.put("key", list);
        return list;
    }

    @PostMapping("/findHolidayCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findHolidayCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody Holiday holiday) {
        return holidayService.findHolidayCountByCondition(holiday);
    }

    @PostMapping("/updateHoliday")
    @ApiOperation(value = "修改", notes = "对象必传")
    public void updateHoliday(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody Holiday holiday) {
        holidayService.updateHoliday(holiday);
    }

    @GetMapping("/deleteHoliday/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteHoliday(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id) {
        holidayService.deleteHoliday(id);
        if (codeCache.get("key") != null&&codeCache.get("key").size() > 0) {
            codeCache.remove("key");
        }
    }

    @PostMapping("/deleteHolidayByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteHolidayByCondition(
            @ApiParam(value = "对象")
            @RequestBody Holiday holiday) {
        holidayService.deleteHolidayByCondition(holiday);
    }

    @PostMapping("/findOneHolidayByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public Holiday findOneHolidayByCondition(
            @ApiParam(value = "对象")
            @RequestBody Holiday holiday) {
        return holidayService.findOneHolidayByCondition(holiday);
    }

    @PostMapping("/batchSaveHoliday")
    @ApiOperation(value = "批量添加")
    public List<Holiday> batchSaveHoliday(
            @ApiParam(value = "对象")
            @RequestBody List<Holiday> holidays) {
        holidayService.batchSaveHoliday(holidays);
        if (codeCache.get("key") != null&&codeCache.get("key").size() > 0 ) {
            codeCache.remove("key");
        }
        return holidays;
    }

}
