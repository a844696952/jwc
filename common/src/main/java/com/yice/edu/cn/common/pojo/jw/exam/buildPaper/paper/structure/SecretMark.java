package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.structure;

import lombok.Data;

/**
 *
 * 保密标记
 *
 */
@Data
public class SecretMark{

    private  String fieldDisName;
    private  String fieldName;
    private  Boolean show;
    private  String text;

}
