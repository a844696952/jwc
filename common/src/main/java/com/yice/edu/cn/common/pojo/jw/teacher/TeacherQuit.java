package com.yice.edu.cn.common.pojo.jw.teacher;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;


@Data
@ApiModel("离职教师信息")
public class TeacherQuit{

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
    @ApiModelProperty("删除标识")
    private Integer del;
    @ApiModelProperty("头像")
    private String imgUrl;
    @ApiModelProperty("工号")
    private String workNumber;
    @Excel(name = "教师姓名",isImportField = "true_teacher")
    @ApiModelProperty("教师姓名")
    private String name;
    @ApiModelProperty("拼音")
    private String pinyin;
    @ApiModelProperty("首字母")
    private String initials;
    @ApiModelProperty("英文名")
    private String englishName;
    @Excel(name = "性别",replace = {"男_4","女_5","_null"})
    @ApiModelProperty("性别")
    private String sex;
    @Excel(name = "联系方式",isImportField = "true_teacher")
    @ApiModelProperty("联系方式")
    private String tel;
    @Excel(name = "QQ")
    @ApiModelProperty("QQ")
    private String qq;
    @Excel(name = "微信")
    @ApiModelProperty("微信")
    private String weixin;
    @Excel(name = "邮箱")
    @ApiModelProperty("email")
    private String email;
    @Excel(name = "国籍",replace = {"中国_1","中国(港澳台)_2","国外_3"},isImportField = "true_teacher")
    @ApiModelProperty("国籍")
    private String nationality;
    @ApiModelProperty("民族")
    private String nation;
    @Excel(name = "民族")
    @ApiModelProperty("民族名称")
    private String nationName;
    @Excel(name = "籍贯")
    @ApiModelProperty("籍贯")
    private String nativePlace;
    @ApiModelProperty("证件类型")
    private String documentType;
    @ApiModelProperty("证件号码")
    private String documentNum;
    @Excel(name = "教师资格证编号")
    @ApiModelProperty("教师资格证编号")
    private String teacherNum;
    @ApiModelProperty("省份id")
    private Integer provinceId;
    @Excel(name = "(家庭住址所在)省份")
    @ApiModelProperty("省份名称")
    private String provinceName;
    @ApiModelProperty("城市id")
    private Integer cityId;
    @Excel(name = "城市")
    @ApiModelProperty("城市名称")
    private String cityName;
    @ApiModelProperty("区域id")
    private Integer countyId;
    @Excel(name = "区域")
    @ApiModelProperty("区域名称")
    private String countyName;
    @Excel(name = "详细地址")
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("政治面貌")
    private String politicalFace;
    @ApiModelProperty("出生日期")
    private String birthDate;
    @Excel(name = "教龄(年)")
    @ApiModelProperty("教龄")
    private String teacherAge;
    @ApiModelProperty("最高学历")
    private String topEdu;
    @ApiModelProperty("毕业学校")
    private String graduate;
    @ApiModelProperty("毕业专业")
    private String major;
    @Excel(name = "来校工作时间")
    @ApiModelProperty("来校工作时间")
    private String beginTime;
    @ApiModelProperty("工作经历")
    private String works;
    @ApiModelProperty("状态（在职/离职）")
    private String status;
    @ApiModelProperty("学校id")
    private String schoolId;
    @ApiModelProperty("学校名称")
    private String schoolName;
    @ApiModelProperty("婚姻状况")
    private String maritalStatus;
    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
