package com.yice.edu.cn.common.pojo.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;


@Data
@ApiModel("宿舍人员入住信息")
public class DormPerson implements Serializable {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("宿舍id")
    private String dormId;
    @ApiModelProperty("入住时间")
    private String moveIntoTime;
    @ApiModelProperty("人员id")
    private String personId;
    @ApiModelProperty("人员类型(1.学生,2.教师,3.非教师职工)")
    private String personType;
    @ApiModelProperty("是否是宿舍长(0.否,1.是)")
    private String isDormLeader;
    @ApiModelProperty("备注")
    private String remarks;
    @ApiModelProperty("床位名称")
    private String bunkName;
    @ApiModelProperty("床位顺序")
    private Integer bunkSort;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("冗余字段")
    private String otherWord;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    /*------------------------------------*/
    @Transient
    @ApiModelProperty("主键ids")
    private List<String> ids;
    @Transient
    @ApiModelProperty("人员ids")
    private List<String> personIds;
    @Transient
    @ApiModelProperty("人员姓名")
    private String personName;
    @Transient
    @ApiModelProperty("人员性别")
    private String personSex;
    @Transient
    @ApiModelProperty("人员年级")
    private String gradeName;
    @Transient
    @ApiModelProperty("班级id")
    private String classesId;
    @Transient
    @ApiModelProperty("班号")
    private String number;
    @Transient
    @ApiModelProperty("手机号")
    private String personTel;
    @Transient
    @ApiModelProperty("调宿人员id")
    private String adjustPersonId;
    @Transient
    @ApiModelProperty("学号")
    private String studentNo;
    @Transient
    @ApiModelProperty("图像")
    private String imgUrl;
    @Transient
    @ApiModelProperty("班主任")
    private String teacherName;
    @Transient
    @ApiModelProperty("班主任电话")
    private String teacherTel;
    @Transient
    @ApiModelProperty("宿舍申请id")
    private String houseApplicanId;
    @Transient
    @ApiModelProperty("员工工号")
    private String workNumber;
    @Transient
    @ApiModelProperty("所在部门字符串")
    private String DepartmentTeacherStr;
    @Transient
    @ApiModelProperty("是否已住宿,0.已住宿,1.未住宿")
    private String isDorm;


}
