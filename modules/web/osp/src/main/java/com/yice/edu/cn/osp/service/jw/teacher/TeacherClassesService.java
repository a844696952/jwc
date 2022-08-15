package com.yice.edu.cn.osp.service.jw.teacher;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassVo;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesForQusSurvey;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class TeacherClassesService {
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public TeacherClasses findTeacherClassesById(String id) {
        return teacherClassesFeign.findTeacherClassesById(id);
    }

    public TeacherClasses saveTeacherClasses(TeacherClasses teacherClasses) {
        return teacherClassesFeign.saveTeacherClasses(teacherClasses);
    }

    public void batchSaveTeacherClasses(List<TeacherClasses> teacherClassess){
        teacherClassesFeign.batchSaveTeacherClasses(teacherClassess);
    }

    public List<TeacherClasses> findTeacherClassesListByCondition(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findTeacherClassesListByCondition(teacherClasses);
    }

    public TeacherClasses findOneTeacherClassesByCondition(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findOneTeacherClassesByCondition(teacherClasses);
    }

    public long findTeacherClassesCountByCondition(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findTeacherClassesCountByCondition(teacherClasses);
    }

    public void updateTeacherClasses(TeacherClasses teacherClasses) {
        teacherClassesFeign.updateTeacherClasses(teacherClasses);
    }

    public void updateTeacherClassesForAll(TeacherClasses teacherClasses) {
        teacherClassesFeign.updateTeacherClassesForAll(teacherClasses);
    }

    public void deleteTeacherClasses(String id) {
        teacherClassesFeign.deleteTeacherClasses(id);
    }

    public void deleteTeacherClassesByCondition(TeacherClasses teacherClasses) {
        teacherClassesFeign.deleteTeacherClassesByCondition(teacherClasses);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/


    public ResponseJson editTeacherPostCourse(TeacherClasses teacherClasses){
        return teacherClassesFeign.editTeacherPostCourse(teacherClasses);
    }

    public List<Map<String, Object>> findTeacherClassPostCourseList(
            @RequestBody TeacherClasses teacherClasses){
        return teacherClassesFeign.findTeacherClassPostCourseList(teacherClasses);
    }

    public List<Map<String, Object>> findTeacherClassPostCourseListBySchoolId(
            @RequestBody TeacherClasses teacherClasses){
        return teacherClassesFeign.findTeacherClassPostCourseListBySchoolId(teacherClasses);
    }

    public List<String> findCourseNameList4Teacher(String teacherId){
        return teacherClassesFeign.findCourseNameList4Teacher(teacherId);
    }


    public List<TeacherClassesForQusSurvey> findClassesByTeacherInfo(TeacherClassesForQusSurvey teacher5Classes){
        teacher5Classes.setSchoolId(mySchoolId());
        return teacherClassesFeign.findClassesByTeacherInfo(teacher5Classes);
    }

    public List<Map<String,String>> findTeacherClassPostCourseListXq(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findTeacherClassPostCourseListXq(teacherClasses);
    }
    public List<JwClasses> findTeacherClassByTeacherId(String teacherId){
        return teacherClassesFeign.findTeacherClassByTeacherId(teacherId);
    }
    public TeacherClasses findTeacherClassByTeacherIdAndPost(String teacherId,String postName){
       return teacherClassesFeign.findTeacherClassByTeacherIdAndPost(teacherId,postName);
    }
    public List<TeacherClassesCourse> findCourse4TeacherClasses(TeacherClasses teacherClasses){
       return teacherClassesFeign.findCourse4TeacherClasses(teacherClasses);
    }
    public List<TeacherClasses> findClassesByTeacherCourse(TeacherClassVo teacherClassVo){
    	return teacherClassesFeign.findClassesByTeacherCourse(teacherClassVo);
    }

    public TeacherClasses findTeacherClasses4EditShow(TeacherClasses teacherClasses) {
        List<TeacherClasses> teacherClassesList = teacherClassesFeign.findTeacherClassesListByCondition(teacherClasses);
        if(teacherClassesList==null||teacherClassesList.size()<=0)
            return null;
        TeacherClasses turn = teacherClassesList.get(0);
        turn.setTeacherClassesCourseList(teacherClassesList.stream().map(tc->{
            TeacherClassesCourse teacherClassesCourse = new TeacherClassesCourse();
            teacherClassesCourse.setCourseId(tc.getCourseId());
            teacherClassesCourse.setCourseName(tc.getCourseName());
            teacherClassesCourse.setSubjectName(tc.getSubjectName());
            return teacherClassesCourse;
        }).collect(Collectors.toList()));
        return turn;
    }
}
