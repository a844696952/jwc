package com.yice.edu.cn.bmp.service.homework;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.bmp.feignClient.course.JwCourseFeign;
import com.yice.edu.cn.bmp.feignClient.homework.HomeworkStudentFeign;
import com.yice.edu.cn.bmp.feignClient.school.SchoolFeign;
import com.yice.edu.cn.bmp.feignClient.schoolYear.SchoolYearFeign;
import com.yice.edu.cn.bmp.feignClient.student.StudentFeign;
import com.yice.edu.cn.bmp.feignClient.topics.WrongTopicsFeign;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.JwCourse.app.SubjectVo;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkStudent;
import com.yice.edu.cn.common.pojo.jy.homework.StudentFindHomeworkVo;
import com.yice.edu.cn.common.pojo.jy.homework.app.HomeworkVo;
import com.yice.edu.cn.common.pojo.jy.topics.WrongTopics;
import com.yice.edu.cn.common.pojo.jy.topics.app.WrongTopicsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;
import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@Service
public class HomeworkStudentService {
    @Autowired
    private HomeworkStudentFeign homeworkStudentFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private JwCourseFeign courseFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private WrongTopicsFeign wrongTopicsFeign;
    @Autowired
    private JwCourseFeign jwCourseFeign;
    @Autowired
    private SchoolYearFeign schoolYearFeign;
    @Autowired
    private SchoolFeign schoolFeign;

    public HomeworkStudent findHomeworkStudentById(String id) {
        return homeworkStudentFeign.findHomeworkStudentById(id);
    }

    public HomeworkStudent saveHomeworkStudent(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.saveHomeworkStudent(homeworkStudent);
    }

    public List<HomeworkStudent> findHomeworkStudentListByCondition(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.findHomeworkStudentListByCondition(homeworkStudent);
    }

    public HomeworkStudent findOneHomeworkStudentByCondition(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.findOneHomeworkStudentByCondition(homeworkStudent);
    }

    public long findHomeworkStudentCountByCondition(HomeworkStudent homeworkStudent) {
        return homeworkStudentFeign.findHomeworkStudentCountByCondition(homeworkStudent);
    }

    public void updateHomeworkStudent(HomeworkStudent homeworkStudent) {
        homeworkStudentFeign.updateHomeworkStudent(homeworkStudent);
    }

    public void deleteHomeworkStudent(String id) {
        homeworkStudentFeign.deleteHomeworkStudent(id);
    }

    public void deleteHomeworkStudentByCondition(HomeworkStudent homeworkStudent) {
        homeworkStudentFeign.deleteHomeworkStudentByCondition(homeworkStudent);
    }
    public ResponseJson studentSubmitHomework(HomeworkVo homeworkVo){
        return homeworkStudentFeign.studentSubmitHomework(homeworkVo);
    }

    public List<SubjectVo> getSubject4Wrong() {
        //查询学生
        Student student = studentFeign.findStudentById(myStudentId());
        //获取学生科目
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(student.getSchoolId());
        //jwCourse.setGradeId(student.getGradeId());
        List<JwCourse> courseList = courseFeign.findJwCourseListByConditionGai(jwCourse);
        //转换为输出类型
        return courseList.stream().map(c->{
            SubjectVo subjectVo = new SubjectVo();
            subjectVo.setSubjectId(c.getId());
            subjectVo.setSubjectName(c.getName());
            //添加新错题数量
            //redisTemplate.opsForValue().increment("WRONG_TOPIC_"+"_"+c.getId()+"_"+studentId,1);
            String a = String.valueOf(stringRedisTemplate.opsForValue().get("WRONG_TOPIC_"+"_"+c.getId()+"_"+myStudentId()));
            subjectVo.setNewCount(new Integer(a.equals("null")||a==null?"0":a));
            return subjectVo;
        }).collect(Collectors.toList());
    }

