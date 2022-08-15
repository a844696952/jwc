package com.yice.edu.cn.osp.service.jy.homework;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassVo;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkNew;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.osp.feignClient.jy.homework.HomeworkFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return homeworkFeign.findHomeworkListByConditionNew(homework);
    }

    public Homework findOneHomeworkByCondition(Homework homework) {
        return homeworkFeign.findOneHomeworkByCondition(homework);
    }

    public long findHomeworkCountByCondition(Homework homework) {
        return homeworkFeign.findHomeworkCountByCondition(homework);
    }
    public long findHomeworkCountByConditionNew(Homework homework) {
        return homeworkFeign.findHomeworkCountByConditionNew(homework);
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

    public List<TeacherClasses> findGradeByTeacher(String teacherId) {
        return teacherClassesFeign.findGradeByTeacher(teacherId);
    }

    public List<TeacherClassesCourse> findCourseByTeacherGrade(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findCourseByTeacherGrade(teacherClasses);
}
    public List<TeacherClassesCourse> findCourseByTeacherGrade2(TeacherClasses teacherClasses) {
        return teacherClassesFeign.findCourseByTeacherGrade2(teacherClasses);
    }

    public List<TeacherClasses> findClassesByTeacherCourse(TeacherClassVo teacherClassVo) {
        return teacherClassesFeign.findClassesByTeacherCourse(teacherClassVo);
    }
    public Homework publishHomework(Homework homework){
        return homeworkFeign.publishHomework(homework);
    }

    public List<Map<String, Object>> findHomeworkListByConditionXq(Homework homework) {
        //大于30%的概率
        List<Map<String,Object>>  list = null;
        list = homeworkFeign.findHomeworkListByConditionXq(homework);
        if(list==null ||list.size()==0){
            return list;
        }
        list = list.stream().filter(s->Double.valueOf(s.get("divideCount").toString())>=0.3).
                limit(6).collect(Collectors.toList());
        return list;
    }

    public  Map<String, Object> findHomeworksByConditionDetail(Homework homework) {
        return homeworkFeign.findHomeworksByConditionDetail(homework);
    }

    public List<HomeworkNew> findKnowlegAllMoreDetailByCondition(Homework homework) {
        return homeworkFeign.findKnowlegAllMoreDetailByCondition(homework);
    }

    public HomeworkNew findCurrentTopicDetail(Homework homework) {
        return homeworkFeign.findCurrentTopicDetail(homework);
    }

    public List<Map<String,Object>> findOneError(Homework homework) {
        return homeworkFeign.findOneError(homework);
    }

    public List<Map<String,Object>> findOneStudentDetail(Homework homework) {
        //大于30%的概率
        List<Map<String,Object>>  list = null;
        list = homeworkFeign.findOneStudentDetail(homework);
        if(list==null ||list.size()==0){
            return null;
        }
        list = list.stream().filter(s->Double.valueOf(s.get("divideCount").toString())>=0.3)
                .collect(Collectors.toList());
        return list;
    }

    public List<Map<String, Object>> findOneStudentKnoledgeContext(Homework homework) {
        return homeworkFeign.findOneStudentKnoledgeContext(homework);
    }

    public  List<WrongTopics> mistakesCollection(Homework homework) {
        return homeworkFeign.mistakesCollection(homework);
    }

    public HomeworkNew mistakesCollectionDetail(Homework homework) {
        return homeworkFeign.mistakesCollectionDetail(homework);
    }

    public Map<String,Object> findWrongtopicInfo(Homework homework) {
        return homeworkFeign.findWrongtopicInfo(homework);
    }

    public Integer findOneStudentKnoledgeContextCount(Homework homework) {
        return homeworkFeign.findOneStudentKnoledgeContextCount(homework);
    }

    public Integer mistakesCollectionCount(Homework homework) {
        return homeworkFeign.mistakesCollectionCount(homework);
    }

    public Integer findKnowlegAllMoreDetailByConditionCount(Homework homework) {
        return homeworkFeign.findKnowlegAllMoreDetailByConditionCount(homework);
    }
}
