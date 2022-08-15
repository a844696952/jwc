package com.yice.edu.cn.tap.service.homework;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassVo;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.tap.feignClient.homework.HomeworkFeign;
import com.yice.edu.cn.tap.feignClient.teacher.TeacherClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkFeign homeworkFeign;

    @Autowired
    private TeacherClassesFeign teacherClassesFeign;

    public Homework findHomeworkById(String id) {
        return homeworkFeign.findHomeworkById(id);
    }

    public Homework saveHomework(Homework homework) {
        return homeworkFeign.saveHomework(homework);
    }

    public List<Homework> findHomeworkListByCondition(Homework homework) {
        return homeworkFeign.findHomeworkListByCondition(homework);
    }

    public Homework findOneHomeworkByCondition(Homework homework) {
        return homeworkFeign.findOneHomeworkByCondition(homework);
    }

    public long findHomeworkCountByCondition(Homework homework) {
        return homeworkFeign.findHomeworkCountByCondition(homework);
    }

    public void updateHomework(Homework homework) {
        homeworkFeign.updateHomeworkNew(homework);
    }

    public void deleteHomework(String id) {
        homeworkFeign.deleteHomeworkNew(id);
    }

    public void deleteHomeworkByCondition(Homework homework) {
        homeworkFeign.deleteHomeworkByCondition(homework);
    }

    public Homework publishHomework(Homework homework){
        return homeworkFeign.publishHomework(homework);
    }

    public List<TeacherClasses> findGradeByTeacher(String teacherId) {
        return teacherClassesFeign.findGradeByTeacher(teacherId);
    }

    public List<TeacherClassesCourse> findCourseByTeacherGrade(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findCourseByTeacherGrade(teacherClasses);
    }

    public List<TeacherClassesCourse> findCourseByTeacherGrade2(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findCourseByTeacherGrade2(teacherClasses);
    }

    public List<TeacherClassesCourse> findCourseByTeacherGrade3(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findCourseByTeacherGrade3(teacherClasses);
    }

    public List<TeacherClasses> findClassesByTeacherCourse(TeacherClassVo teacherClassVo) {
        return teacherClassesFeign.findClassesByTeacherCourse(teacherClassVo);
    }

    public List<Homework> findHomeworks4Index(Homework homework) {
        return homeworkFeign.findHomeworks4Index(homework);
    }
}
