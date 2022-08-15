package com.yice.edu.cn.osp.controller.jy.myworkbench;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassSchedule;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ScheduleList;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.journal.NewestJournal;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.jw.classSchedule.ScheduleListService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.jy.homework.HomeworkService;
import com.yice.edu.cn.osp.service.jy.myworkbench.MyWorkbenchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/myWorkbench")
@Api(value = "/myWorkbench", description = "我的工作台模块")
public class MyWorkbenchController {
    @Autowired
    private MyWorkbenchService myWorkbenchService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private CurSchoolYearService curSchoolYearService;
    @Autowired
    private ScheduleListService scheduleListService;

    @PostMapping("/ignore/myClassSchedule")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findList() {
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setTeacherId(LoginInterceptor.myId());
        classSchedule.setSchoolId(LoginInterceptor.mySchoolId());
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.setType(1);
        scheduleList.setSchoolId(mySchoolId());
        ScheduleList scheduleList1 = scheduleListService.findOneScheduleListByCondition(scheduleList);
        if(scheduleList1!=null){
            classSchedule.setScheduleId(scheduleList1.getId());
        }
        List<List<ClassSchedule>> list = myWorkbenchService.findList(classSchedule);
        return new ResponseJson(list);
    }
    @GetMapping("/ignore/findNewestJournalsForWorkbench")
    @ApiOperation(value = "为工作台查询最新日志,包含本校教师,和所教班级")
    public ResponseJson findNewestJournalsForWorkbench(){
        NewestJournal newestJournal=myWorkbenchService.findNewestJournalsForWorkbench();
        return new ResponseJson(newestJournal.getTeachers(),newestJournal.getStudents());

    }

    @PostMapping("/ignore/findCorrespondencesByCondition")
    @ApiOperation(value = "根据条件查找老师通讯录", notes = "返回响应对象")
    public ResponseJson findCorrespondencesByCondition(@RequestBody Teacher teacher){
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setSchoolId(mySchoolId());
        teacher.setId(myId());//排除自身
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);//只查询教师
        List<Teacher> list = teacherService.findCorrespondencesByTeacher(teacher);
        Long count = teacherService.findTeacherCountByCondition4Like(teacher);
        return new ResponseJson(list,count);
    }

    @PostMapping("/ignore/findTeacherClassList")
    @ApiOperation(value = "(学生通讯录子接口)无需传参直接调用查出当前老师所教的班级", notes = "返回当前教师对应所教的班级(学生通讯录)")
    public ResponseJson findTeacherClassList(@RequestBody Student student){
        Pager pager = new Pager();
        pager.setPaging(false);
        student.setPager(pager);
        student.setSchoolId(mySchoolId());//学校id
        student.setTeacherId(myId());//教师id
        List<Student> data = studentService.findTeacherClassList(student);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/findClassStudentByclassTitle")
    @ApiOperation(value = "(学生通讯录子接口)班级classesId且不为空查出学生记录，当模糊查询的时候只要输入姓名(name)或学号(studentCode)就行", notes = "返回当前点击班级，教师对应所教的学生信息和数量(学生通讯录)")
    public ResponseJson findClassStudentByclassTitle(@RequestBody Student student){
        Pager pager = new Pager();
        pager.setPaging(false);
        student.setPager(pager);
        student.setSchoolId(mySchoolId());//学校id
        student.setTeacherId(myId());//教师id
        List<Student> data = studentService.findClassStudentByclassTitle(student);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/findCorrespondencesByStudent")
    @ApiOperation(value = "(学生通讯录旧接口建议不使用)根据条件查找学生通讯录", notes = "返回响应对象")
    public ResponseJson findCorrespondencesByStudent(@RequestBody Student student){
        Pager pager = new Pager();
        pager.setPaging(false);
        student.setPager(pager);
        student.setSchoolId(mySchoolId());//学校id
        student.setTeacherId(myId());//教师id
        List<Student> data = studentService.findCorrespondencesByStudent(student);
        if(!data.isEmpty()){
            data.forEach(s->s.setTitle(s.getTitle()+"班"));
        }
        return new ResponseJson(data);
    }



    @GetMapping("/ignore/findHomeworksByCondition")
    @ApiOperation(value = "查找当前教师布置作业", notes = "返回响应对象")
    public ResponseJson findHomeworksByCondition() {
        Homework homework = new Homework();
        homework.setTeacherId(myId());
        curSchoolYearService.setSchoolYearId(homework,mySchoolId());
        Pager pager = new Pager();
        pager.setSortField("createTime").setSortOrder(Pager.DESC).setPageSize(3);
        homework.setPager(pager);
        List<Homework> data = homeworkService.findHomeworkListByCondition(homework);
        return new ResponseJson(data);
    }
}
