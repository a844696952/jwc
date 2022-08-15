package com.yice.edu.cn.common.pojo.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.Pager;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("学生成绩查询条件类")
public class ScoreCond {
    private String clazzId;
    private String examNo;
    private String schoolExamId;
    private String courseId;
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;

    private String studentId;

}
