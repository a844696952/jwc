package com.yice.edu.cn.common.pojo.dm.teacher;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

@Data
@ApiModel("名师风采表")
public class DmFamousTeacher {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("教师ID")
    private String teacherId;
    @ApiModelProperty("教师简介")
    private String teacherDesc;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    /**
     *  教师姓名
     * */
    private String name;

    /**
     *  教师姓名
     * */
    private String imgUrl;
}
