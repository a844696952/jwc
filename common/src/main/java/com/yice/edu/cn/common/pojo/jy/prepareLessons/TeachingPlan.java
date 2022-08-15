package com.yice.edu.cn.common.pojo.jy.prepareLessons;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;

import java.util.List;

import javax.validation.Valid;
/**
*
*
*
*/
@Data
public class TeachingPlan{
	
	@ApiModelProperty(value = "教案主键",dataType = "String")
	@NotNull(groups= {GroupTwo.class},message="教案Id不能为空")
    private String id;
	@ApiModelProperty(value = "学校Id",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="学校Id不能为空")
    private String schoolId;
	
	@ApiModelProperty(value = "教师Id",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="教师Id不能为空")
    private String teacherId;
	
	@ApiModelProperty(value = "教材Id",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="教材Id不能为空")
    private String textbookId;//教材id
	
	@ApiModelProperty(value = "教材名称",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="教材名称不能为空")
    private String textbookName;//教材名称
	
	
	@ApiModelProperty(value = "章节Id",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="章节Id不能为空")
    private String chapterId;//章节Id
	
	@ApiModelProperty(value = "章节名称",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="章节名称不能为空")
    private String chapterName;//章节名称
	
	@ApiModelProperty(value = "课时名称",dataType = "String")
	@NotNull(groups= {GroupOne.class},message="课时名称不能为空")
	private String courseName;//课时名称
	
	@ApiModelProperty(value = "教学目标",dataType = "String")
    private String teachTarget;//教学目标
	@ApiModelProperty(value = "教学要点",dataType = "String")
    private String tearchPoint;//教学要点
	@ApiModelProperty(value = "教学方法",dataType = "String")
    private String tearchMethod;//教学方法
	@ApiModelProperty(value = "教学难点",dataType = "String")
    private String teachDifficulty;//教学难点
	@ApiModelProperty(value = "教学过程",dataType = "String")
    private String tearchProcess;//教学过程
	@ApiModelProperty(value = "题包id,多个用逗号隔开",dataType = "String")
    private String itemPackageIds;//题包id,多个用逗号隔开
	@ApiModelProperty(value = "下载次数",dataType = "Integer")
    private Integer downloadCount;//下载次数
	@ApiModelProperty(value = "查看次数",dataType = "Integer")
    private Integer viewCount;//查看次数
	@ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;//创建时间
	@ApiModelProperty(value = "更新时间",dataType = "String")
    private String updateTime;//更新时间
	@ApiModelProperty(value = "分享状态",dataType = "Integer")
	private Integer shareStatus;//分享状态,0:个人;1:校本
	
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
    
    @Transient
    private List<LessonsFile>  lessonsFiles;
    
    @Transient
    private List<ItemPackage>  itemPackages;
    
    @Transient
    private String lessonsFileIds;//题包文件id,多个用逗号隔开

}
