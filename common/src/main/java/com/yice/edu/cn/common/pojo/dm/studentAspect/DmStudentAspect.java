package com.yice.edu.cn.common.pojo.dm.studentAspect;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("学生人脸特征")
public class DmStudentAspect{

    @ApiModelProperty("主键")
    private Integer id;

    @NotNull(groups= {GroupTwo.class},message="学生Id不能为空")
    @ApiModelProperty("学生Id")
    private String studentId;

    @NotNull(groups= {GroupTwo.class},message="学生姓名不能为空")
    @ApiModelProperty("学生姓名")
    private String studentName;

    @NotNull(groups= {GroupTwo.class},message="图片路径不能为空")
    @ApiModelProperty("图片路径")
    private String imagePath;

    @ApiModelProperty("班级Id")
    private String classId;

    @NotNull(groups= {GroupTwo.class},message="学校Id不能为空")
    @ApiModelProperty("学校")
    private String schoolId;
    @ApiModelProperty("特征码路径")
    private String featurePath;
    @ApiModelProperty("操作时间")
    private String operateDate;

    @NotNull(groups= {GroupTwo.class},message="脸部识别Id不能为空")
    @ApiModelProperty("脸部识别Id")
    private String faceId;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
