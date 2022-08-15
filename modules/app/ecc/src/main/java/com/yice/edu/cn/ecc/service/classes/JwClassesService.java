package com.yice.edu.cn.ecc.service.classes;

import com.yice.edu.cn.common.pojo.jw.classes.ClassesInfoViewVo;
import com.yice.edu.cn.common.pojo.jw.classes.CreateClassesVo;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher4Classes;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.ecc.feignClient.classes.JwClassesFeign;
import com.yice.edu.cn.ecc.feignClient.teacher.TeacherClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JwClassesService {
    @Autowired
    private JwClassesFeign jwClassesFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;

    public ClassesInfoViewVo getClassesTeacherAndMaster(JwClasses jwClasses){
        return jwClassesFeign.getClassesTeacherAndMaster(jwClasses);
    }
    public List<Teacher4Classes> findClassTeacherListByClasses(TeacherClasses teacherClasses){
        return teacherClassesFeign.findClassTeacherListByClasses(teacherClasses);
    }
    public JwClasses findJwClassesById(String id){
        return jwClassesFeign.findJwClassesById(id);
    }
}
