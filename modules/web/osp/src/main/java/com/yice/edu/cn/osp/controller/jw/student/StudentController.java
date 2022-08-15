package com.yice.edu.cn.osp.controller.jw.student;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.google.common.collect.Lists;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.student.ClassesStudentScoreNum;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.student.StudentFamily;
import com.yice.edu.cn.common.pojo.validateClass.GroupThree;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.parent.ParentService;
import com.yice.edu.cn.osp.service.jw.student.StudentFamilyService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/student")
@Api(value = "/student", description = "学生信息模块")
public class StudentController {

    private Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private DormPersonService dormPersonService;
    @Autowired
    private StudentFamilyService studentFamilyService;

    @PostMapping("/save/saveStudent")
    @ApiOperation(value = "保存学生信息对象", notes = "返回响应对象")
    public ResponseJson saveStudent(
            @ApiParam(value = "学生信息对象", required = true)
            @RequestBody @Validated({GroupTwo.class}) Student student) {
        if (student.getStudentNo() == null) {
            return new ResponseJson(false, "学号不能为空");
        }
        Student ss = new Student();
        ss.setStudentCode(student.getStudentCode());
        student.setSchoolId(mySchoolId());
        student.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.STUDENT_DEFAULT_PWD)));
        if(StrUtil.isNotBlank(student.getStudentCode())) {
            long c = studentService.findStudentCodeCountByCondition(ss);   //判断学籍号是否存在
            if (c != 0) {
                return new ResponseJson(false, "添加失败，学籍号已存在！");
            }
        }
        long num = studentService.findStudentCountByNoAndSchoolId(student);
        if (num != 0) {
            return new ResponseJson(false, "添加失败，学号已存在！");
        }
        student.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT);  //默认 请假(离校)
        student.setBoarder(Constant.STUDENT_BOARDER_TYPE.BOARDER_NOT);
        Student s = studentService.saveStudent(student);
        return new ResponseJson(s);

    }

    @GetMapping("/update/findStudentById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Student student = studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @PostMapping("/update/updateStudent")
    @ApiOperation(value = "修改学生信息对象", notes = "返回响应对象")
    public ResponseJson updateStudent(
            @ApiParam(value = "被修改的学生信息对象,对象属性不为空则修改", required = true)
            @RequestBody @Validated({GroupThree.class}) Student student) {
        if (student.getStudentNo() == null) {
            return new ResponseJson(false, "学号不能为空");
        }
        Student student1 = new Student();
        student1.setId(student.getId());
        student1.setStudentCode(student.getStudentCode());
        student.setSchoolId(mySchoolId());
        //判断学籍号是否存在
        if(StrUtil.isNotBlank(student.getStudentCode())) {
            if (studentService.findStudentCountByConditionForUpdate(student1) > 0) {
                return new ResponseJson(false, "修改失败，学籍号已存在！");
            }
        }
        if (studentService.findStudentNoCountByStudentNoExceptSelf(student) > 1) {
            return new ResponseJson(false, "修改失败，学号已存在");
        }
//        //查询是否有住宿信息
//        DormPerson dormPersonQuery = new DormPerson();
//        dormPersonQuery.setPersonId(student.getId());
//        dormPersonQuery.setPersonType("1");
//        long dormCount = dormPersonService.findDormPersonCountByCondition(dormPersonQuery);
//        //如果有住宿信息则学生修改信息无法修改住宿状态
//        if(dormCount>0){
//            student.setBoarder(null);
//        }
        studentService.updateStudentNew(student);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStudentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生信息", notes = "返回响应对象")
    public ResponseJson lookStudentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Student student = studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @GetMapping("/update/searchStudentById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生信息", notes = "返回响应对象")
    public ResponseJson searchStudentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Student student = studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @PostMapping("/find/findStudentsByCondition")
    @ApiOperation(value = "根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student) {

        List<Student> data = studentService.findStudentListByCondition(student);
        long count = studentService.findStudentCountByCondition(student);
        return new ResponseJson(data, count);
    }

    @GetMapping("/delete/deleteStudent/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStudent(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        Parent parent = parentService.findParentByStudentId(id);
        if (parent == null) {
            studentService.deleteStudent(id);
            return new ResponseJson(true, "删除成功");
        } else {
            return new ResponseJson(false, "当前学生已被绑定，请先解绑后再删除");
        }
    }

    @GetMapping("/deleteStudentByCondition")
    @ApiOperation(value = "根据条件删除学生信息", notes = "返回响应对象")
    public ResponseJson deleteStudentByCondition(
            @ApiParam(value = "被删除的学生信息对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody Student student) {
        studentService.deleteStudentByCondition(student);
        return new ResponseJson();
    }

    @PostMapping("/find/findStudentListByConditionWithFamily")
    @ApiOperation(value = "根据条件查找学生信息", notes = "返回响应对象")
    public ResponseJson findStudentListByConditionWithFamily(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        String postId = student.getPostId();
        //有传职务时候再做判断
        if (StrUtil.isBlank(postId)) {
            return new ResponseJson(Lists.newArrayList(), 0);
        }
        List<String> gradeIds = null;
        if (StrUtil.isBlank(student.getGradeId())) {
            List<Dd> teacherOwnGrade = jwClassesService.getTeacherOwnGrade(postId, myId(), mySchoolId());
            gradeIds = teacherOwnGrade.stream().map(item -> item.getId()).collect(Collectors.toList());
            student.setGradeIdList(gradeIds);
        }
        List<String> clazzIds = CollUtil.newArrayList();
        if (StrUtil.isNotBlank(student.getGradeId()) && StrUtil.isBlank(student.getClassesId())) {
            clazzIds = jwClassesService.getTeacherOwnClazzId(postId, myId(), mySchoolId(), student.getGradeId());
            student.setClazzIdList(clazzIds);
        } else if (CollUtil.isNotEmpty(gradeIds) && StrUtil.isBlank(student.getClassesId())) {
            for (String gradeId : gradeIds) {
                List<String> teacherOwnClazzId = jwClassesService.getTeacherOwnClazzId(postId, myId(), mySchoolId(), gradeId);
                clazzIds.addAll(teacherOwnClazzId);
            }
            student.setClazzIdList(clazzIds);
        }
        List<Student> data = studentService.findStudentListByConditionWithFamily(student);
        long count = studentService.findStudentCountByConditionWithFamily(student);
        return new ResponseJson(data, count);
    }

    @PostMapping("/download")
    @ApiOperation(value = "导出学生信息", notes = "返回响应对象")
    public void exportStudent(@ApiParam(value = "学生信息对象")
                              @RequestBody Student student, HttpServletResponse response) {

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            student.setSchoolId(mySchoolId());
            Workbook workbook = studentService.exportStudent(student);
            workbook.write(s);
        } catch (Exception e) {
            log.info("学生导出异常:",e);
        }
    }

    @PostMapping("/class/download")
    @ApiOperation(value = "导出学生班级信息", notes = "返回响应对象")
    public void exportStudentClass(@ApiParam(value = "学生班级信息对象")
                                   @RequestBody Student student, HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            student.setSchoolId(mySchoolId());
            Workbook workbook = studentService.exportStudentClass(student);
            workbook.write(s);
        } catch (Exception e) {
            log.info("学生班级导出异常:",e);
        }
    }


    @GetMapping("/upload/exportTemplate")   //导入模板
    @ApiOperation(value = "导入学生信息模板", notes = "返回响应对象")
    public void exportTemplate(HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = studentService.exportTemplate();
            workbook.write(s);
        } catch (Exception e) {
            log.info("学生模板导出异常:",e);
        }
    }

    @GetMapping("/upload/exportClassTemplate")   //导入模板
    @ApiOperation(value = "导入学生班级信息模板", notes = "返回响应对象")
    public void exportClassTemplate(HttpServletResponse response) {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = studentService.exportClassTemplate();
            workbook.write(s);
        } catch (Exception e) {
            log.info("学生班级模板导出异常:",e);
        }
    }


    @PostMapping("/upload/uploadStudent")
    @ApiOperation(value = "导入学生信息", notes = "返回响应对象")
    public ResponseJson uploadStudent(MultipartFile file) {
        return new ResponseJson(studentService.uploadStudent(file, mySchoolId()));
    }


    @PostMapping("/upload/uploadStudentClass")
    @ApiOperation(value = "导入学生班级信息", notes = "返回响应对象")
    public ResponseJson uploadStudentClass(MultipartFile file) {
        return new ResponseJson(studentService.uploadStudentClass(file, mySchoolId()));
    }

    @PostMapping("/save/saveStudentFamily")
    @ApiOperation(value = "保存学生家庭信息对象", notes = "返回响应对象")
    public ResponseJson saveStudentFamily(
            @ApiParam(value = "学生家庭信息对象", required = true)
            @RequestBody StudentFamily[] studentFamilies) {
        for (int i = 0; i < studentFamilies.length; i++) {
            studentFamilies[i].setSchoolId(mySchoolId());
            studentFamilyService.saveStudentFamily(studentFamilies[i]);
        }
        return new ResponseJson();
    }

    @PostMapping("/update/updatesaveStudentFamily")
    @ApiOperation(value = "保存学生家庭信息对象", notes = "返回响应对象")
    public ResponseJson updatasaveStudentFamily(
            @ApiParam(value = "学生家庭信息对象", required = true)
            @RequestBody StudentFamily[] studentFamilies) {
        for (StudentFamily studentFamily : studentFamilies) {
            studentFamily.setSchoolId(mySchoolId());
            studentFamilyService.saveStudentFamily(studentFamily);
        }
        return new ResponseJson();
    }

    @GetMapping("/update/findStudentFamilyById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson findStudentFamilyById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        List<StudentFamily> studentFamily = studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }

    @PostMapping("/update/updateStudentFamily")
    @ApiOperation(value = "修改学生家庭信息对象", notes = "返回响应对象")
    public ResponseJson updateStudentFamily(
            @ApiParam(value = "被修改的学生家庭信息对象,对象属性不为空则修改", required = true)
            @RequestBody StudentFamily studentFamily) {
        studentFamilyService.updateStudentFamily(studentFamily);
        return new ResponseJson();
    }

    @PostMapping("/update/updateStudentFamilyArray")
    @ApiOperation(value = "修改家长的信息", notes = "不返回对象")
    public ResponseJson updateStudentFamily(
            @ApiParam(value = "家长的信息", required = true)
            @RequestBody StudentFamily[] studentFamilies
    ) {
        for (StudentFamily studentFamily : studentFamilies) {
            studentFamilyService.updateStudentFamily(studentFamily);
        }
        return new ResponseJson();
    }

    @GetMapping("/look/lookStudentFamilyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson lookStudentFamilyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        List<StudentFamily> studentFamily = studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }

    @GetMapping("/update/searchStudentFamilyById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson searchStudentFamilyById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        List<StudentFamily> studentFamily = studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }

    @PostMapping("/look/findStudentFamilysByCondition")
    @ApiOperation(value = "根据条件查找学生家庭信息", notes = "返回响应对象")
    public ResponseJson findStudentFamilysByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody StudentFamily studentFamily) {
        List<StudentFamily> data = studentFamilyService.findStudentFamilyListByCondition(studentFamily);
        long count = studentFamilyService.findStudentFamilyCountByCondition(studentFamily);
        return new ResponseJson(data, count);
    }

    @GetMapping("/delete/deleteStudentFamily/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteStudentFamily(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        studentFamilyService.deleteStudentFamily(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteStudentFamilyByCondition")
    @ApiOperation(value = "根据条件删除学生家庭信息", notes = "返回响应对象")
    public ResponseJson deleteStudentFamilyByCondition(
            @ApiParam(value = "被删除的学生家庭信息对象,对象属性不为空则作为删除条件", required = true)
            @RequestBody StudentFamily studentFamily) {
        studentFamilyService.deleteStudentFamilyByCondition(studentFamily);
        return new ResponseJson();
    }

    @PostMapping("/save/batchSaveStudentFamily")
    @ApiOperation(value = "批量保存学生家庭信息对象", notes = "返回响应对象")
    public ResponseJson batchSaveStudentFamily(
            @ApiParam(value = "学生家庭信息对象", required = true)
            @RequestBody List<StudentFamily> studentFamilyList) {
        List<StudentFamily> s = studentFamilyService.batchSaveStudentFamily(studentFamilyList);
        return new ResponseJson(s);
    }

    @PostMapping("/find/findJwClassessByCondition")
    @ApiOperation(value = "根据条件查找班级信息", notes = "返回响应对象")
    public ResponseJson findJwClassessByCondition(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JwClasses jwClasses) {
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> data = jwClassesService.findJwClassesListByCondition(jwClasses);
        return new ResponseJson(data);
    }

    @GetMapping("/update/findJwClassesById/{id}")
    @ApiOperation(value = "通过id查找班级信息", notes = "返回响应对象")
    public ResponseJson findJwClassesById(@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
        JwClasses jwClasses = jwClassesService.findJwClassesById(id);
        return new ResponseJson(jwClasses);
    }

    @GetMapping("/save/findJwClassesById/{id}")
    @ApiOperation(value = "通过id查找班级信息", notes = "返回响应对象")
    public ResponseJson savefindJwClassesById(@ApiParam(value = "需要用到的id", required = true) @PathVariable String id) {
        JwClasses jwClasses = jwClassesService.findJwClassesById(id);
        return new ResponseJson(jwClasses);
    }

    @GetMapping("/ignore/findClassesByGradeId/{gradeId}")
    public ResponseJson findClassesByGradeId(@PathVariable String gradeId) {
        JwClasses c = new JwClasses();
        c.setGradeId(gradeId);
        c.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setSortOrder("asc");
        pager.setSortField("number");
        pager.setPaging(false);
        c.setPager(pager);
        return new ResponseJson(jwClassesService.findJwClassesListByCondition(c));
    }

    @PostMapping("/save/findOneStudentMaxSeatNumberByCondition")
    @ApiOperation(value = "条件查询学生对象", notes = "返回学生信息")
    public ResponseJson findOneStudentMaxSeatNumberByCondition(
            @ApiParam(value = "学生信息对象", required = true)
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        Student s = studentService.findOneStudentMaxSeatNumberByCondition(student);
        return new ResponseJson(s);
    }

    @PostMapping("/save/findStudentRowNumber")
    public void findStudentRowNumber(
            @ApiParam(value = "学生信息对象", required = true)
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());  //设置学校id
        //student.setStatus("50");            //设置学生状态为在读
        studentService.findStudentRowNumber(student);
    }

    @GetMapping("/ignore/batchUpdateStudentRegisterStatus")
    @ApiOperation(value = "按学校一键激活学生IM", notes = "返回响应对象")
    public ResponseJson batchUpdateStudentRegisterStatus() {
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        long rt = studentService.batchUpdateStudentRegisterStatus(student);
        return new ResponseJson(rt);
    }

    @PostMapping("/findStudentCountClassesByCondition")
    @ApiOperation(value = "传入classesIds schoolId", notes = "返回总人数")
    public ResponseJson findStudentCountClassesByCondition(@RequestBody Map map) {
        map.put("schoolId", mySchoolId());
        List<ClassesStudentScoreNum> rt = studentService.findStudentCountClassesByCondition(map);
        return new ResponseJson(rt);
    }


    @PostMapping("/find/findStudentListByClassIdAndName")
    @ApiOperation(value = "根据班级id查找未分组学生信息", notes = "返回响应对象")
    public ResponseJson findStudentListByClassIdAndName(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        List<Student> students = studentService.findStudentListByClassIdAndName(student);
        return new ResponseJson(students);
    }


    @PostMapping("/update/uploadAvatar")
    @ApiOperation(value = "图片上传,根据图片名称去修改对应学生头像，返回成功或者失败")
    public ResponseJson uploadAvatar(MultipartFile file) {
        //获取图片名称
        return new ResponseJson(studentService.uploadAvatar(file));
    }

    @PostMapping("/update/resetPassword")
    @ApiOperation(value = "根据学生id重置学生密码为66666666，返回成功或者失败")
    public ResponseJson resetPassword(
            @ApiParam(value = "学生id的集合")
            @RequestBody List<String> ids) {
        studentService.resetPassword(ids);
        return new ResponseJson();
    }

    @PostMapping("/findStudentCountByStudentNoAndId")
    @ApiOperation(value = "通过学号查找学生数量排除自己")
    public ResponseJson findStudentCountByStudentNoAndId(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        long c = studentService.findStudentNoCountByStudentNoExceptSelf(student);
        if (c != 0) {
            return new ResponseJson(false, "学号已存在");
        }
        return new ResponseJson();
    }

    @PostMapping("/findStudentCountByStudentNo")
    @ApiOperation(value = "通过学号查找学生数量")
    public ResponseJson findStudentCountByStudentNo(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        long c = studentService.findStudentCountByNoAndSchoolId(student);
        if (c != 0) {
            return new ResponseJson(false, "学号已存在");
        }
        return new ResponseJson();
    }


}
