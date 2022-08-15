package com.yice.edu.cn.common.pojo.jy.knowledgePoint;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*知识点表
*
*/
@Data
public class KnowledgePoint{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("科目id")
    private String subjectId;
    @ApiModelProperty("科目名称")
    private String subjectName;
    @ApiModelProperty("学段id")
    private String typeId;
    @ApiModelProperty("学段名称")
    private String typeName;
    @ApiModelProperty("知识点名称")
    private String name;
    @ApiModelProperty("题目数量")
    private Integer topicCount;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("父类id")
    private String parentId;
    @ApiModelProperty("子类数量")
    private Integer childNum;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    private Integer code; //作为添加或者更新返回校验码

    @Transient
    @ApiModelProperty(value = "创建时间-开始",dataType = "String")
    private String begin;
    @Transient
    @ApiModelProperty(value = "创建时间-结束",dataType = "String")
    private String end;
    @Transient
    @ApiModelProperty(value = "子类")
    private List<KnowledgePoint> children;
}
