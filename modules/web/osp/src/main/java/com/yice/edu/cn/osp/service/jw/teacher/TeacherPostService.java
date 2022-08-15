package com.yice.edu.cn.osp.service.jw.teacher;

import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherPostFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherPostService {
    @Autowired
    private TeacherPostFeign teacherPostFeign;

    public TeacherPost findTeacherPostById(String id) {
        return teacherPostFeign.findTeacherPostById(id);
    }

    public TeacherPost saveTeacherPost(TeacherPost teacherPost) {
        return teacherPostFeign.saveTeacherPost(teacherPost);
    }

    public List<TeacherPost> findTeacherPostListByCondition(TeacherPost teacherPost) {
        return teacherPostFeign.findTeacherPostListByCondition(teacherPost);
    }

    public long findTeacherPostCountByCondition(TeacherPost teacherPost) {
        return teacherPostFeign.findTeacherPostCountByCondition(teacherPost);
    }

    public void updateTeacherPost(TeacherPost teacherPost) {
        teacherPostFeign.updateTeacherPost(teacherPost);
    }

    public void deleteTeacherPost(String id) {
        teacherPostFeign.deleteTeacherPost(id);
    }

    public int editTeacherPost(TeacherPost teacherPost){
       return teacherPostFeign.editTeacherPost(teacherPost);
    }
    public TeacherPost findOneTeacherPostByCondition(TeacherPost teacherPost) {
        return teacherPostFeign.findOneTeacherPostByCondition(teacherPost);
    }
    public List<TeacherPost> findGradeTeacherBySchool(String schoolId,List<Dd> gradeList){
        return teacherPostFeign.findGradeTeacherBySchool(schoolId,gradeList);
    }
    public List<Teacher> findTeachersByPost(TeacherPost teacherPost){
        return teacherPostFeign.findTeachersByPost(teacherPost);
    }
    public List<TeacherPost> findTeacherListByPost(TeacherPost teacherPost){
        return teacherPostFeign.findTeacherListByPost(teacherPost);
    }
    public List<TeacherPost> findTeachers4Grade(String schoolId){
        return teacherPostFeign.findTeachers4Grade(schoolId);
    }
    public List<TeacherPost> findTeachers4Class(TeacherPost teacherPost){
        return teacherPostFeign.findTeachers4Class(teacherPost);
    }
}
