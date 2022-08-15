package com.yice.edu.cn.common.pojo.jw.schoolYear;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class CurSchoolYear {
    /**
     * 被继承时要用到的三个字段
     */
    @ApiModelProperty("当前学年的id")
    private String schoolYearId;
    @ApiModelProperty("学年")
    private String fromTo;
    @ApiModelProperty("0表示上学期,1下学期")
    private Integer term;


    //分页
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
