package com.yice.edu.cn.common.pojo.jw.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TeacherVo extends Teacher {
    @ApiModelProperty(value = "职务id")
    private String postId;
    @ApiModelProperty(value = "年级id")
    private String gradeId;
}
