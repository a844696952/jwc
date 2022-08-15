package com.yice.edu.cn.bmp.service.teacher;

import com.yice.edu.cn.bmp.feignClient.teacher.TeacherPostFeign;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Teacher> findTeachersByPost(TeacherPost teacherPost) {
        return teacherPostFeign.findTeachersByPost(teacherPost);
    }
}
