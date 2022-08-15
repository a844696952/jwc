package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper;

import io.swagger.annotations.Api;
import lombok.Data;

@Api("年，月，日")
@Data
public class DatePaper {
    private Long year;
    private Long month;
    private Long day;
    private String yearMonthDay;
}
