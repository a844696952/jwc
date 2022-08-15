package com.yice.edu.cn.common.pojo.jw.student;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 *
 *学生信息
 *
 */
@Data
@ApiModel(value = "学生",description = "客户端上传数据时,需要id,name,studentCode,classesId,enrollYear,schoolId,examNo,classesNumber,gradeId,gradeName字段")
public class Student implements Serializable {
    private static final long serialVersionUID = 1001871195338770382L;
    @ApiModelProperty(value = "主键id",dataType = "String")
    @NotBlank(message = "学生id不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String id;//id

    @NotBlank(message = "姓名不能为空",groups = {GroupSix.class, GroupSeven.class,GroupTwo.class,GroupThree.class, GroupFive.class})  //GroupTwo 添加  GroupThree修改
    @Excel(name = "* 学生姓名",isImportField = "true_student")
    @ApiModelProperty(value = "学生姓名",dataType = "String")
    private String name;//姓名

    @ApiModelProperty(value = "学生性别（男_4,女_5）",dataType = "String")
    @Excel(name = "* 性别",replace = {"男_4","女_5"})
    @Range(min = 4,max = 5,message = "性别必须在4/5",groups = {GroupTwo.class,GroupThree.class})
    private String sex;//性别

    @ApiModelProperty(value = "学生监护人联系方式",dataType = "String")
    @NotBlank(message = "监护人联系方式不能为空",groups = {GroupTwo.class,GroupThree.class,GroupFive.class})
    @Pattern(regexp = "^1\\d{10}$",message = "监护人联系方式必须为11位手机号码",groups = {GroupTwo.class,GroupThree.class})
    @Excel(name = "* 监护人联系方式-手机号",isImportField = "true_student")
    private String guardianContact;//监护人联系方式

    @ApiModelProperty(value = "学生头像",dataType = "String")
    private String imgUrl;//头像

    @ApiModelProperty(value = "学籍号",dataType = "String")
    @Excel(name="学籍号",orderNum = "1")
    private String studentCode;//学籍号

    @ApiModelProperty(value = "学生邮箱",dataType = "String")
    @Email(message = "邮箱格式错误",groups = {GroupTwo.class,GroupThree.class})
    @Excel(name="邮箱",orderNum = "1")
    private String email;//邮箱

    @ApiModelProperty(value = "学生国籍",dataType = "String")
    @NotBlank(message = "国籍不能为空",groups = {GroupTwo.class,GroupThree.class})
    @Excel(name = "* 国籍",replace = {"中国_1","中国(港澳台)_2","国外_3"},isImportField = "true_student")
    private String nationality;//国籍

    @ApiModelProperty(value = "民族id",dataType = "String")
    private String nation;//民族

    @ApiModelProperty(value = "民族名称",dataType = "String")
    @Excel(name="民族名称",orderNum = "1")
    private String nationName;//民族名称

    @ApiModelProperty(value = "籍贯",dataType = "String")
    @Excel(name="籍贯",orderNum = "1")
    private String nativePlace;//籍贯

    @ApiModelProperty(value = "省份id",dataType = "String")
    private String provinceId;//省id
    @ApiModelProperty(value = "(家庭住址所在)省份",dataType = "String")
    @Excel(name = "省份名称",orderNum = "1")
    private String provinceName;//省份名称
    @ApiModelProperty(value = "城市id",dataType = "String")
    private String cityId;//市id
    @ApiModelProperty(value = "(家庭住址所在)城市",dataType = "String")
    @Excel(name="城市名称",orderNum = "1")
    private String cityName;// 城市名称
    @ApiModelProperty(value = "区域id",dataType = "String")
    private String countyId;//区/县id
    @ApiModelProperty(value = "(家庭住址所在)区县",dataType = "String")
    @Excel(name="区县的名称",orderNum = "1")
    private String countyName;//区/县名称
    @ApiModelProperty(value = "(家庭住址所在)详细地址",dataType = "String")
    @Excel(name="家庭住址的详细地址",orderNum = "1")
    private String address;//详细地址
    @Range(min = 6,max = 8,message = "政治面貌为群众，团员，党员",groups = {GroupTwo.class,GroupThree.class})
    @Excel(name = "政治面貌",replace = {"群众_6","团员_7","党员_8","_null"},orderNum = "1")
    @ApiModelProperty(value = "政治面貌（群众_6，团员_7，党员_8）",dataType = "String")
    private String politicsFace;//政治面貌
    @Pattern(regexp="^[0-9]{4}-[0-9]{2}-[0-9]{2}$",message="出生日期格式不正确",groups = {GroupTwo.class,GroupThree.class})
    @Excel(name="出生日期",orderNum = "1")
    @ApiModelProperty(value = "出生日期",dataType = "String")
    private String birthday;//出生日期

    @Excel(name="* 学号")
    @ApiModelProperty(value = "学号",dataType = "String")
    @NotBlank(message = "学号不能为空",groups = {GroupSix.class,GroupSeven.class})
    private String studentNo;//学号

    @Pattern(regexp="^[0-9]{4}-[0-9]{2}-[0-9]{2}$",message="入学时间格式不正确",groups = {GroupTwo.class,GroupThree.class})
    @NotBlank(message = "入学时间不能为空",groups = {GroupTwo.class,GroupThree.class})
    @Excel(name="* 入学时间")
    @ApiModelProperty(value = "入学时间",dataType = "String")
    private String admissionDate;//入学时间


    @ApiModelProperty(value = "年级id",dataType = "String")
    @NotBlank(message = "年级id不能为空",groups = {GroupSix.class, GroupSeven.class,GroupTwo.class,GroupThree.class})
    private String gradeId;//年级id

    @ApiModelProperty(value = "年级名称",dataType = "String")
    @NotBlank(message = "年级不能为空",groups = {GroupSix.class, GroupSeven.class,GroupTwo.class,GroupThree.class})
    @Excel(name="* 年级")
    private String gradeName;//年级名称

    @ApiModelProperty(value = "班级id",dataType = "String")
     @NotBlank(message = "班级不能为空",groups = {GroupSix.class, GroupSeven.class,GroupTwo.class,GroupThree.class})
    private String classesId;//班级di
    @ApiModelProperty(value = "班号",dataType = "String")
    @Excel(name="* 班号")
    @NotBlank(message = "学生班号不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String classesNumber;//班号

    @ApiModelProperty(value = "学生状态（在读_50,留级_51,休学_52,转校_53,其他_54）",dataType = "String")
    @NotBlank(message = "状态不能为空",groups = {GroupTwo.class,GroupThree.class})
    @Excel(name="* 状态",replace = {"在读_50","留级_51","休学_52","转校_53","其他_54"})
    private String status;//学生状态
    private String cardNumber;//ic卡号
    private String createTime;//创建时间
    private String updateTime;//修改时间

    @ApiModelProperty(value = "注册状态（0：未注册，1：已经注册）",dataType = "String")
    private String registerStatus;

    @ApiModelProperty(value = "班级应届年份",dataType = "String")
    @Excel(name="* 入学年份")
    @NotBlank(message = "学生入学年份不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String enrollYear;//班级应届年份
    @ApiModelProperty(value = "删除标志，（1：未删除，2：已删除）",dataType = "String")
    private String del;//删除标志
    @ApiModelProperty(value = "学校",dataType = "String")
    @NotBlank(message = "学校id不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String schoolId;//学校
    @ApiModelProperty(value = "座位号",dataType = "Integer")
    @NotNull(message = "学生座位号不能为空",groups = {GroupSix.class, GroupSeven.class})
    private Integer seatNumber;
    @ApiModelProperty(value = "中移对接学生头像录入状态  (0: 成功, 1: 失败)",dataType = "String")
    private String zyCheckStatus;  //中移对接头像录入状态
    @ApiModelProperty("当前状态  在校/请假(在校)/请假(离校)/离校  0/1/2/3  默认2请假（离校）")
    private String nowStatus;
    @ApiModelProperty("是否住宿  ( 0:是 1:否) ")
    @Excel(name = "* 住宿",replace = {"是_0","否_1","_null"})
    private String boarder;

    @ApiModelProperty(value = "职务Id",dataType = "String")
    private String postId;
    @ApiModelProperty(value = "密码",dataType = "String")
    private String password;
    @Transient
    private String contactWay; //家长联系方式
    private String title;//学生通讯记录的标题
    private String teacherId;//教师id
    @Transient
    private List<Student> children;//学生通讯记录的具体信息


    @Transient
    private List<StudentFamily> studentFacmilyL;//学生家庭通讯记录的具体信息
    private Long count;//班级人数
    private String parentName;//家长姓名
    private String parentContact;//家长联系方式
    private String relation;//家长和学生的关系
    @Transient
    private String schoolName;    //学校名字
    private String schoolProvinceName;         //学校省名称
    private String schoolCityName;        //学校市名称
    private String schoolDistrictName;  //区县名字
    private String typeId;        //
    private String typeName;      //学段
    private String outing ;       //学校性质
    private String prsnAvtrUrlAddr;//中移校验图片文件(二进制字符串)
    private Integer isSignUp;    //校活动项目是否报名
    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
    //返回提示
    @Transient
    private String code;
    @Transient
    private String msg;
    @Transient
    private String type;//分辨学生类型

    @ApiModelProperty(value = "家长app端亲属关系" ,dataType = "String")
    private String relationship;//亲属关系
    @ApiModelProperty("考号")
    @NotBlank(message = "学生考号不能为空",groups = {GroupSix.class, GroupSeven.class})
    private String examNo;
    @ApiModelProperty(value = "家长app端访问来源类型" ,dataType = "Integer")
    private Integer appPermType;
    @ApiModelProperty(value = "版本号" ,dataType = "Integer")
    private Integer version;
    private List<String> clazzIdList;
    private List<String> gradeIdList;
}
