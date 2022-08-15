package com.yice.edu.cn.common.pojo.jw.teacher;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("教师职务")
public class TeacherPost{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("删除标识")
    private Integer del;
    @ApiModelProperty("职务名称")
    private String name;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("字典表对应的id")
    private String ddId;
    @ApiModelProperty("年级id")
    private String gradeId;
    @ApiModelProperty("年级")
    private String gradeName;
    @ApiModelProperty("班级id")
    private String classId;
    @ApiModelProperty("职务排序")
    private Integer sort;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //非表名称
    @ApiModelProperty("教师名称")
    private String teacherName;
    @ApiModelProperty(value = "工号",dataType = "String")
    private String workNumber;
    @ApiModelProperty(value = "头像",dataType = "String")
    private String imgUrl;//头像
    @ApiModelProperty(value = "性别",dataType = "String")
    private String sex;//性别-取数据字典
    @ApiModelProperty("班级名称")
    private String className;
    @ApiModelProperty("应届年份")
    private Integer enrollYear;
}
