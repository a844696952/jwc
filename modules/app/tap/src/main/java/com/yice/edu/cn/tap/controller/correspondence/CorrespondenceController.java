package com.yice.edu.cn.tap.controller.correspondence;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.tap.service.classes.JwClassesService;
import com.yice.edu.cn.tap.service.student.StudentService;
import com.yice.edu.cn.tap.service.teacher.TeacherPostService;
import com.yice.edu.cn.tap.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/correspondence")
@Api(value = "/correspondence", description = "通讯录模块")
public class CorrespondenceController {


    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherPostService teacherPostService;
    @Autowired
    private JwClassesService jwClassesService;

    @PostMapping("/findTeacherClassInfo")
    @ApiOperation(value = "(教师IM第二期接口1)查找老师所教的班级和班级人数(可选参数registerStatus:0为计算未注册人数，1为已注册人数)", notes = "返回响应对象(title和count属性)")
    public ResponseJson findTeacherClassInfoim(@RequestBody Student student){
        student.setSchoolId(mySchoolId());//学校id
        student.setTeacherId(myId());//教师id
        List<Student> data = studentService.findTeacherClassListim(student);
        return new ResponseJson(data);
    }

    @PostMapping("/findStudentByTitleim")
    @ApiOperation(value = "(教师IM第二期接口2)查找班级对应的学生信息(需要传入classesId和title)(可选参数registerStatus 0(字符串类型)：未注册，1(字符串类型)：已经注册)", notes = "返回学生信息(包含学生家庭信息)")
    public ResponseJson findStudentByTitleim(@RequestBody Student student){
        student.setTitle(student.getTitle());
        student.setSchoolId(mySchoolId());//学校id
        student.setTeacherId(myId());//教师id
        List<Student> data = studentService.findStudentByTitleim(student);
        return new ResponseJson(data);
    }

    @PostMapping("/findTeacherList")
    @ApiOperation(value = "(教师IM第二期接口4)我的同事(可选参数registerStatus0：未注册，1：已经注册)", notes = "返回响应对象")
    public ResponseJson findTeacherList(@RequestBody Teacher teacher){
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setSchoolId(mySchoolId());
        teacher.setId(myId());//排除自身教师
        teacher.setPersonType(currentTeacher().getPersonType());
//        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);//查询教师
        if(teacher.getRegisterStatus()!=null && "".equals(teacher.getRegisterStatus())){//为iso传参新增逻辑
            teacher.setRegisterStatus(null);
        }
        //添加职位名称
        List<Teacher> list = teacherService.findTeacherListByConditionRegister(teacher);
        for (Teacher t : list) {
            TeacherPost teacherPost = new TeacherPost();
            teacherPost.setTeacherId(t.getId());
            List<TeacherPost> teacherPostList = teacherPostService.findTeacherPostListByCondition(teacherPost);
            if(CollUtil.isNotEmpty(teacherPostList)) {
                t.setTeacherjob(teacherPostList.get(0).getName());
            }
        }
        Long count = teacherService.findTeacherCountByCondition4Like(teacher);
        return new ResponseJson(list,count);
    }

    @PostMapping("/findTeacherOrstudentByName")
    @ApiOperation(value = "(教师IM第二期接口5)根据老师名字和学生名字模糊查询", notes = "返回响应对象")
    public ResponseJson findTeacherOrstudentByName(@RequestBody Teacher teacher){
        Student s = new Student();
        if(teacher.getName()!=null && !"".equals(teacher.getName().trim())){
            s.setName(teacher.getName());
        }
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        teacher.setSchoolId(mySchoolId());
        if(teacher.getRegisterStatus()!=null && "".equals(teacher.getRegisterStatus().trim())){
            teacher.setRegisterStatus(teacher.getRegisterStatus().trim());
            s.setRegisterStatus(teacher.getRegisterStatus().trim());
        }
        Pager pager = new Pager();
        pager.setPaging(false);
        pager.setLike("name");
        pager.addExcludes("password");
        teacher.setId(myId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);//查询教师
        teacher.setPager(pager);
        List<Teacher> teacherResult = teacherService.findTeacherListByConditionRegister(teacher);
        List<Map<String,Object>> list_all = new ArrayList<>();
        if(teacherResult.size()>0){
            teacherResult.forEach(teacher1 -> {
                teacher1.setType("0");
                Map<String,Object> temp = BeanUtil.beanToMap(teacher1);
                list_all.add(temp);
            });
        }
        s.setTeacherId(myId());
        s.setSchoolId(mySchoolId());
        List<Student> studentResult = studentService.findStudentListByConditionim(s);
        if(studentResult.size()>0){
            studentResult.forEach(student -> {
                student.setType("1");
                Map<String,Object> temp = BeanUtil.beanToMap(student);
                JwClasses classes = null;
                if(student.getClassesId()!=null) {
                    classes = jwClassesService.findJwClassesById(student.getClassesId());
                }
                String className = "";
                if(classes!=null) {
                    className = classes.getGradeName()+"("+classes.getNumber()+")班";
                }
                temp.put("className", className);
                list_all.add(temp);
            });
        }
        return  new ResponseJson(list_all);
    }

