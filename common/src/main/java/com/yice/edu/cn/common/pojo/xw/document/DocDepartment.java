package com.yice.edu.cn.common.pojo.xw.document;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import lombok.Data;

import java.util.List;

@Data
public class DocDepartment {
    private List<Department> departments;
    private List<SendObject> sendObjects;
    private List<WritingManagement> writingManagementList;
    private Writing writing;
    private String id;

}
