package com.yice.edu.cn.common.pojo.jy.prepareLessonsNew;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;

import java.util.List;

@Data
@ApiModel("自定义章节")
public class JySubjectMaterialExtend {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("父节点id")
    private String parentId;
    @ApiModelProperty("数据字典表id")
    private String ddId;
    @ApiModelProperty("年段id")
    private String annualPeriodId;
    @ApiModelProperty("树级别")
    private Integer level;
    @ApiModelProperty("是否是叶子节点(1是 2.否）")
    private Integer leaf;
    @ApiModelProperty("path")
    private String path;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("创建用户id，也作为教师id标识")
    private String createUserId;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("修改用户id")
    private String updateUserId;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("新增章节类型（1学校，2教师）")
    private Integer type;
    @ApiModelProperty("逻辑删除标志（0删除，1未删除，暂不用）")
    private Integer deleteStatus;
    @ApiModelProperty("教材id，章节关联教材")
    private String materialId;
    //分页
    @Transient
    private Pager pager;

    private List<JySubjectMaterialExtend> children;
}
