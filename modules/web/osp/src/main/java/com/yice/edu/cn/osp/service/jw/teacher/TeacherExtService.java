package com.yice.edu.cn.osp.service.jw.teacher;

import com.yice.edu.cn.common.pojo.general.node.Node;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherExtFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherExtService {
    @Autowired
    private TeacherExtFeign teacherExtFeign;

    public List<Node> searchTeachersByName(Teacher teacher) {
        return teacherExtFeign.searchTeachersByName(teacher);
    }
}
