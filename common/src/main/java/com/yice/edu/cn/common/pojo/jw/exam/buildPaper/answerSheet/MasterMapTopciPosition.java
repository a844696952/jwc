package com.yice.edu.cn.common.pojo.jw.exam.buildPaper.answerSheet;

import io.swagger.annotations.Api;
import lombok.Data;

import java.util.List;

@Api("交给图片识别的数据坐标和图片")
@Data
public class MasterMapTopciPosition {

    private List<PostilTopciPosition> ptpList;
    //原卷
    private List<String> imgList;
    private List<String> newImgList;
}
