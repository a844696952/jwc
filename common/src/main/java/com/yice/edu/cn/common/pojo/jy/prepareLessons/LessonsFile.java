package com.yice.edu.cn.common.pojo.jy.prepareLessons;
import com.yice.edu.cn.common.pojo.Pager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
/**
*
*
*
*/
@Data
public class LessonsFile{
	@ApiModelProperty(value = "关联文件Id",dataType = "String")
    private String id;
	@ApiModelProperty(value = "文件名称",dataType = "String")
    private String fileName;//文件名称
	@ApiModelProperty(value = "图标地址",dataType = "String")
    private String iconPath;//图标地址
	@ApiModelProperty(value = "文件路径",dataType = "String")
    private String filePath;//文件路径
	@ApiModelProperty(value = "教案Id",dataType = "String")
    private String teachingPlanId;//教案Id
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
}
