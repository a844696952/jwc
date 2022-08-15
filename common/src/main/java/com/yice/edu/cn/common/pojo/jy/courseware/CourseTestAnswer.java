package com.yice.edu.cn.common.pojo.jy.courseware;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupFive;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("课堂检测资源对应的答案")
public class CourseTestAnswer{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("课堂检测资源id")
    @NotBlank(message = "课堂检测资源id不可为空",groups = {GroupOne.class})
    private String testId;
    @ApiModelProperty("答案")
    @NotBlank(message = "答案不可为空",groups = {GroupFive.class})
    private String answer;
    @ApiModelProperty("题号")
    @NotNull(message = "题号不可为空",groups = {GroupFive.class})
    private Integer qid;
    //分页排序等
    @Transient
//    @NotNull(message = "pager不能为空")
//    @Valid
    private Pager pager;
}
