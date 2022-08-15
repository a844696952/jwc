package com.yice.edu.cn.jw.controller.teacher;

import com.yice.edu.cn.common.pojo.general.node.Node;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.jw.service.teacher.TeacherExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teacherExt")
public class TeacherExtController {
    @Autowired
    private TeacherExtService teacherExtService;
    @PostMapping("/searchTeachersByName")
    public List<Node> searchTeachersByName(@RequestBody Teacher teacher){
        return teacherExtService.searchTeachersByName(teacher);
    }
}
