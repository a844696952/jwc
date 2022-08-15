package com.yice.edu.cn.common.pojo.yedAdmin;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    private static final long serialVersionUID = 7762799280945548221L;
    private String id;
    @NotBlank(groups = GroupOne.class,message = "用户名不能为空")
    @Size(groups = GroupOne.class,max = 64,message = "最长{max}字符")
    private String username;//用户名
    @NotBlank(groups = {GroupOne.class, GroupTwo.class},message = "密码不能为空")
    @Size(groups = GroupOne.class,max = 64,message = "最长{max}字符")
    private String password;//密码
    private String realName;//真实姓名
    private String phone;//手机号码
    private String email;//电子邮件
    private String portrait;//头像地址
    private String createTime;
    @ApiModelProperty(value = "教育局id",dataType = "String")
    private String educationBureauId;
    //额外
    @NotNull
    private Pager pager;
    private String verifyCode;
    private String roleNames;
    private String newPassword;
    private String challenge;
//    @NotBlank(groups = GroupOne.class,message = "token不能为空")
    @Length(groups = GroupOne.class,max = 255,message = "最长{max}字符")
    private String token;
}
