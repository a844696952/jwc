package com.yice.edu.cn.common.pojo.jw.auth;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
/**
*
*教师角色关联
*
*/
@Data
public class TeacherRole{

    private String id;
    private String teacherId;//关联tearcher的id
    private String roleId;//关联role id
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
    private String roleIds;
    private String[] teacherArr;
    private String[] roleArr;


}
