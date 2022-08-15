package com.yice.edu.cn.common.pojo.wb.latticePager;


import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

import java.util.List;

@Data
@ApiModel("点阵试卷页码表")
public class DmPagerNumber extends CurSchoolYear{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("试卷名称")
    private String name;
    @ApiModelProperty("页码管理用逗号分隔")
    private String pagerNumber;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("试卷id")
    private String latticeId;
    @ApiModelProperty("更新时间")
    private String modifyTime;
    @ApiModelProperty("是否回收 1 已回收  0 没有回收")
    private Integer isRecycle;

    @ApiModelProperty("教师id")
    private String teacherId;

    private List<String> searchTime;
}
