package com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean;

import com.yice.edu.cn.common.pojo.dm.ycVeriface.blacklist.YcVerifaceBlacklist;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class YcDataToRegisterPage {

    private List<Student> studentList = new ArrayList<>();
    private List<Teacher> teacherList = new ArrayList<>();
    private List<YcVerifaceBlacklist> blacklists = new ArrayList<>();
    private long count;
}
