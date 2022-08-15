package com.yice.edu.cn.common.pojo.jw.electiveCourse;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("选修班级关联表")
public class ElectiveClasses extends CurSchoolYear {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("选修id")
    private String electiveId;
    @ApiModelProperty("班级id")
    private String classesId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("年级id")
    private Integer gradeId;
    @ApiModelProperty("年级名字")
    private String gradeName;
    @ApiModelProperty("班级名字")
    private String classesName;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
