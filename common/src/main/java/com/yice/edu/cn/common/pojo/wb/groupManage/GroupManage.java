package com.yice.edu.cn.common.pojo.wb.groupManage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel("分组管理")
public class GroupManage{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("小组序号")
    private Integer groupNumber;
    @ApiModelProperty("小组名称")
    private String groupName;
    @ApiModelProperty("小组类型：1一般，2中等，3良好，4优秀")
    private String groupType;
    @ApiModelProperty("小组人数")
    private String peopleNumber;
    @ApiModelProperty("创建时间")
    private String createDate;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("班级id")
    private String classesId;
    @ApiModelProperty("创建时间")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    //起始时间
    private String startTime;
    //结束时间
    private String endTime;


    @JsonIgnore
    private List<StudentGroup> studentGroups;

    private JwClasses jwClasses;

    private String[] studentIds;

    private List<Student> students;

}