    @PostMapping("/findTeacherOrstudentByType")
    @ApiOperation(value = "(教师IM第二期接口5)根据id,type", notes = "返回响应对象")
    public ResponseJson findTeacherOrstudentByType(@RequestBody Teacher teacher){
        ResponseJson t=null;
        if(teacher.getType().equals("0")){//老师
            teacher.setId(teacher.getId());
            teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);//在线的教师
            teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);//只查询老师
            List<Teacher>teacherResult = teacherService.findTeacherListByCondition(teacher);
            t=new ResponseJson(teacherResult);
        }else{//学生
            Student student = new Student();
            student.setId(teacher.getId());
            List<Student> studentResult = studentService.findStudentInfoAndFamily(student);
            t=new ResponseJson(studentResult);
        }
        return t;
    }

    @PostMapping("/findCorrespondencesByCondition")
    @ApiOperation(value = "( 可分页)根据条件查找老师通讯录，不传参的话查询的是当前学校的所有老师通讯录，再传入老师名字(name)的话可以模糊查询", notes = "返回响应对象")
    public ResponseJson findCorrespondencesByCondition(@RequestBody Teacher teacher){
//            Pager pager = new Pager();
//            teacher.setPager(pager);
            teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
            teacher.setSchoolId(mySchoolId());
            teacher.setId(myId());
            List<Teacher> list = teacherService.findCorrespondencesByTeacher(teacher);
            Long count = teacherService.findTeacherCountByCondition4Like(teacher);
            return new ResponseJson(list,count);
        }

    @PostMapping("/ignore/findCorrespondencesByStudentApp")
    @ApiOperation(value = "根据条件查找学生通讯录不传参的话查询的是当前老师所教班级的学生通讯录，也可以传入学生名字(name)或学籍号()的话可以模糊查询", notes = "返回响应对象")
    public ResponseJson findCorrespondencesByStudentApp(@RequestBody Student student){
//        Pager pager = new Pager();
//        student.setPager(pager);
        student.setSchoolId(mySchoolId());//学校id
        student.setTeacherId(myId());//教师id
        List<Student> data = studentService.findCorrespondencesByStudent(student);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/findTeacherClassList")
    @ApiOperation(value = "无需传参直接调用查出当前老师所教的班级", notes = "返回当前教师对应所教的班级(学生通讯录)")
    public ResponseJson findTeacherClassList(@RequestBody Student student){
        Pager pager = new Pager();
        student.setPager(pager);
        student.setSchoolId(mySchoolId());//学校id
        student.setTeacherId(myId());//教师id
        List<Student> data = studentService.findTeacherClassList(student);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/findClassStudentByclassTitle")
    @ApiOperation(value = "班级classesId且不为空查出学生记录，当模糊查询的时候只要输入姓名(name)或学号(studentCode)就行", notes = "返回当前点击班级，教师对应所教的学生信息和数量(学生通讯录)")
    public ResponseJson findClassStudentByclassTitle(@RequestBody Student student){
        Pager pager = new Pager();
        student.setPager(pager);
        student.setSchoolId(mySchoolId());//学校id
        student.setTeacherId(myId());//教师id
        List<Student> data = studentService.findClassStudentByclassTitle(student);
        return new ResponseJson(data);
    }
}
