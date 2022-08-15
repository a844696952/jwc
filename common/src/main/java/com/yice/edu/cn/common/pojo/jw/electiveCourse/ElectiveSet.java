package com.yice.edu.cn.common.pojo.jw.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("选修课设置")
public class ElectiveSet extends CurSchoolYear {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("年级id")
    private String gradeId;
    @ApiModelProperty("必选课程数量")
    private Integer minNum;
    @ApiModelProperty("最大数量")
    private Integer maxNum;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;


}
