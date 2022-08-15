package com.yice.edu.cn.common.pojo.xw.kq;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.pojo.xw.kq
 * @Author: Administrator
 * @CreateTime: 2019-03-04 09:42
 * @Description: 人像录入对象
 */
@Data
public class KqStuAddBean {
    @ApiModelProperty(value = "要批量操作的学生列表", dataType = "List")
    ArrayList<Student> studentList;
}
