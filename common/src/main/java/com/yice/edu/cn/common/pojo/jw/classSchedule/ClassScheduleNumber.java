package com.yice.edu.cn.common.pojo.jw.classSchedule;

import io.swagger.annotations.Api;
import lombok.Data;

import java.util.List;

@Api("保存课时节数和午休位子")
@Data
public class ClassScheduleNumber {
    private List<ClassScheduleInit> classScheduleInits;
    private Integer number;
}
