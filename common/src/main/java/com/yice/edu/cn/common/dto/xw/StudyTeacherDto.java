package com.yice.edu.cn.common.dto.xw;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author: keyusong
 * @Date: 2019/5/31 18:24
 * <p>
 * 我的学习和学习资源的dto
 */
@Data
public class StudyTeacherDto {

    //学习资源id
    private String resourceId;
    //我的学习中教师id
    private String teacherId;
    //学校id
    private String schoolId;
    //标题
    private String title;
    //学习资源主题id
    private String type;
    //学习资源主题名称
    private String typeName;
    //学习资源类型
    private String activityType;
    //我的学习的完成时间
    private String endTime;
    //学习资源的发布时间
    private String publishTime;
    //学习资源发起人名称
    private String name;

    //做时间段查询，开始时间
    private String startTime;


    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

}
