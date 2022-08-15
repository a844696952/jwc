package com.yice.edu.cn.common.pojo.jw.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JwClassesHistory extends JwClasses{
	    @ApiModelProperty("当前学年的id")
	    private String schoolYearId;
	    @ApiModelProperty("学年")
	    private String fromTo;
	    @ApiModelProperty("0表示上学期,1下学期")
	    private int term;
}
