package com.yice.edu.cn.common.pojo.mes.classManage.rules;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("德育小程序制度表全网适用，每一个学校都是一样的制度")
public class MesAppletsRule {

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("班规制度名称")
    private String name;
    @ApiModelProperty("班规制度描述")
    private String description;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("应用类型 0--默认 1--推荐")
    private Integer appType;
    @ApiModelProperty("标签类型 0--待改进 1--表扬")
    private Integer tagType;
    @ApiModelProperty("分值")
    private Integer score;
    @ApiModelProperty("父Id")
    private String parentId;
    @ApiModelProperty("级别 1-一级 2--二级")
    private Integer level;
    @ApiModelProperty("排序编号")
    private Integer sortNumber;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
     //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;



    private List<MesAppletsRule> children;
    private long totalClass;                //所有购买该模块的班级数
    private long referenceClassNum;         //引用该制度的班级数
    private long clickTimes;                //点击次数
    private String beginTime;
    private String endTime;


    /*
    * 1 向上移动 2是向下移动
    * **/
    private String moveStatus;
}
