package com.yice.edu.cn.yed.service.jw.holiday;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.jw.holiday.Holiday;
import com.yice.edu.cn.common.pojo.jw.holiday.HolidayDate;
import com.yice.edu.cn.yed.feignClient.jw.holiday.HolidayFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HolidayService {
    @Autowired
    private HolidayFeign holidayFeign;
    public Holiday findHolidayById(String id) {
        return holidayFeign.findHolidayById(id);
    }

    public Holiday saveHoliday(Holiday holiday) { return holidayFeign.saveHoliday(holiday);
    }

    public List<Holiday> findHolidayListByCondition(Holiday holiday) {
        /*Holiday holiday1 = new Holiday();
        HolidayDate holidayDate=new HolidayDate();
        holidayDate.setHoliday(true);
        holiday1.setYear(holiday.getYear());
        holiday1.setStatus("0");
        holiday1.setHolidayDate(holidayDate);
        List<Holiday> list1 = holidayFeign.findHolidayListByCondition(holiday1);
        System.out.println(list1);
        if (list1.size() > 0) {
            Map<String, List<Holiday>> collect1 = list1.stream().collect(Collectors.groupingBy(kq -> kq.getHolidayDate().getName()));
            collect1.forEach((k, v) -> {
                for (int i = 0; i < v.size(); i++) {
                    Holiday holiday2 = new Holiday();
                    if (v.size() == 1) {
                        holiday2.setStartTime(v.stream().findFirst().get().getDate());
                        holiday2.setEndTime(v.stream().findFirst().get().getDate());
                        holiday2.setId(v.stream().findFirst().get().getId());
                        holidayFeign.updateHoliday(holiday2);
                    }
                    holiday2.setStartTime(v.stream().sorted(Comparator.comparing(Holiday::getDate)).findFirst().get().getDate());
                    holiday2.setEndTime(v.stream().sorted(Comparator.comparing(Holiday::getDate).reversed()).findFirst().get().getDate());
                    for(Holiday cc:v){
                        holiday2.setId(cc.getId());
                        holidayFeign.updateHoliday(holiday2);
                    }
                }
            });
        }*/
        return holidayFeign.findHolidayListByCondition(holiday);
    }

    public Holiday findOneHolidayByCondition(Holiday holiday) {
        return holidayFeign.findOneHolidayByCondition(holiday);
    }

    public long findHolidayCountByCondition(Holiday holiday) {
        return holidayFeign.findHolidayCountByCondition(holiday);
    }

    public void updateHoliday(Holiday holiday) {
        holidayFeign.updateHoliday(holiday);
    }

    public void deleteHoliday(String id) {
        holidayFeign.deleteHoliday(id);
    }

    public void deleteHolidayByCondition(Holiday holiday) { holidayFeign.deleteHolidayByCondition(holiday); }

    public void batchSaveHoliday(List<Holiday> holidays){
        holidayFeign.batchSaveHoliday(holidays); }

}
