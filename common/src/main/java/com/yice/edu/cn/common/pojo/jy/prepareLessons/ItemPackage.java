package com.yice.edu.cn.common.pojo.jy.prepareLessons;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 
* @ClassName: ItemPackage  
* @Description: 题包  
* @author xuchang  
* @date 2018年11月30日
 */
@Data
public class ItemPackage {
	@ApiModelProperty(value = "题包主键",dataType = "String")	
	private String id;//id
	
	@ApiModelProperty(value = "题包名称",dataType = "String")
	private String name;//题包名称
	
	@ApiModelProperty(value = "章节Id",dataType = "String")
	@NotNull
	private String chapterId;//章节Id
	
	@ApiModelProperty(value = "教师Id",dataType = "String")
	private String teacherId;//教师Id
	
	@ApiModelProperty(value = "题目Id，多个用','隔开",dataType = "String")
	@NotNull
	private String topicIds;//题目Id，多个用','隔开
	
	@ApiModelProperty(value = "教案Id",dataType = "String")
	@NotNull
	private String teachingPlanId;//教案Id
	
	
	private String createTime;
	
	private String updateTime;
	
	
	//题目包
	@Transient
	private List<Topics> Topics;
	
	@Transient
	@NotNull
	@ApiModelProperty(value = "教案名称",dataType = "String")
	private String teachingPlanName;//教案名称
	
	//分页排序等
    @Transient
    @Valid
    private Pager pager;
	
}
