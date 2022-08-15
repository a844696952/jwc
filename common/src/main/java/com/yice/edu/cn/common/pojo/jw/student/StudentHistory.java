package com.yice.edu.cn.common.pojo.jw.student;


import com.yice.edu.cn.common.pojo.jw.classes.JwClaCadresStu;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@ApiModel("学生当年信息记录(每学期记录一次)")
public class StudentHistory{
    @Indexed
    private String id;
    private String schoolYearId;
    private String fromTo;
    private int term;
    private Student student;
    //职务信息
    private List<JwClaCadresStu> jwClaCadresStu;
    //家庭信息
    private List<StudentFamily> studentFamily;
    private String createTime;
}
