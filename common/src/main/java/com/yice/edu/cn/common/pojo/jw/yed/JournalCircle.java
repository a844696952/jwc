package com.yice.edu.cn.common.pojo.jw.yed;

import lombok.Data;

/**
 * 大屏中右上角日志的圈圈
 */
@Data
public class JournalCircle {
    private String text;
    private String num;
    private Boolean riseFlag;
    private Long count;
}
