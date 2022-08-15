package com.yice.edu.cn.common.pojo.jy.resourceCenter;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class TeacherCourse {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("教师姓名")
    private String name;
    @ApiModelProperty("头像")
    private String imgUrl;
    @ApiModelProperty("电话")
    private String tel;
    @ApiModelProperty("课程名称")
    private String courseName;
    @ApiModelProperty("学校id")
    private String schoolId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

}
