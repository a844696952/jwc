package com.yice.edu.cn.common.pojo.cc.cloudCourse;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("云课堂子课程表")
public class CloudSubCourse{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("父课程")
    private CloudCourse cloudCourse;
    @ApiModelProperty("子课程名称")
    private String name;
    @ApiModelProperty("课程开始时间")
    private String startTime;
    @ApiModelProperty("课程结束时间")
    private String endTime;
    @ApiModelProperty("授课教师")
    private Teacher teacher;
    @ApiModelProperty("课程状态( 1->待上课 2->上课中 3->已结束)")
    private Integer status;
    @ApiModelProperty("结束类型( 1->正常 2->异常)")
    private Integer endType;
    @ApiModelProperty("学校")
    private String schoolId;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("直播码")
    private String broadcastCode;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    @ApiModelProperty("登陆人id")
    private String loginId;

    @Transient
    @ApiModelProperty("课件数量")
    private long coursewareNum;
}
