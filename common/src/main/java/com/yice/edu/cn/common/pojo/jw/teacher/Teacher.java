package com.yice.edu.cn.common.pojo.jw.teacher;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
*
*教师信息

*
*/
@Data
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1045132113520664224L;
    @ApiModelProperty(value = "主键id",dataType = "String")
    private String id;//主键id
    private String createTime;//创建时间
    private String updateTime;//更新时间
    private String del;//删除标识
    @ApiModelProperty(value = "头像",dataType = "String")
    private String imgUrl;//头像
    @ApiModelProperty(value = "工号",dataType = "String")
    @Excel(name = "工号",isImportField = "true_teacher")
    private String workNumber;
    @ApiModelProperty(value = "教师姓名",dataType = "String")
    @Excel(name = "教师姓名",isImportField = "true_teacher")
    private String name;//教师姓名
    @ApiModelProperty(value = "性别",dataType = "String")
    @Excel(name = "性别",replace = {"男_4","女_5","_null"})
    private String sex;//性别-取数据字典
    @ApiModelProperty(value = "联系方式",dataType = "String")
    @Excel(name = "联系方式",isImportField = "true_teacher")
    @NotBlank(groups = GroupTwo.class,message = "电话号码不能你为空")
    @Length(max = 11,groups = GroupTwo.class,message = "电话号码长度应为{max}")
    private String tel;//联系方式
    @ApiModelProperty(value = "国籍（中国_1，中国(港澳台)_2，国外_3）",dataType = "String")
    @Excel(name = "国籍",replace = {"中国_1","中国(港澳台)_2","国外_3"},isImportField = "true_teacher")
    private String nationality;//国籍
    @ApiModelProperty(value = "证件类型（身份证_60，护照_61）",dataType = "String")
    @Excel(name = "证件类型",replace = {"身份证_60","护照_61"})
    private String documentType;//证件类型
    @ApiModelProperty(value = "证件号码",dataType = "String")
    @Excel(name = "证件号码")
    private String documentNum;//证件号码
    @ApiModelProperty(value = "姓名拼音",dataType = "String")
    @Excel(name = "姓名拼音")
    private String pinyin;
    @ApiModelProperty(value = "姓名首字母",dataType = "String")
    @Excel(name = "姓名首字母")
    private String initials;
    @ApiModelProperty(value = "英文名",dataType = "String")
    @Excel(name = "英文名")
    private String englishName;
    @ApiModelProperty(value = "QQ",dataType = "String")
    @Excel(name = "QQ")
    private String qq;//QQ
    @ApiModelProperty(value = "微信",dataType = "String")
    @Excel(name = "微信")
    private String weixin;//微信
    @Excel(name = "邮箱")
    @ApiModelProperty(value = "邮箱",dataType = "String")
    private String email;
    @ApiModelProperty(value = "民族id",dataType = "String")
    private String nation;//民族
    @Excel(name = "民族")
    @ApiModelProperty(value = "民族名称",dataType = "String")
    private String nationName;//民族名称
    @Excel(name = "籍贯")
    @ApiModelProperty(value = "籍贯",dataType = "String")
    private String nativePlace;//籍贯
    @Excel(name = "教师资格证编号")
    @ApiModelProperty(value = "教师资格证编号",dataType = "String")
    private String teacherNum;//教师资格证编号
    @ApiModelProperty(value = "省份id",dataType = "String")
    private String provinceId;//省份id
    @ApiModelProperty(value = "(家庭住址所在)省份",dataType = "String")
    @Excel(name = "(家庭住址所在)省份")
    private String provinceName;//省份名称
    @ApiModelProperty(value = "城市id",dataType = "String")
    private String cityId;//城市id
    @ApiModelProperty(value = "(家庭住址所在)城市",dataType = "String")
    @Excel(name = "(家庭住址所在)城市")
    private String cityName;//城市名称
    @ApiModelProperty(value = "区域id",dataType = "String")
    private String countyId;//区域id
    @Excel(name = "(家庭住址所在)区域")
    private String countyName;//区域名称
    @Excel(name = "(家庭住址所在)详细地址")
    @ApiModelProperty(value = "(家庭住址所在)详细地址",dataType = "String")
    private String address;//详细地址
    @Excel(name = "政治面貌",replace = {"群众_6","团员_7","党员_8","_null"})
    @ApiModelProperty(value = "政治面貌（群众_6，团员_7，党员_8）",dataType = "String")
    private String politicalFace;//政治面貌
    @Excel(name = "出生日期")
    @ApiModelProperty(value = "出生日期",dataType = "String")
    private String birthDate;//出生日期
    @Excel(name = "教龄(年)")
    @ApiModelProperty(value = "教龄",dataType = "String")
    private String teacherAge;//教龄
    @ApiModelProperty(value = "最高学历(专科_30，本科_31，硕士_32，博士_33，MBA_34，EMBA_35)",dataType = "String")
    @Excel(name = "最高学历",replace = {"专科_30","本科_31","硕士_32","博士_33","MBA_34","EMBA_35","_null"})
    private String topEdu;//最高学历
    @Excel(name = "毕业学校")
    @ApiModelProperty(value = "毕业学校",dataType = "String")
    private String graduate;//毕业学校
    @Excel(name = "毕业专业")
    @ApiModelProperty(value = "毕业专业",dataType = "String")
    private String major;//毕业专业
    @Excel(name = "来校工作时间")
    @ApiModelProperty(value = "来校工作时间",dataType = "String")
    private String beginTime;//来校工作时间
    @Excel(name = "工作经历")
    @ApiModelProperty(value = "工作经历",dataType = "String")
    private String works;//工作经历
    @ApiModelProperty(value = "状态（在职/离职/管理员)",dataType = "String")
    private String status;//状态（在职/离职/管理员）
    @ApiModelProperty(value = "学校id",dataType = "String")
    private String schoolId;//学校id
    @ApiModelProperty(value = "学校名称",dataType = "String")
    private String schoolName;//学校名称,冗余字段
    @NotBlank(groups={GroupOne.class,GroupTwo.class},message = "旧密码不能为空")
    @Length(min=8,max=32,groups = {GroupOne.class,GroupTwo.class},message = "密码长度必须在{min}--{max}之间")
    private String password;//密码
    private String account;//管理员登录账号(新增自动生成的学校管理员账号yc******)
    @ApiModelProperty(value = "婚姻状况",dataType = "String")
    @Excel(name = "婚姻状况",replace = {"未婚_241","已婚_242","丧偶_243","离婚_244","其他_245","_null"})
    private String maritalStatus;
    @ApiModelProperty(value = "注册状态（0：未注册，1：已经注册）",dataType = "String")
    private String registerStatus;
    @ApiModelProperty(value = "微信小程序openId")
    private String openId;
    @ApiModelProperty(value = "设备id")
    private String deviceId;
    @ApiModelProperty("人员类型（1:教师、2:职工）")
    private Integer personType;

    @ApiModelProperty("app登录为0，小程序登录为1")
    private Integer appPermType;
    @ApiModelProperty("app版本号")
    private Integer appVersion;
    @ApiModelProperty("老师对应的科目信息")
    private  List<Map<String, Object>> maps;


    //分页排序等
    @Transient
    @NotNull
    private Pager pager;
    //额外字段
    @Transient
    private School school;//教师所属学校
    @Transient
    @NotBlank(groups=GroupOne.class,message = "新密码不能为空")
    @Length(max=32,groups = GroupOne.class,message = "密码长度不能超过{max}字符")
    private String newPassword;
