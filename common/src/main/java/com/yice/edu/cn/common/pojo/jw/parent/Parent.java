package com.yice.edu.cn.common.pojo.jw.parent;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupFive;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
*
*
*
*/
@Data
public class Parent implements Serializable{
    private static final long serialVersionUID = 863017690189234264L;
    @ApiModelProperty(value = "家长id",dataType = "String")
    @NotBlank(groups = GroupOne.class,message = "id不能为空")
    private String id;//主键id
    @NotBlank(groups = {GroupTwo.class,GroupThree.class},message = "电话号码不能为空")
    @Length(max = 11,groups = {GroupTwo.class,GroupThree.class},message = "电话号码长度应为{max}")
    @ApiModelProperty(value = "家长电话",dataType = "String")
    @Pattern(regexp = "^1\\d{10}$",message = "电话号码必须为11位手机号码",groups = {GroupTwo.class,GroupThree.class})
    private String tel;//家长联系电话
    @ApiModelProperty(value = "创建时间",dataType = "String")
    private String createTime;//创建时间
    @ApiModelProperty(value = "修改时间",dataType = "String")
    private String updateTime;//修改时间
    @NotBlank(groups = {GroupTwo.class,GroupThree.class},message = "密码不能为空")
    @ApiModelProperty(value = "密码",dataType = "String")
    private String password;//密码
    @ApiModelProperty(value = "二次输入密码",dataType = "String")
    @NotBlank(groups= GroupTwo.class,message = "验证密码不能为空")
    private String passwordagain;//确认密码
    @ApiModelProperty(value = "家长姓名",dataType = "String")
    private String name;//家长姓名
    @ApiModelProperty(value = "性别",dataType = "String")
    private String sex;//性别
    @ApiModelProperty(value = "学生id",dataType = "String")
    private String studentId;//孩子id
    @ApiModelProperty(value = "验证码",dataType = "String")
    @NotBlank(groups = GroupThree.class ,message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$",message = "验证码必须为6位",groups = GroupThree.class)
    private String code;//随机验证码
    @ApiModelProperty(value = "旧密码",dataType = "String")
    private  String oldPassword;
    //分页排序等
    @Transient
    @NotNull
    @Valid
    private Pager pager;
    //额外字段
    @ApiModelProperty(value = "学校id",dataType = "String")
    private  String schoolId;
    @ApiModelProperty(value = "激活状态",dataType = "String")
    private  String registerStatus;
    @ApiModelProperty(value = "openId",dataType = "String")
    private  String openId;
    @ApiModelProperty(value = "家长app端访问来源类型" ,dataType = "Integer")
    private Integer appPermType;
    @ApiModelProperty(value = "版本号" ,dataType = "Integer")
    private Integer version;

}
