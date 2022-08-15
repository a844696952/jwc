package com.yice.edu.cn.common.pojo.jw.classSchedule;

import lombok.Data;

import java.util.List;

/**
 * 导入传参类
 *
 */
@Data
public class ImportClassSchedule {
    private List<Schedule> schedules;
    private List<String> error;
    private String gradeId;
    private String classesId;
    private String schoolId;
    private String scheduleId;

    private List<List<String>> lists;
    private Integer count;
}