//    @NotBlank(groups={GroupTwo.class},message = "token不能为空") 暂时注释这个，不限制前段登录一定要传token
    @Length(max=255,groups = {GroupTwo.class},message = "token长度不能超过{max}个字符")
    private String token;
    private String roleNames;
    //返回提示
    @Transient
    private String code;
    @Transient
    private String msg;
    @Transient
    private List<TeacherClassesCourse> courseNameList;//教师所教的课程
    @Transient
    private String teacherjob;//教师职务
    @Transient
    private String teacherClassName;//教师所教的课程
    @Transient
    private String classId;//班级id
    @Transient
    private String classesName;//班级名称（年级名称+班号）
    @Transient
    private List<Teacher> teacherChildren;//教师所有人
    @Transient
    private String teacherId;//教师id别名
    @Transient
    private String teacherName;//教师别名
    @Transient
    private String collectivePlanId;//讨论组id
    @Transient
    private String type;//为0
    @Transient
    private List<Perm> permList;//教师app权限
    @Transient
    private List<TeacherClasses> teacherClasses;//所教科目 班级 年级
    @Transient
    private List<TeacherPost> postList;//教师所任职务

    private List<AppPerm> appPermList;//权限树（新）

    @ApiModelProperty("职工所属部门id")
    private String departmentId;
    @ApiModelProperty("职工所属部门")
    private String departmentName;
    @Transient
    @ApiModelProperty("头像识别头像")
    private String img;
    @Transient
    @ApiModelProperty("头像校验状态")
    private String checkStatus;
    @ApiModelProperty("头像校验查询 类型 1：全部，2：通过，3：未校验或者校验不通过的")
    private String searchCheckType;
    @ApiModelProperty("科目名称")
    private String courseName;
    @ApiModelProperty("科目名称对应教学人数")
    private Integer number;
    @ApiModelProperty("已分配的班牌权限")
    private String assignedClass;
}
