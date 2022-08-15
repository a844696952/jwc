package com.yice.edu.cn.yed.feignClient.jw.holiday;

import com.yice.edu.cn.common.pojo.jw.holiday.Holiday;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "holidayFeign",path = "/holiday")
public interface HolidayFeign {
    @GetMapping("/findHolidayById/{id}")
    Holiday findHolidayById(@PathVariable("id") String id);
    @PostMapping("/saveHoliday")
    Holiday saveHoliday(Holiday holiday);
    @PostMapping("/findHolidayListByCondition")
    List<Holiday> findHolidayListByCondition(Holiday holiday);
    @PostMapping("/findOneHolidayByCondition")
    Holiday findOneHolidayByCondition(Holiday holiday);
    @PostMapping("/findHolidayCountByCondition")
    long findHolidayCountByCondition(Holiday holiday);
    @PostMapping("/updateHoliday")
    void updateHoliday(Holiday holiday);
    @GetMapping("/deleteHoliday/{id}")
    void deleteHoliday(@PathVariable("id") String id);
    @PostMapping("/deleteHolidayByCondition")
    void deleteHolidayByCondition(Holiday holiday);
    @PostMapping("/batchSaveHoliday")
    void batchSaveHoliday(List<Holiday> holidays);
}
