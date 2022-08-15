package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper;

import io.swagger.annotations.Api;
import lombok.Data;

@Api("每天的试卷数量")
@Data
public class PaperDayMonth {
    private DatePaper id;
    private Long count;
}
