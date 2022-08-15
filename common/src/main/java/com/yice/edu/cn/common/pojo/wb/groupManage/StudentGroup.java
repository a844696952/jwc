package com.yice.edu.cn.common.pojo.wb.groupManage;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("学生分组")
public class StudentGroup{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("学生id")
    private String studentId;
    @ApiModelProperty("小组id")
    private String groupId;
    @ApiModelProperty("班级id")
    private String classesId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
