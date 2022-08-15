package com.yice.edu.cn.common.pojo.jy.journal;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jy.album.Album;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Document
public class Journal {
    private String id;
    private String name;
    @Indexed
    @ApiModelProperty(value = "教师id或者是学生id",dataType = "String")
    @NotNull(groups = {GroupTwo.class, GroupThree.class},message = "userId不能为空")
    private String userId;
    @Indexed
    @ApiModelProperty(value = "日志是否是教师所发",dataType = "Boolean")
    @NotNull(groups = GroupTwo.class,message = "fromTeacher不能为空")
    private Boolean fromTeacher;
    @Indexed
    private String sqId;
    @Indexed
    private String createTime;
    @Indexed
    private String classIdEnrollYear;//班级id和当届年份的合并字段,使用逗号分隔
    private String portrait;
    @Length(max=1500,message = "最长限定{max}字",groups = GroupOne.class)
    private String text;
    private List<Album> images;
    @Indexed
    private String schoolId;
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
    //额外字段
    @Transient
    @ApiModelProperty(hidden=true)
    private List<TeacherClasses> tcs;
    private Long thumbCount;
    private Boolean thumbed;
    @Transient
    @ApiModelProperty(value = "是否只查询自己的,如果是老师，不只看自己则能看见所交班级学生日志")
    @NotNull(groups = GroupTwo.class,message = "myself不能为空")
    private Boolean mySelf;
    @Transient
    @ApiModelProperty(value = "当前登录用户id")
    private String loginId;


}
