package com.yice.edu.cn.common.pojo.mes.schoolManage.institution;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;


@Data
@ApiModel("制度表")
public class MesInstitution{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("制度名称")
    private String name;
    @ApiModelProperty("等级 1- 一级制度 2--二级制度")
    private Integer level;
    @ApiModelProperty("父id")
    private String parentId;
    @ApiModelProperty("分数")
    private Integer score;
    @ApiModelProperty("排序编号")
    private Integer sortNumber;
    @ApiModelProperty("关联时间状态表(未发布时为-1)")
    private String timeStatusId;
    @ApiModelProperty("学段 0--小学 1--中学 2--高中")
    private Integer schoolRange;
    @ApiModelProperty("子制度")
    List<MesInstitution> children;
    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("用户ID")
    private List<String> userIds;
    @ApiModelProperty("用户类型 0--老师 1--学生")
    private Integer userType;
    private Integer isShow;//学校评比周评比table页是否已设置学周  0:展示未设置学周的页面，1:展示列表页

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
