package com.yice.edu.cn.tap.service.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherPostFeign;
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
    public List<TeacherPost> findTeacherPostListByCondition(TeacherPost teacherPost) {
        return teacherPostFeign.findTeacherPostListByCondition(teacherPost);
    }


}
