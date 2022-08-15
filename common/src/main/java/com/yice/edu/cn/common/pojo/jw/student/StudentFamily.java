package com.yice.edu.cn.common.pojo.jw.student;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.validateClass.*;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
*
*学生家庭信息
*
*/
@Data
public class StudentFamily implements Serializable {

    private static final long serialVersionUID = 5740072280971782817L;
    //@NotBlank(message = "id不能为空")
    private String id;//id
    //@NotBlank(message = "学生id不能为空",groups = {GroupFive.class})
    private String studentId;//学生id
    //@NotBlank(message = "姓名不能为空",groups = {GroupFive.class})
    private String name;//姓名
    //@Max(value = 999,groups = {GroupFive.class})
    private String age;//年龄
    private String relation;//与本人关系
    private String workingUnit;//在职单位
    //@Pattern(regexp = "^1\\d{10}$",message = "手机号码格式错误",groups = {GroupFive.class})
    //@NotBlank(message = "手机号不能为空",groups = {GroupFive.class})
    private String contactWay;//联系方式
    private String job;//职务
    private String createTime;//创建时间
    private String updateTime;//修改时间
    private String del;//删除标志
    private String schoolId;//学校
    //分页排序等
    @Transient
    @NotNull(groups = GroupFour.class)
    private Pager pager;

    //返回提示
    @Transient
    private String code;
    @Transient
    private String msg;
}
