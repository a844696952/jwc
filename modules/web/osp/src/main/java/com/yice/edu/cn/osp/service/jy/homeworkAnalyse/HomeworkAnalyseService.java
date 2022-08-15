package com.yice.edu.cn.osp.service.jy.homeworkAnalyse;

import cn.hutool.core.util.NumberUtil;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesCourse;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherHomeworkAnalyseClasses;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.topics.Topics;
import com.yice.edu.cn.common.pojo.jy.topics.TopicsRecord;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.feignClient.jy.homeworkAnalyse.HomeworkAnalyseFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HomeworkAnalyseService {
    @Autowired
    private HomeworkAnalyseFeign homeworkAnalyseFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;


    public Homework findHomeworkAnalyseById(String id) {
        Homework homework = homeworkAnalyseFeign.findHomeworkAnalyseById(id);

        return homework;
    }

    public List<Homework> findHomeworkAnalyseListByConditionNew (Homework homework){
        return homeworkAnalyseFeign.findHomeworkAnalyseListByConditionNew(homework);
    }

    public long findHomeworkAnalyseCountByConditionNew (Homework homework){
        return homeworkAnalyseFeign.findHomeworkAnalyseCountByConditionNew(homework);
    }

    public List<Map<String,String>> findTeacherClassPostCourseListHomeworkAnalyse(String teacherId){

        return teacherClassesFeign.findTeacherClassPostCourseListHomeworkAnalyse(teacherId);
    }

    public List<TeacherClassesCourse> findTeacherClassCourseListHomeworkAnalyse(String teacherClassesId){
        return teacherClassesFeign.findTeacherClassCourseListHomeworkAnalyse(teacherClassesId);
    }

    public List<TeacherHomeworkAnalyseClasses> findClassTeacherListHomeworkAnalyseByClasses(String classesId){
        return teacherClassesFeign.findClassTeacherListHomeworkAnalyseByClasses(classesId);
    }

    public List<Map<String,Object>> findClassesCourseListHomeworkAnalyseByTeacherClassesIds(Map map){
        return teacherClassesFeign.findClassesCourseListHomeworkAnalyseByTeacherClassesIds(map);
    }
    public List<Map<String,String>> findTeacherClassesHomeworkAnalyseByTeacherId(String teacherId){
        return teacherClassesFeign.findTeacherClassesHomeworkAnalyseByTeacherId(teacherId);
    }
    public List<HomeworkStudent> findHomeworkStudentListAnalyseByCondition(HomeworkStudent homeworkStudent){
        return  homeworkAnalyseFeign.findHomeworkStudentListAnalyseByCondition(homeworkStudent);
    }

    public List<TopicsRecord> findTopicsRecordListAnalyseByCondition(TopicsRecord topicsRecord){
        return homeworkAnalyseFeign.findTopicsRecordListAnalyseByCondition(topicsRecord);
    }



}
