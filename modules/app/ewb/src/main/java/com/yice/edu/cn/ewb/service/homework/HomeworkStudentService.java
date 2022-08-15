package com.yice.edu.cn.ewb.service.homework;


import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.StudentFindHomeworkVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.HomeworkVo;
import com.yice.edu.cn.ewb.feignClient.course.JwCourseFeign;
import com.yice.edu.cn.ewb.feignClient.homework.HomeworkStudentFeign;
import com.yice.edu.cn.ewb.feignClient.student.StudentFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myStudentId;

@Service
public class HomeworkStudentService {
    @Autowired
    private HomeworkStudentFeign homeworkStudentFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private JwCourseFeign jwCourseFeign;

    public List<HomeworkStudent> findHomeworkStudents4Bmp(StudentFindHomeworkVo studentFindHomeworkVo) {
        HomeworkStudent homeworkStudent = new HomeworkStudent();
        if(StringUtils.isNotEmpty(studentFindHomeworkVo.getSubjectId()))
            homeworkStudent.setSubjectId(studentFindHomeworkVo.getSubjectId());
        Homework homework = new Homework();
        homework.setHomeworkName(studentFindHomeworkVo.getHomeworkName());
        homework.setType(2);
        homeworkStudent.setHomeworkObj(homework);
        homeworkStudent.setStudentId(myStudentId());
        homeworkStudent.setStatus(studentFindHomeworkVo.getStatus());
        homeworkStudent.setPager(studentFindHomeworkVo.getPager());
        return homeworkStudentFeign.findHomeworkStudents4Bmp(homeworkStudent);
    }
    public ResponseJson findHomeworkSubject() {
        List<JwCourse> courseList = new ArrayList<>();
        JwCourse jwCourse = new JwCourse();
        jwCourse.setId("");
        jwCourse.setAlias("全部");
        courseList.add(jwCourse);
        jwCourse = new JwCourse();
        Student student = studentFeign.findStudentById(myStudentId());
        if(student == null)
            return new ResponseJson(false,"学生信息错误");
        jwCourse.setSchoolId(student.getSchoolId());
        jwCourse.setGradeId(student.getGradeId());
        Pager pager = new Pager();
        pager.setIncludes("id","alias");
        pager.setSortField("createTime").setSortOrder("desc");
        pager.setPageSize(100);
        jwCourse.setPager(pager);
        courseList.addAll(jwCourseFeign.findJwCourseListByConditionGai(jwCourse));
        return new ResponseJson(courseList);
    }

    public HomeworkStudent findHomeworkStudentById(String id) {
        return homeworkStudentFeign.findHomeworkStudentById(id);
    }

    public ResponseJson studentSubmitHomework(HomeworkVo homeworkVo){
        return homeworkStudentFeign.studentSubmitHomework(homeworkVo);
    }

    public HomeworkStudent findOneHomeworkStudentByCondition(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.findOneHomeworkStudentByCondition(homeworkStudent);
    }

    public List<HomeworkStudent> findHomeworkStudentListByCondition(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.findHomeworkStudentListByCondition(homeworkStudent);
    }

    public long findHomeworkStudentCountByCondition(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.findHomeworkStudentCountByCondition(homeworkStudent);
    }
}
