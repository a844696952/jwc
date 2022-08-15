package com.yice.edu.cn.common.pojo.jw.educationBureau;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
/**
*
*教育局
*
*/
@Data
public class EducationBureau{

    private String id;
    @ApiModelProperty(value = "名称",dataType = "String")
    private String name;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;

}
