package com.yice.edu.cn.yed.controller.jw.holiday;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.holiday.Holiday;
import com.yice.edu.cn.common.pojo.jw.holiday.HolidayDate;
import com.yice.edu.cn.yed.service.jw.holiday.DataSynchronize;
import com.yice.edu.cn.yed.service.jw.holiday.HolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/holiday")
@Api(value = "/holiday", description = "模块")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private DataSynchronize dataSynchronize;

    @PostMapping("/update/dataSynchronize")
    @ApiOperation(value = "节假日数据同步", notes = "返回保存好的对象", response = Holiday.class)
    public ResponseJson dataSynchronize(@RequestBody Holiday holiday) {
        Holiday holiday3 = new Holiday();
        List<Holiday> holidayList = holidayService.findHolidayListByCondition(holiday3);
        if (holidayList.size() > 0) {
            Map<String, List<Holiday>> collect = holidayList.stream().collect(Collectors.groupingBy(kq -> kq.getYear()));
            collect.forEach((k, v) -> {
                if (k.equals(holiday.getYear()) && holiday.getStatus().equals("0")) {
                    Holiday h = new Holiday();
                    h.setYear(k);
                    h.setStatus("0");
                    holidayService.deleteHolidayByCondition(h);
                }
            });
        }
        dataSynchronize.dataSynchronize(holiday);
        return new ResponseJson();
    }


    @PostMapping("/saveHoliday")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = Holiday.class)
    public ResponseJson saveHoliday(
            @ApiParam(value = "对象", required = true)
            @RequestBody Holiday holiday) {
        String[] arr = holiday.getAllDate();
        List<Holiday> list = new ArrayList<>();
        Holiday holiday2 = new Holiday();
        List<Holiday> list1 = holidayService.findHolidayListByCondition(holiday2);
        if (list1.size() > 0) {
            for (Holiday ll : list1) {
                String yearDate = ll.getYear() + "-" + ll.getDate();
                if (ll.getHolidayDate().getName().equals(holiday.getHolidayDate().getName())) {
                    return new ResponseJson(false, "名称重复");
                }
                for (int i = 0; i < arr.length; i++) {
                    if (yearDate.equals(arr[i])) {
                        return new ResponseJson(false, "日期已存在");
                    }
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            Holiday holiday1 = new Holiday();
            HolidayDate holidayDate = new HolidayDate();
            holidayDate.setName(holiday.getHolidayDate().getName());
            holiday1.setHolidayDate(holidayDate);
            holiday1.setYear(arr[i].substring(0, 4));
            holiday1.setDate(arr[i].substring(5));
            holiday1.setStatus("1");
            holiday1.setType(holiday.getType());
            list.add(holiday1);
        }
        holidayService.batchSaveHoliday(list);
        return new ResponseJson();
    }

    @GetMapping("/update/findHolidayById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = Holiday.class)
    public ResponseJson findHolidayById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Holiday holiday = holidayService.findHolidayById(id);
        return new ResponseJson(holiday);
    }

    @PostMapping("/update/updateHoliday")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateHoliday(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Holiday holiday) {
        holidayService.updateHoliday(holiday);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHolidayById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = Holiday.class)
    public ResponseJson lookHolidayById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Holiday holiday = holidayService.findHolidayById(id);
        return new ResponseJson(holiday);
    }

    @PostMapping("/findHolidaysByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = Holiday.class)
    public ResponseJson findHolidaysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Holiday holiday) {
        holiday.getPager().setSortOrder(Pager.ASC);
        holiday.getPager().setSortField("date");
        List<Holiday> list = holidayService.findHolidayListByCondition(holiday);
        long count = holidayService.findHolidayCountByCondition(holiday);
        return new ResponseJson(list, count);
    }

    @PostMapping("/findOneHolidayByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = Holiday.class)
    public ResponseJson findOneHolidayByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Holiday holiday) {
        Holiday one = holidayService.findOneHolidayByCondition(holiday);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteHoliday/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHoliday(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        holidayService.deleteHoliday(id);
        return new ResponseJson();
    }

    @PostMapping("/deleteHolidayByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteHolidayByCondition(
            @ApiParam(value = "对象")
            @RequestBody Holiday holiday) {
        holidayService.deleteHoliday(holiday.getId());
    }

    @PostMapping("/findHolidayListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = Holiday.class)
    public ResponseJson findHolidayListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody Holiday holiday) {
        List<Holiday> data = holidayService.findHolidayListByCondition(holiday);
        return new ResponseJson(data);
    }

}
