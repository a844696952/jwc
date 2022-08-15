package com.yice.edu.cn.osp.controller.jw.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.node.Node;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/teacherExt")
public class TeacherExtController {
    @Autowired
    private TeacherExtService teacherExtService;
    @PostMapping("/ignore/searchTeachersByName")
    public ResponseJson searchTeachersByName(@RequestBody @Validated Teacher teacher){
        if(teacher.getName()==null){
            return new ResponseJson(false,"教师名称不能为空");
        }
        teacher.setSchoolId(mySchoolId());
        List<Node> teachers=teacherExtService.searchTeachersByName(teacher);
        return new ResponseJson(teachers);
    }
}
