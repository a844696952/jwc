package com.yice.edu.cn.common.pojo.jw.exam.examManage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("试率")
public class ExamRate {
    @ApiModelProperty(value = "低分",dataType = "Array")
    private Integer[] poor=new Integer[]{0,60};
    @ApiModelProperty(value = "及格",dataType = "Array")
    private Integer[] fair=new Integer[]{60,80};
    @ApiModelProperty(value = "良好",dataType = "Array")
    private Integer[] good=new Integer[]{80,90};
    @ApiModelProperty(value = "优秀",dataType = "Array")
    private Integer[] excellent=new Integer[]{90,100};
}
