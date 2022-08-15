package com.yice.edu.cn.tap.service.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesForQusSurvey;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@Service
public class TeacherClassesService {
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;

    public TeacherClasses findTeacherClassesById(String id) {
        return teacherClassesFeign.findTeacherClassesById(id);
    }

    public TeacherClasses saveTeacherClasses(TeacherClasses teacherClasses) {
        return teacherClassesFeign.saveTeacherClasses(teacherClasses);
    }

    public List<TeacherClasses> findTeacherClassesListByCondition(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findTeacherClassesListByCondition(teacherClasses);
    }

    public long findTeacherClassesCountByCondition(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findTeacherClassesCountByCondition(teacherClasses);
    }

    public void updateTeacherClasses(TeacherClasses teacherClasses) {
        teacherClassesFeign.updateTeacherClasses(teacherClasses);
    }

    public void deleteTeacherClasses(String id) {
        teacherClassesFeign.deleteTeacherClasses(id);
    }


    public ResponseJson editTeacherPostCourse(TeacherClasses teacherClasses){
        return teacherClassesFeign.editTeacherPostCourse(teacherClasses);
    }

    public List<Map<String, Object>> findTeacherClassPostCourseList(
            @RequestBody TeacherClasses teacherClasses){
        return teacherClassesFeign.findTeacherClassPostCourseList(teacherClasses);
    }

    public List<String> findCourseNameList4Teacher(String teacherId){
        return teacherClassesFeign.findCourseNameList4Teacher(teacherId);
    }


    public List<TeacherClassesForQusSurvey> findClassesByTeacherInfo(TeacherClassesForQusSurvey teacher5Classes){
        teacher5Classes.setSchoolId(mySchoolId());
        return teacherClassesFeign.findClassesByTeacherInfo(teacher5Classes);
    }

    public List<Map<String,Object>> findTeacherClassPostCourseListXq(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findTeacherClassPostCourseListXq(teacherClasses);
    }
    public List<JwClasses> findTeacherClassByTeacherId(String teacherId){
        return teacherClassesFeign.findTeacherClassByTeacherId(teacherId);
    }
    public TeacherClasses findTeacherClassByTeacherIdAndPost(String teacherId,String postName){
       return teacherClassesFeign.findTeacherClassByTeacherIdAndPost(teacherId,postName);
    }
}
