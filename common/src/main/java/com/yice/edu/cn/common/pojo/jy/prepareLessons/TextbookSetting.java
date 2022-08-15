package com.yice.edu.cn.common.pojo.jy.prepareLessons;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;

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
public class TextbookSetting{
	@ApiModelProperty(value = "教材设定主键",dataType = "String")
    private String id;
	@ApiModelProperty(value = "教材id",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="教材id不能为空")
    private String textbookId;//教材id
	@ApiModelProperty(value = "老师id",dataType = "String")
    private String teacherId;
	@ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;
	@ApiModelProperty(value = "教材名称",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="教材名称不能为空")
    private String textbookName;//教材名称
	@ApiModelProperty(value = "图标路径",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="图标路径不能为空")
    private String iconPath;//图标路径
	@ApiModelProperty(value = "所属科目年级表id",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="所属科目年级表id不能为空")
    private String subjectMaterialId;
	@ApiModelProperty(value = "科目名称",dataType = "String")
    private String subjectName;
	
	private String createTime;
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
}
