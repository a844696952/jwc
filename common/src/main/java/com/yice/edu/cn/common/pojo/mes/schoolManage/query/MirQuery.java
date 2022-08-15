package com.yice.edu.cn.common.pojo.mes.schoolManage.query;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("操作记录表query对象")
public class MirQuery {


    private List<TeacherPost> teacherPosts;


    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;


    @ApiModelProperty("审核状态等  0--申述中 1--申述通过   2--申述拒绝")
    private Integer miaStatus;


    private String schoolId;


    @ApiModelProperty("对象类型 0--学生 1--班级")
    private Integer objectType;


    public MirQuery(List<TeacherPost> teacherPosts, @NotNull(message = "pager不能为空") @Valid Pager pager,String schoolId) {
        this.teacherPosts = teacherPosts;
        this.pager = pager;
        this.schoolId = schoolId;
    }
}
