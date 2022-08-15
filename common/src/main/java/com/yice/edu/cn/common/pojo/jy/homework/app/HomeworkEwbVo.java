package com.yice.edu.cn.common.pojo.jy.homework.app;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document
public class HomeworkEwbVo {
    /**
     * 状态（0：全部，1：今天，2：昨天，3：近三天,4:条件搜索）
     */
    @ApiModelProperty(value = "状态",dataType = "Integer")
    private Integer type;
    @ApiModelProperty(value = "发布开始时间",dataType = "String")
    private String startTime;
    @ApiModelProperty(value = "发布结束时间",dataType = "String")
    private String endTime;

    @ApiModelProperty(value = "班级id",dataType = "String")
    private String classesId;
    @ApiModelProperty(value = "科目id",dataType = "String")
    private String subjectId;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;


    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
}
