package com.yice.edu.cn.common.pojo.jw.classes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.yice.edu.cn.common.pojo.validateClass.GroupSeven;
import com.yice.edu.cn.common.pojo.validateClass.GroupSix;
import org.springframework.data.annotation.Transient;

import com.yice.edu.cn.common.pojo.Pager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

/**
*
*班级信息
*
*/
@ApiModel(value = "班级信息",description="客户端保存班级对象时,需要id,number,gradeId,gradeName,schoolId,enrollYear字段")
@Data
public class JwClasses{

    @ApiModelProperty(value = "主键id",dataType = "String")
    @NotBlank(message = "班级id不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String id;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "删除标识",dataType = "String")
    private String del;
    @ApiModelProperty(value = "班号",dataType = "String")
    private String number;
    @ApiModelProperty(value = "备注",dataType = "String")
    private String remarks;
    @ApiModelProperty(value = "年级",dataType = "String")
    @NotBlank(message = "年级id不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String gradeId;
//    @NotBlank(message = "班级id",groups = {GroupSix.class, GroupSeven.class})
    private String classesId;
    @ApiModelProperty(value = "年级名称",dataType = "String")
    @NotBlank(message = "年级名称不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String gradeName;
    @ApiModelProperty(value = "学校id",dataType = "String")
    @NotBlank(message = "学校id不能为空",groups ={GroupSix.class, GroupSeven.class})
    private String schoolId;
    @ApiModelProperty(value = "班级应届年份",dataType = "String")
    @NotBlank(message = "班级应届年份不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String enrollYear;
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;

    @ApiModelProperty(value = "班主任",dataType = "String")
    private String headTeacher;
    @ApiModelProperty(value = "班级学生人数",dataType = "String")
    private String studentCount;

    @ApiModelProperty(value = "学生状态",dataType = "String")
    private String studentStu;
    @ApiModelProperty(value = "设备",dataType = "String")
    private String equipmentIp;
    @ApiModelProperty(value = "班牌id",dataType = "String")
    private String classCardId;
    @ApiModelProperty(value = "锁屏状态",dataType = "String")
    private String lockStatus;
    @ApiModelProperty(value = "班牌用户名",dataType = "String")
    private String userName;
    @ApiModelProperty(value = "班主任",dataType = "String")
    private String teacherId;

    @ApiModelProperty("年段levelTypeId")
    private Integer levelType;
    @ApiModelProperty("班级个数所占百分比")
    private String classesParcent;
    @ApiModelProperty("当前职务")
    private String postValue;
}
