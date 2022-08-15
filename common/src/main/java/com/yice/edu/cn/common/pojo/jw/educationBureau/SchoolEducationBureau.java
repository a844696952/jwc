package com.yice.edu.cn.common.pojo.jw.educationBureau;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
*
*学校教育局关联表
*
*/
@Data
public class SchoolEducationBureau{

    private String id;
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "教育局id",dataType = "String")
    @NotNull(groups = GroupOne.class,message ="教育局id不能为空" )
    private String educationBureauId;
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
    //额外字段
    @NotNull(groups = GroupOne.class,message ="学校id列表不能为空" )
    private List<String> schoolIds;
}
