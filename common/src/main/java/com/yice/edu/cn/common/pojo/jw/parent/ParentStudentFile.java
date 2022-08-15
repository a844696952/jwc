package com.yice.edu.cn.common.pojo.jw.parent;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yice.edu.cn.common.pojo.validateClass.GroupFive;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.pojo.jw.parent
 * @Author: Administrator
 * @CreateTime: 2018-09-25 19:38
 * @Description: ${Description}
 */
@Data
public class ParentStudentFile implements Serializable {

    private static final long serialVersionUID = -485016485452450298L;



    @ApiModelProperty(value = "id",dataType = "String")
    private String id;
    @ApiModelProperty(value = "姓名",dataType = "String")
    private String name;
    @ApiModelProperty(value = "性别 4男  5女",dataType = "String")
    private String sex;
    @ApiModelProperty(value = "监护人联系方式",dataType = "String")
    private String guardianContact;
    @ApiModelProperty(value = "头像",dataType = "String")
    private String imgUrl;
    @ApiModelProperty(value = "邮箱",dataType = "String")
    private String email;
    @ApiModelProperty(value = "国籍",dataType = "String")
    private String nationality;
    @ApiModelProperty(value = "民族",dataType = "String")
    private String nation;
    @ApiModelProperty(value = "民族名称",dataType = "String")
    private String nationName;
    @ApiModelProperty(value = "籍贯",dataType = "String")
    private String nativePlace;
    @ApiModelProperty(value = "省id",dataType = "String")
    private String provinceId;
    @ApiModelProperty(value = "省份名称",dataType = "String")
    private String provinceName;
    @ApiModelProperty(value = "市id",dataType = "String")
    private String cityId;
    @ApiModelProperty(value = "城市名称",dataType = "String")
    private String cityName;
    @ApiModelProperty(value = "区/县id",dataType = "String")
    private String countyId;
    @ApiModelProperty(value = "区/县名称",dataType = "String")
    private String countyName;
    @ApiModelProperty(value = "详细地址",dataType = "String")
    private String address;
    @ApiModelProperty(value = "政治面貌",dataType = "String")
    private String politicsFace;
    @ApiModelProperty(value = "出生日期",dataType = "String")
    private String birthday;
    @ApiModelProperty(value = "学号",dataType = "String")
    private String studentNo;
    @ApiModelProperty(value = "入学时间",dataType = "String")
    private String admissionDate;
    @ApiModelProperty(value = "学籍号",dataType = "String")
    private String studentCode;
    @ApiModelProperty(value = "年级id",dataType = "String")
    private String gradeId;
    @ApiModelProperty(value = "年级名称",dataType = "String")
    private String gradeName;
    @ApiModelProperty(value = "班级di",dataType = "String")
    private String classesId;
    @ApiModelProperty(value = "ic卡号",dataType = "String")
    private String cardNumber;
    @ApiModelProperty(value = "学生状态",dataType = "String")
    private String status;
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;
    @ApiModelProperty(value = "班级应届年份",dataType = "String")
    private String enrollYear;
    @ApiModelProperty(value = "删除标志",dataType = "Integer")
    private Integer del;
    @ApiModelProperty(value = "学校",dataType = "String")
    private String schoolId;
    @ApiModelProperty(value = "注册状态（0：未注册，1：已经注册）",dataType = "String")
    private String registerStatus;
   /*------------------*/
    //家长姓名
    private String parentName;//家长姓名
    //关系
    private String relationship;//亲属关系
    private String schoolName;//学校名字
    //班级
    private int classesNumber;
    private String parentId;//家长姓名

}
