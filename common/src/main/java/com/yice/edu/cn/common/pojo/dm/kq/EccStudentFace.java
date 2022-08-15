package com.yice.edu.cn.common.pojo.dm.kq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.validateClass.GroupFour;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author dengfengfeng
 * 云班牌录入的人脸
 */
@Data
@ApiModel("云班牌录入的人脸表")
public class EccStudentFace {
    @NotBlank(message = "人脸id不可为空",groups = {GroupTwo.class})
    @ApiModelProperty(value="id",dataType="String")
    private String id;
    @ApiModelProperty(value="学校id",dataType="String")
    private String schoolId;
    @ApiModelProperty(value="班级id",dataType="String")
    private String classesId;
    @ApiModelProperty(value="年级ID",dataType="String")
    private String gradeId;

    @NotBlank(message = "学生id不可为空",groups = {GroupOne.class})
    @ApiModelProperty(value="学生id",dataType="String")
    private String studentId;
    @ApiModelProperty(value="学生对象",dataType="Student")
    private Student student;
//    @NotBlank(message = "学生头像地址不可为空",groups = {GroupSeven.class})
    @ApiModelProperty(value="学生人脸图像",dataType="String")
    private String faceImg;

    @ApiModelProperty(value="人脸特征",dataType="String")
    private byte[] faceFeature;

    @ApiModelProperty(value="创建时间",dataType="String")
    private String createTime;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空",groups = {GroupThree.class, GroupFour.class})
    @Valid
    private Pager pager;




}
