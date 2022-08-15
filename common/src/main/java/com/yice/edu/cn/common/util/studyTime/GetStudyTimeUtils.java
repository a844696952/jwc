package com.yice.edu.cn.common.util.studyTime;

import java.util.Calendar;

public class GetStudyTimeUtils {
    /**
     * 获取当前的学年,当前月份超过9月则为今年到明年,反之去年到今年
     */
    public static String getStudyTime() {
        String school_yera = "";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        if (month >= 9) {
            school_yera = year + "-" + (year + 1) + "学年";
        } else {
            school_yera = (year - 1) + "-" + year + "学年";
        }
        return school_yera;
    }
}
