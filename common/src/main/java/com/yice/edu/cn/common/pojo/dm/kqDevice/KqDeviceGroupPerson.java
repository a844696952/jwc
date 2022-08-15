package com.yice.edu.cn.common.pojo.dm.kqDevice;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Map;


@Data
@ApiModel("考勤设备分组人员绑定表")
public class KqDeviceGroupPerson{

    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("分组id")
    private String deviceGroupId;
    @ApiModelProperty("人员id")
    private String personId;
    @ApiModelProperty("人员类型(1教师，2学生，3其他职工)")
    private String personType;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("factory zy:1 yc:0")
    private String factoryType;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    @Transient
    @ApiModelProperty("快速绑定用,(1教师，2学生，3其他职工)")
    private List<String> fastBound;
    @Transient
    @ApiModelProperty("预绑定人员id数组")
    private List<String> personIdList;
    @Transient
    @ApiModelProperty("预绑定人员的设备编号列表")
    private List<String> deviceNoList;

    //查询用字段
    @ApiModelProperty("gradeId年级id")
    private String gradeId;//年级id
    @ApiModelProperty(value = "classesId班级id",dataType = "String")
    private String classesId;//班级di
    @ApiModelProperty(value = "name学生姓名",dataType = "String")
    private String name;//姓名
    @ApiModelProperty(value = "workNumber工号",dataType = "String")
    private String workNumber;
    @ApiModelProperty("departmentId职工所属部门id")
    private String departmentId;
    @ApiModelProperty("预绑定人员id数组和人员类型的map")
    private Map<String,List<String>> mapForPersonIdListByType;
}
