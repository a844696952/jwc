package com.yice.edu.cn.api.service.teacher;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.api.feign.teacher.TeacherFeign;
import com.yice.edu.cn.api.feign.teacher.TeacherPostFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherFeign teacherFeign;

    @Autowired
    private TeacherPostFeign teacherPostFeign;


    public List<Teacher> findTeacherListByCondition4Like(Teacher teacher) {
        return teacherFeign.findTeacherListByCondition4Like(teacher);
    }

    public List<TeacherPost> findTeacherPost(String teacherId){
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setTeacherId(teacherId);
        teacherPost.setPager(new Pager().setPaging(false).setIncludes("name","ddId","gradeId","gradeName","classId","className","sort").setSortField("sort"));
        return teacherPostFeign.findTeacherPostListByCondition(teacherPost);
    }

    public List<Perm> findTeacherFuncPermsByTeacherId(String id) {
        return teacherFeign.findTeacherFuncPermsByTeacherId(id);
    }

    public Teacher findTeacherById(String id) {
        return teacherFeign.findTeacherById(id);
    }


    public Teacher login(Teacher teacher){
        return teacherFeign.login(teacher);
    }
}
