package com.yice.edu.cn.bmp.controller.correspondence;

import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.bmp.service.teacher.TeacherPostService;
import com.yice.edu.cn.bmp.service.teacher.TeacherService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myStudentId;

@RestController
@RequestMapping("/correspondence")
@Api(value = "/correspondence", description = "通讯录模块")
public class CorrespondenceController {

    @Autowired
    private TeacherPostService teacherPostService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/findStudentTeachers")
    @ApiOperation(value = "(im接口)根据条件查找老师通讯录，不传参的话查询的是当前学校的所有老师通讯录，再传入老师名字(name)的话可以模糊查询,可选参数registerStatus0(字符串类型)：未注册，1(字符串类型)：已经注册", notes = "返回响应对象(加入可选参数决定返回是否注册的对象信息)")
    public ResponseJson findStudentTeachers(@RequestBody Teacher teacher){
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        String studentId = myStudentId();
        Student student =  studentService.findStudentById(studentId);
        teacher.setSchoolId(student.getSchoolId());
        teacher.setClassId(student.getClassesId());
        List<Teacher> myTeacherlist = teacherService.findStudentTeachers(teacher);

        //心理老师
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setTeacherName(teacher.getName());
        Pager pager = new Pager();
        pager.setLike("teacherName");
        pager.setPaging(false);
        teacherPost.setDdId(Constant.TEACHER_POST.COUNSELING);
        teacherPost.setSchoolId(student.getSchoolId());
        teacherPost.setPager(pager);
        List<Teacher> pshcholgConsultTeachers = teacherPostService.findTeachersByPost(teacherPost);
        Map<String,Object> list = new HashMap<>();
        list.put("myTeacherlist",myTeacherlist);
        list.put("pshcholgConsultTeachers",pshcholgConsultTeachers);
        return new ResponseJson(list);
    }

    @PostMapping("/findCorrespondencesByCondition")
    @ApiOperation(value = "(可分页)根据条件查找老师通讯录，不传参的话查询的是当前学校的所有老师通讯录，再传入老师名字(name)的话可以模糊查询", notes = "返回响应对象")
    public ResponseJson findCorrespondencesByCondition(@RequestBody Teacher teacher){
            teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
            String studentId = myStudentId();
            Student student =  studentService.findStudentById(studentId);
            teacher.setSchoolId(student.getSchoolId());
            teacher.setClassId(student.getClassesId());
            teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);//只查询教师
            List<Teacher> list = teacherService.findCorrespondencesByTeacher(teacher);
            Long count = teacherService.findTeacherCountByCondition4Like(teacher);
            return new ResponseJson(list,count);
    }

    @GetMapping("/look/lookTeacherById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找教师信息", notes = "返回响应对象")
    public ResponseJson lookTeacherById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Teacher teacher = teacherService.findTeacherById(id);
        return new ResponseJson(teacher);
    }
}
