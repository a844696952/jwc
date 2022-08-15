package com.yice.edu.cn.common.pojo.dm.schoolActive;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@ApiModel("活动报名表")
public class DmActivitySiginUp{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("活动ID")
    private String activityId;
    @ApiModelProperty("项目ID")
    private String itemId;
    @ApiModelProperty("学生ID")
    private String studentId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("学校ID")
    private String schoolId;
    @ApiModelProperty("班级ID")
    private String classesId;







    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


    private String name;    //学生姓名
    private String imgUrl;  //头像地址
}
