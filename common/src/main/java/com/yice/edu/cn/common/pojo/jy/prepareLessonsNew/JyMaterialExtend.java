package com.yice.edu.cn.common.pojo.jy.prepareLessonsNew;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

@Data
@ApiModel("教材扩展")
public class JyMaterialExtend {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("教材id")
    private String materialId;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("教材类型（1学校，2教师）")
    private Integer type;
    @ApiModelProperty("逻辑删除标志（0删除，1未删除）")
    private Integer deleteStatus;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页
    @Transient
    private Pager pager;

    private Material material;

    //接收年级条件
    private String gradeType;
    //接收科目名称
    private String subjectName;
}
