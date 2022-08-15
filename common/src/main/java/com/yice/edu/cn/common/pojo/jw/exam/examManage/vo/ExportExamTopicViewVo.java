package com.yice.edu.cn.common.pojo.jw.exam.examManage.vo;


import com.yice.edu.cn.common.pojo.jy.topics.Topics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExportExamTopicViewVo extends Topics{
    
	@ApiModelProperty(value = "[0,30%]",dataType = "Integer")
	private Integer firstRate;
	@ApiModelProperty(value = "(30%,60%]",dataType = "Integer")
	private Integer secondRate;
	@ApiModelProperty(value = "(60%,80%]",dataType = "Integer")
	private Integer thirdRate;
	@ApiModelProperty(value = "(80%,100%]",dataType = "Integer")
	private Integer fourthRate;

}