    public WrongTopics getWrongTopicsListBySubject(WrongTopicsVo wrongTopicsVo) {
        WrongTopics wrongTopics = new WrongTopics();
        wrongTopics.setStudentId(myStudentId());
        wrongTopics.setSubjectId(wrongTopicsVo.getSubjectId());
        wrongTopics.setPager(new Pager ().setPageSize(1).setPage(wrongTopicsVo.getTopicsNum()).setSortField("answerTime").setSortOrder("desc"));
        //移除缓存数量
        stringRedisTemplate.delete("WRONG_TOPIC_"+"_"+wrongTopicsVo.getSubjectId()+"_"+myStudentId());
        return wrongTopicsFeign.findWrongTopicsListByCondition(wrongTopics).get(0);
    }

    public List<HomeworkStudent> findTodayHomeworkByStudent(String studentId) {
        //获取今天日期
        String nowDay = DateUtil.today();
        HomeworkStudent homeworkStudent = new HomeworkStudent();
        homeworkStudent.setStudentId(studentId);
        Homework homework = new Homework();
        homework.setPublishTime(nowDay);//获取发布日期为今天的作业
        homeworkStudent.setHomeworkObj(homework);
        return homeworkStudentFeign.findTodayHomeworkByStudent(homeworkStudent);
    }
    public List<HomeworkStudent> findHomeworkStudents4Bmp(StudentFindHomeworkVo studentFindHomeworkVo) {
        studentFindHomeworkVo.getPager().setLike("homeworkName"); //增加作业名称模糊查询
        HomeworkStudent homeworkStudent = new HomeworkStudent();
        if(StringUtils.isNotEmpty(studentFindHomeworkVo.getSubjectId()))
            homeworkStudent.setSubjectId(studentFindHomeworkVo.getSubjectId());
        Homework homework = new Homework();
        homework.setHomeworkName(studentFindHomeworkVo.getHomeworkName());
        homeworkStudent.setHomeworkObj(homework);
        homeworkStudent.setStudentId(myStudentId());
        homeworkStudent.setStatus(studentFindHomeworkVo.getStatus());
        homeworkStudent.setPager(studentFindHomeworkVo.getPager());

        CurSchoolYear curSchoolYear = schoolYearFeign.findCurSchoolYear(mySchoolId());
        School schoolInfo = schoolFeign.findSchoolById(mySchoolId());
        /**
         * 本学年本学期 要调整修改 添加字段完之后这边 同时要修改
         */
        switch (studentFindHomeworkVo.getType()){
            case 0://本学年
                //homeworkStudent.setCreateTime(schoolInfo.getLastTermTime()+" 00:00:00");
                homeworkStudent.setSchoolYearId(curSchoolYear.getSchoolYearId());
                homeworkStudent.setFromTo(curSchoolYear.getFromTo());
                break;
            case 1://本学期
                /*if(curSchoolYear.getTerm()==0){
                    //本学期是上学期
                   // homeworkStudent.setCreateTime(schoolInfo.getLastTermTime()+" 00:00:00");
                }else{
                    //本学期是下学期
                   // homeworkStudent.setCreateTime(schoolInfo.getNextTermTime()+" 00:00:00");
                }*/
                homeworkStudent.setSchoolYearId(curSchoolYear.getSchoolYearId());
                homeworkStudent.setFromTo(curSchoolYear.getFromTo());
                homeworkStudent.setTerm(curSchoolYear.getTerm());
                break;
            case 2://近一月内
                homeworkStudent.setSchoolYearId(curSchoolYear.getSchoolYearId());
                homeworkStudent.setCreateTime(DateUtil.formatDate(DateUtil.beginOfMonth(DateUtil.date()))+" 00:00:00");
                break;
            case 3://近一周内
                homeworkStudent.setSchoolYearId(curSchoolYear.getSchoolYearId());
                homeworkStudent.setCreateTime(DateUtil.formatDate(DateUtil.beginOfWeek(DateUtil.date()))+" 00:00:00");
                break;
        }
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
}
