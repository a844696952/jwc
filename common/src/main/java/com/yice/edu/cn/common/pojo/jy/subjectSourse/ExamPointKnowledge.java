package com.yice.edu.cn.common.pojo.jy.subjectSourse;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*考点和知识点的中间表
*
*/
@Data
public class ExamPointKnowledge{

    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "外键考点表id",dataType = "String")
    private String examPointId;
    @ApiModelProperty(value = "知识点id",dataType = "String")
    private String knowledgePointId;
    @ApiModelProperty(value = "考点表的path",dataType = "String")
    private String examPointPath;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    
    //知识点名称
    private String knowledgeName;
}
