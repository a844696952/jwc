package com.yice.edu.cn.common.pojo.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学生出入校状态变更记录表")
public class StuStatusChange{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("学生Id")
    private String studentId;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("变更时间")
    private String changeTime;
    @ApiModelProperty("变更后的状态")
    private String status;
    @ApiModelProperty("班级ID")
    private String classId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("创建日期")
    private String createDate;


    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
