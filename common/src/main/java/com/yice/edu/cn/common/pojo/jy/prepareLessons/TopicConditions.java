package com.yice.edu.cn.common.pojo.jy.prepareLessons;

import javax.validation.constraints.NotNull;


import com.yice.edu.cn.common.pojo.Pager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
* @ClassName: TopicConditions  
* @Description: 题包查询条件 
* @author xuchang  
* @date 2018年11月30日
 */
@Data
public class TopicConditions {
	
	@ApiModelProperty(value = "多个题目类型,用逗号隔开",dataType = "String")	
    private String typeIds;//多个题目类型 查询
	
	@ApiModelProperty(value = "内容，模糊查询",dataType = "String")	
    private String content;
    
    @NotNull
    @ApiModelProperty(value = "章节Id",dataType = "String")	
    private String chapterId;
    
    @ApiModelProperty(value = "题目Id",dataType = "String")	
    private String sqIds;// 多个用,隔开
    
    private Pager pager;
}
