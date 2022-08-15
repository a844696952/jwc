package com.yice.edu.cn.common.pojo.xw.psycholgConsult;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*
*
*/
@Data
public class XwPshcholgConsult{

    @ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    @ApiModelProperty(value = "学生姓名",dataType = "String")
    private String name;
    @ApiModelProperty(value = "性别 男4 女5",dataType = "String")
    private String sex;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classId;
    @ApiModelProperty(value = "生日",dataType = "String")
    private String birthday;
    @ApiModelProperty(value = "电话",dataType = "String")
    private String tel;
    @ApiModelProperty(value = "联系方式",dataType = "String")
    private String qq;
    @ApiModelProperty(value = "下次咨询时间",dataType = "String")
    private String reConsultTime;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "咨询问题",dataType = "String")
    private String consultQuestion;
    @ApiModelProperty(value = "咨询记录",dataType = "String")
    private String consultRecord;
    @ApiModelProperty(value = "咨询对策",dataType = "String")
    private String consultDession;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "教师id",dataType = "String")
    private String teacherId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //其他字段
    private String[] searchTime;
    private String searchStartTime;
    private String searchEndTime;
    private String gradeClassName;//班级名称
    private String gradeName;//年级
    private String className;//班号
    //学生端心理老师所需要的数据

    private String tid;

    private String tqq;

    private String tweixin;

    private String temail;

    private String timgUrl;

    private String tname;

    private String ttel;

    private String tsex;

    private String tregisterStatus;

    private List<Teacher> listTeacher;
}
