package com.yice.edu.cn.jw.service.teacher;

import com.yice.edu.cn.common.pojo.general.node.Node;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.jw.dao.teacher.ITeacherExtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherExtService {
    @Autowired
    private ITeacherExtDao teacherExtDao;
    @Transactional(readOnly = true)
    public List<Node> searchTeachersByName(Teacher teacher) {
        return teacherExtDao.searchTeachersByName(teacher);
    }
}
