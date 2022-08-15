package com.yice.edu.cn.common.pojo.jw.exam.examManage;

import com.yice.edu.cn.common.pojo.Pager;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@ApiModel("考生")
@Document
public class ExamStudent{

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("考试id")
    @Indexed(unique = true)
    private String schoolExamId;
    @ApiModelProperty("考试信息列表")
    private List<ExamStudentInfo> examStudentInfos;

    //分页排序等
    @Transient
    @NotNull(message = "pager不能为空")
    @Valid
    private Pager pager;
}
