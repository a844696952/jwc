package com.yice.edu.cn.common.pojo.jy.prepareLessons;


import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.yice.edu.cn.common.pojo.jy.topics.Topics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TopicCart{

	@ApiModelProperty(value = "主键",dataType = "String")
    private String id;
    
	@ApiModelProperty(value = "教案Id",dataType = "String")
	@NotNull
    private String teachingPlanId;
	 
	@ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
	 
	@ApiModelProperty(value = "题目Id",dataType = "String")
    @NotNull
    private String topicId;
    
    //用于查询结果返回
    @Transient
    @ApiModelProperty(value = "题目集合",dataType = "String")
	private List<Topics> Topics;
    
    //用于批量保存
    @Transient
    @ApiModelProperty(value = "题目Id，用于批量保存",dataType = "String")
    private String topicIds;
    
}
