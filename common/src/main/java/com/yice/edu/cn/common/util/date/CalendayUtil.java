package com.yice.edu.cn.common.util.date;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.kq.CalendayEntity;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendayUtil {

    /**
     * 百度日历API地址
     */
    private static String baiduApiUrl = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=%s&resource_id=%s";

    /**
     * 日历查询字段
     */
    private static String resource_id = "6018";

    private static volatile CalendayUtil calendayUtil = null;

    private CalendayUtil() {
    }

    public static CalendayUtil getInstance() {
        if (calendayUtil == null) {
            synchronized (CalendayUtil.class) {
                if (calendayUtil == null) {
                    calendayUtil = new CalendayUtil();
                }
            }
        }
        return calendayUtil;
    }

    /**
     * 将日期字符格式化为百度API传递的日期字符 如： 2017-10-01 --> 2017-10-1
     *
     * @param date
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public int isFestival(Date date, String festivalStr) throws IOException, ParseException {
        return isFestival(DateUtil.format(date, "yyyy-MM-d"), festivalStr);
    }

    /**
     * 查询当前日期是否为周末 法定节假日
     *
     * @param dateStr
     * @return 1 --法定节假日 2--休息时间  3---正常工作日期
     * @throws IOException
     * @throws ParseException
     */
    public int isFestival(String dateStr, String festivalStr) throws IOException, ParseException {
        int isFestival = -1;
        if (StringUtils.isBlank(festivalStr)) {
            festivalStr = calendayUtil.sendReq(String.format(baiduApiUrl, dateStr, resource_id));
        }
        JSONArray jsonArr = JSONObject.parseObject(festivalStr).getJSONArray("data");
        if (Objects.nonNull(jsonArr) && jsonArr.size() > 0) {
            String holiday = JSONObject.parseObject(jsonArr.getString(0)).getString("holiday");
            JSONArray holidayList = JSONObject.parseObject(jsonArr.getString(0)).getJSONArray("holidaylist");
            if (StringUtils.isNotBlank(holiday)) {
                List<CalendayEntity> calendarEntities = new ArrayList<>();
                if (holiday.startsWith("{") && holiday.endsWith("}")) {
                    CalendayEntity calendayEntity = JSONObject.parseObject(holiday, CalendayEntity.class);
                    calendarEntities.add(calendayEntity);
                }
                if (holiday.startsWith("[") && holiday.endsWith("]")) {
                    calendarEntities = JSONObject.parseArray(holiday, CalendayEntity.class);
                }
                if (CollUtil.isNotEmpty(calendarEntities)) {
                    for (int i = 0; i < calendarEntities.size(); i++) {
                        CalendayEntity calendayEntity = calendarEntities.get(i);
                        if (Objects.nonNull(calendayEntity)) {
                            List<CalendayEntity.Festival> list = calendayEntity.list;
                            if (DateUtil.parse(dateStr, Constant.DATE_FORMATTER_DAY).compareTo(DateUtil.parse(calendayEntity.festival, Constant.DATE_FORMATTER_DAY)) == 0) {
                                //法定节假日
                                isFestival = 1;
                            }
                            for (CalendayEntity.Festival festival : list) {
                                if (DateUtil.parse(dateStr, Constant.DATE_FORMATTER_DAY).compareTo(DateUtil.parse(festival.date, Constant.DATE_FORMATTER_DAY)) == 0) {
                                    if ("1".equals(festival.status)) {
                                        //法定节假日
                                        isFestival = 1;
                                        break;
                                    } else if ("2".equals(festival.status)) {
                                        //调休
                                        isFestival = -2;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (holidayList != null) {
                    for (int j = 0; j < holidayList.size(); j++) {
                        if (Objects.nonNull(holidayList.get(j))) {
                            String startDay = ((JSONObject) holidayList.get(j)).getString("startday");
                            if (StringUtils.isNotBlank(startDay)) {
                                if (DateUtil.parse(dateStr, Constant.DATE_FORMATTER_DAY).compareTo(DateUtil.parse(startDay, Constant.DATE_FORMATTER_DAY)) == 0) {
                                    //法定节假日
                                    isFestival = 1;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        // 当前日期不包含在法定节假日中
        if (isFestival == -1) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = dateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            // 判断日期是否为周末
            if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                if (isFestival != -2) {
                    isFestival = 2;
                } else {
                    isFestival = 3;
                }
            } else {
                isFestival = 3;
            }
        }
        return isFestival;
    }


    /**
     * 发送请求获取百度日历节假日数据
     *
     * @param reqUrl
     * @return
     * @throws
     * @throws IOException
     */
    public String sendReq(String reqUrl) {
        String result = HttpUtil.get(reqUrl, 30000);
        return result;
    }

    /**
     * 获取当前日历信息
     *
     * @param date
     * @return
     */
    public String getHolidayInfo(String date) {
        if (StringUtils.isNotBlank(date)) {
            date = DateUtil.format(DateUtil.parse(date, "yyyy-MM"), "yyyy-MM");
        } else {
            date = DateUtil.format(DateUtil.parse(DateUtil.now(), "yyyy-MM"), "yyyy-MM");
        }
        return HttpUtil.get(String.format(baiduApiUrl, date, resource_id), 30000);
    }


}
