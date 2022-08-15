package com.yice.edu.cn.yed.service.jw.holiday;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.holiday.Holiday;
import com.yice.edu.cn.common.pojo.jw.holiday.HolidayDate;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author:xushu
 * @date:2019/5/27
 */
@Component
@ApiModel(value = "同步第三方节假日数据")
public class DataSynchronize {
    @Autowired
    private HolidayService holidayService;
    @Value("${holiday.url}")
    private String url;

    public ResponseJson dataSynchronize(Holiday holiday) {
        String text = url + holiday.getYear();
        try {
            String s = HttpUtil.get(text);
            Map<String, String> holidays = JSON.parseObject(s, Map.class);
            if (!String.valueOf(holidays.get("code")).equals("0")) {
                return new ResponseJson(false, "同步失效,请手动添加!");
            }
            Map holiday1 = JSON.parseObject(String.valueOf(holidays.get("holiday")), Map.class);
            Set<String> set = holiday1.keySet();
            List<Holiday> list = new ArrayList<>();
            for (String k : set) {
                HolidayDate holidayDate = JSON.parseObject(String.valueOf(holiday1.get(k)), HolidayDate.class);
                Holiday holiday2 = new Holiday();
                holiday2.setDate(k);
                if (holidayDate.getHoliday() == true) {
                    holiday2.setType("0");
                } else {
                    holiday2.setType("1");
                }
                holiday2.setStatus("0");
                holiday2.setHolidayDate(holidayDate);
                holiday2.setYear(holiday.getYear());
                list.add(holiday2);
            }
            holidayService.batchSaveHoliday(list);
            Holiday holiday11 = new Holiday();
            holiday11.setYear(holiday.getYear());
            holiday11.setStatus("1");
            Pager pager = new Pager();
            pager.setPaging(false);
            List<Holiday> ll = holidayService.findHolidayListByCondition(holiday11);
            if (ll.size() > 0 && ll != null) {
                for (Holiday l : ll) {
                    holidayService.deleteHoliday(l.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseJson();
    }
}
