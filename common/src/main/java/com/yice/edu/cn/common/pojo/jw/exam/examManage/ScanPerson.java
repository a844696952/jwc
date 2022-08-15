package com.yice.edu.cn.common.pojo.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("扫描员管理表")
public class ScanPerson{

    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "教师id  ",dataType = "String")
    private String teacherId;
    @ApiModelProperty(value = "教师姓名",dataType = "String")
    private String teacherName;
    @ApiModelProperty(value = "教师联系电话",dataType = "String")
    private String teacherTel;
    @ApiModelProperty(value = "教师班级id  ",dataType = "String")
    private String teacherClassesId;
    @ApiModelProperty(value = "教师在职状态（在职40/离职/管理员）",dataType = "String")
    private int teacherStatus;
    @ApiModelProperty(value = "年级名",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "班级名",dataType = "String")
    private String classesName;
    @ApiModelProperty(value = "课程表",dataType = "String")
    private String teacherClassesCourseId;
    @ApiModelProperty(value = "课程名",dataType = "String")
    private String courseName;
    @ApiModelProperty(value = "是否为扫描员 0/1是",dataType = "Integer")
    private int person;



    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    private String className;

    private List<ScanPerson> teachers;

    private List<String> teacherIds;
}
