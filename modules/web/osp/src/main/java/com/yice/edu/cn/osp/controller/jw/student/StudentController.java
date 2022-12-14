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
@Api(value = "/student", description = "??????????????????")
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
    @ApiOperation(value = "????????????????????????", notes = "??????????????????")
    public ResponseJson saveStudent(
            @ApiParam(value = "??????????????????", required = true)
            @RequestBody @Validated({GroupTwo.class}) Student student) {
        if (student.getStudentNo() == null) {
            return new ResponseJson(false, "??????????????????");
        }
        Student ss = new Student();
        ss.setStudentCode(student.getStudentCode());
        student.setSchoolId(mySchoolId());
        student.setPassword(DigestUtil.sha1Hex(DigestUtil.md5Hex(Constant.STUDENT_DEFAULT_PWD)));
        if(StrUtil.isNotBlank(student.getStudentCode())) {
            long c = studentService.findStudentCodeCountByCondition(ss);   //???????????????????????????
            if (c != 0) {
                return new ResponseJson(false, "????????????????????????????????????");
            }
        }
        long num = studentService.findStudentCountByNoAndSchoolId(student);
        if (num != 0) {
            return new ResponseJson(false, "?????????????????????????????????");
        }
        student.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.LEAVE_OUT);  //?????? ??????(??????)
        student.setBoarder(Constant.STUDENT_BOARDER_TYPE.BOARDER_NOT);
        Student s = studentService.saveStudent(student);
        return new ResponseJson(s);

    }

    @GetMapping("/update/findStudentById/{id}")
    @ApiOperation(value = "???????????????,??????id??????????????????", notes = "??????????????????")
    public ResponseJson findStudentById(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @PathVariable String id) {
        Student student = studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @PostMapping("/update/updateStudent")
    @ApiOperation(value = "????????????????????????", notes = "??????????????????")
    public ResponseJson updateStudent(
            @ApiParam(value = "??????????????????????????????,??????????????????????????????", required = true)
            @RequestBody @Validated({GroupThree.class}) Student student) {
        if (student.getStudentNo() == null) {
            return new ResponseJson(false, "??????????????????");
        }
        Student student1 = new Student();
        student1.setId(student.getId());
        student1.setStudentCode(student.getStudentCode());
        student.setSchoolId(mySchoolId());
        //???????????????????????????
        if(StrUtil.isNotBlank(student.getStudentCode())) {
            if (studentService.findStudentCountByConditionForUpdate(student1) > 0) {
                return new ResponseJson(false, "????????????????????????????????????");
            }
        }
        if (studentService.findStudentNoCountByStudentNoExceptSelf(student) > 1) {
            return new ResponseJson(false, "??????????????????????????????");
        }
//        //???????????????????????????
//        DormPerson dormPersonQuery = new DormPerson();
//        dormPersonQuery.setPersonId(student.getId());
//        dormPersonQuery.setPersonType("1");
//        long dormCount = dormPersonService.findDormPersonCountByCondition(dormPersonQuery);
//        //??????????????????????????????????????????????????????????????????
//        if(dormCount>0){
//            student.setBoarder(null);
//        }
        studentService.updateStudentNew(student);
        return new ResponseJson();
    }

    @GetMapping("/look/lookStudentById/{id}")
    @ApiOperation(value = "???????????????,??????id??????????????????", notes = "??????????????????")
    public ResponseJson lookStudentById(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @PathVariable String id) {
        Student student = studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @GetMapping("/update/searchStudentById/{id}")
    @ApiOperation(value = "???????????????,??????id??????????????????", notes = "??????????????????")
    public ResponseJson searchStudentById(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @PathVariable String id) {
        Student student = studentService.findStudentById(id);
        return new ResponseJson(student);
    }

    @PostMapping("/find/findStudentsByCondition")
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????")
    public ResponseJson findStudentsByCondition(
            @ApiParam(value = "????????????????????????????????????")
            @RequestBody Student student) {

        List<Student> data = studentService.findStudentListByCondition(student);
        long count = studentService.findStudentCountByCondition(student);
        return new ResponseJson(data, count);
    }

    @GetMapping("/delete/deleteStudent/{id}")
    @ApiOperation(value = "??????id??????", notes = "??????????????????")
    public ResponseJson deleteStudent(
            @ApiParam(value = "??????????????????id", required = true)
            @PathVariable String id) {
        Parent parent = parentService.findParentByStudentId(id);
        if (parent == null) {
            studentService.deleteStudent(id);
            return new ResponseJson(true, "????????????");
        } else {
            return new ResponseJson(false, "???????????????????????????????????????????????????");
        }
    }

    @GetMapping("/deleteStudentByCondition")
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????")
    public ResponseJson deleteStudentByCondition(
            @ApiParam(value = "??????????????????????????????,??????????????????????????????????????????", required = true)
            @RequestBody Student student) {
        studentService.deleteStudentByCondition(student);
        return new ResponseJson();
    }

    @PostMapping("/find/findStudentListByConditionWithFamily")
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????")
    public ResponseJson findStudentListByConditionWithFamily(
            @ApiParam(value = "????????????????????????????????????")
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        String postId = student.getPostId();
        //??????????????????????????????
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
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    public void exportStudent(@ApiParam(value = "??????????????????")
                              @RequestBody Student student, HttpServletResponse response) {

        // ???????????????????????????????????????????????????
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            student.setSchoolId(mySchoolId());
            Workbook workbook = studentService.exportStudent(student);
            workbook.write(s);
        } catch (Exception e) {
            log.info("??????????????????:",e);
        }
    }

    @PostMapping("/class/download")
    @ApiOperation(value = "????????????????????????", notes = "??????????????????")
    public void exportStudentClass(@ApiParam(value = "????????????????????????")
                                   @RequestBody Student student, HttpServletResponse response) {
        // ???????????????????????????????????????????????????
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            student.setSchoolId(mySchoolId());
            Workbook workbook = studentService.exportStudentClass(student);
            workbook.write(s);
        } catch (Exception e) {
            log.info("????????????????????????:",e);
        }
    }


    @GetMapping("/upload/exportTemplate")   //????????????
    @ApiOperation(value = "????????????????????????", notes = "??????????????????")
    public void exportTemplate(HttpServletResponse response) {
        // ???????????????????????????????????????????????????
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = studentService.exportTemplate();
            workbook.write(s);
        } catch (Exception e) {
            log.info("????????????????????????:",e);
        }
    }

    @GetMapping("/upload/exportClassTemplate")   //????????????
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????")
    public void exportClassTemplate(HttpServletResponse response) {
        // ???????????????????????????????????????????????????
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = studentService.exportClassTemplate();
            workbook.write(s);
        } catch (Exception e) {
            log.info("??????????????????????????????:",e);
        }
    }


    @PostMapping("/upload/uploadStudent")
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    public ResponseJson uploadStudent(MultipartFile file) {
        return new ResponseJson(studentService.uploadStudent(file, mySchoolId()));
    }


    @PostMapping("/upload/uploadStudentClass")
    @ApiOperation(value = "????????????????????????", notes = "??????????????????")
    public ResponseJson uploadStudentClass(MultipartFile file) {
        return new ResponseJson(studentService.uploadStudentClass(file, mySchoolId()));
    }

    @PostMapping("/save/saveStudentFamily")
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????")
    public ResponseJson saveStudentFamily(
            @ApiParam(value = "????????????????????????", required = true)
            @RequestBody StudentFamily[] studentFamilies) {
        for (int i = 0; i < studentFamilies.length; i++) {
            studentFamilies[i].setSchoolId(mySchoolId());
            studentFamilyService.saveStudentFamily(studentFamilies[i]);
        }
        return new ResponseJson();
    }

    @PostMapping("/update/updatesaveStudentFamily")
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????")
    public ResponseJson updatasaveStudentFamily(
            @ApiParam(value = "????????????????????????", required = true)
            @RequestBody StudentFamily[] studentFamilies) {
        for (StudentFamily studentFamily : studentFamilies) {
            studentFamily.setSchoolId(mySchoolId());
            studentFamilyService.saveStudentFamily(studentFamily);
        }
        return new ResponseJson();
    }

    @GetMapping("/update/findStudentFamilyById/{id}")
    @ApiOperation(value = "???????????????,??????id????????????????????????", notes = "??????????????????")
    public ResponseJson findStudentFamilyById(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @PathVariable String id) {
        List<StudentFamily> studentFamily = studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }

    @PostMapping("/update/updateStudentFamily")
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????")
    public ResponseJson updateStudentFamily(
            @ApiParam(value = "????????????????????????????????????,??????????????????????????????", required = true)
            @RequestBody StudentFamily studentFamily) {
        studentFamilyService.updateStudentFamily(studentFamily);
        return new ResponseJson();
    }

    @PostMapping("/update/updateStudentFamilyArray")
    @ApiOperation(value = "?????????????????????", notes = "???????????????")
    public ResponseJson updateStudentFamily(
            @ApiParam(value = "???????????????", required = true)
            @RequestBody StudentFamily[] studentFamilies
    ) {
        for (StudentFamily studentFamily : studentFamilies) {
            studentFamilyService.updateStudentFamily(studentFamily);
        }
        return new ResponseJson();
    }

    @GetMapping("/look/lookStudentFamilyById/{id}")
    @ApiOperation(value = "???????????????,??????id????????????????????????", notes = "??????????????????")
    public ResponseJson lookStudentFamilyById(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @PathVariable String id) {
        List<StudentFamily> studentFamily = studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }

    @GetMapping("/update/searchStudentFamilyById/{id}")
    @ApiOperation(value = "???????????????,??????id????????????????????????", notes = "??????????????????")
    public ResponseJson searchStudentFamilyById(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @PathVariable String id) {
        List<StudentFamily> studentFamily = studentFamilyService.findStudentFamilyById(id);
        return new ResponseJson(studentFamily);
    }

    @PostMapping("/look/findStudentFamilysByCondition")
    @ApiOperation(value = "????????????????????????????????????", notes = "??????????????????")
    public ResponseJson findStudentFamilysByCondition(
            @ApiParam(value = "????????????????????????????????????")
            @RequestBody StudentFamily studentFamily) {
        List<StudentFamily> data = studentFamilyService.findStudentFamilyListByCondition(studentFamily);
        long count = studentFamilyService.findStudentFamilyCountByCondition(studentFamily);
        return new ResponseJson(data, count);
    }

    @GetMapping("/delete/deleteStudentFamily/{id}")
    @ApiOperation(value = "??????id??????", notes = "??????????????????")
    public ResponseJson deleteStudentFamily(
            @ApiParam(value = "??????????????????id", required = true)
            @PathVariable String id) {
        studentFamilyService.deleteStudentFamily(id);
        return new ResponseJson();
    }

    @GetMapping("/deleteStudentFamilyByCondition")
    @ApiOperation(value = "????????????????????????????????????", notes = "??????????????????")
    public ResponseJson deleteStudentFamilyByCondition(
            @ApiParam(value = "????????????????????????????????????,??????????????????????????????????????????", required = true)
            @RequestBody StudentFamily studentFamily) {
        studentFamilyService.deleteStudentFamilyByCondition(studentFamily);
        return new ResponseJson();
    }

    @PostMapping("/save/batchSaveStudentFamily")
    @ApiOperation(value = "????????????????????????????????????", notes = "??????????????????")
    public ResponseJson batchSaveStudentFamily(
            @ApiParam(value = "????????????????????????", required = true)
            @RequestBody List<StudentFamily> studentFamilyList) {
        List<StudentFamily> s = studentFamilyService.batchSaveStudentFamily(studentFamilyList);
        return new ResponseJson(s);
    }

    @PostMapping("/find/findJwClassessByCondition")
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????")
    public ResponseJson findJwClassessByCondition(@ApiParam(value = "????????????????????????????????????") @RequestBody JwClasses jwClasses) {
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> data = jwClassesService.findJwClassesListByCondition(jwClasses);
        return new ResponseJson(data);
    }

    @GetMapping("/update/findJwClassesById/{id}")
    @ApiOperation(value = "??????id??????????????????", notes = "??????????????????")
    public ResponseJson findJwClassesById(@ApiParam(value = "???????????????id", required = true) @PathVariable String id) {
        JwClasses jwClasses = jwClassesService.findJwClassesById(id);
        return new ResponseJson(jwClasses);
    }

    @GetMapping("/save/findJwClassesById/{id}")
    @ApiOperation(value = "??????id??????????????????", notes = "??????????????????")
    public ResponseJson savefindJwClassesById(@ApiParam(value = "???????????????id", required = true) @PathVariable String id) {
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
    @ApiOperation(value = "????????????????????????", notes = "??????????????????")
    public ResponseJson findOneStudentMaxSeatNumberByCondition(
            @ApiParam(value = "??????????????????", required = true)
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        Student s = studentService.findOneStudentMaxSeatNumberByCondition(student);
        return new ResponseJson(s);
    }

    @PostMapping("/save/findStudentRowNumber")
    public void findStudentRowNumber(
            @ApiParam(value = "??????????????????", required = true)
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());  //????????????id
        //student.setStatus("50");            //???????????????????????????
        studentService.findStudentRowNumber(student);
    }

    @GetMapping("/ignore/batchUpdateStudentRegisterStatus")
    @ApiOperation(value = "???????????????????????????IM", notes = "??????????????????")
    public ResponseJson batchUpdateStudentRegisterStatus() {
        Student student = new Student();
        student.setSchoolId(mySchoolId());
        long rt = studentService.batchUpdateStudentRegisterStatus(student);
        return new ResponseJson(rt);
    }

    @PostMapping("/findStudentCountClassesByCondition")
    @ApiOperation(value = "??????classesIds schoolId", notes = "???????????????")
    public ResponseJson findStudentCountClassesByCondition(@RequestBody Map map) {
        map.put("schoolId", mySchoolId());
        List<ClassesStudentScoreNum> rt = studentService.findStudentCountClassesByCondition(map);
        return new ResponseJson(rt);
    }


    @PostMapping("/find/findStudentListByClassIdAndName")
    @ApiOperation(value = "????????????id???????????????????????????", notes = "??????????????????")
    public ResponseJson findStudentListByClassIdAndName(
            @ApiParam(value = "????????????????????????????????????")
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        List<Student> students = studentService.findStudentListByClassIdAndName(student);
        return new ResponseJson(students);
    }


    @PostMapping("/update/uploadAvatar")
    @ApiOperation(value = "????????????,????????????????????????????????????????????????????????????????????????")
    public ResponseJson uploadAvatar(MultipartFile file) {
        //??????????????????
        return new ResponseJson(studentService.uploadAvatar(file));
    }

    @PostMapping("/update/resetPassword")
    @ApiOperation(value = "????????????id?????????????????????66666666???????????????????????????")
    public ResponseJson resetPassword(
            @ApiParam(value = "??????id?????????")
            @RequestBody List<String> ids) {
        studentService.resetPassword(ids);
        return new ResponseJson();
    }

    @PostMapping("/findStudentCountByStudentNoAndId")
    @ApiOperation(value = "??????????????????????????????????????????")
    public ResponseJson findStudentCountByStudentNoAndId(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        long c = studentService.findStudentNoCountByStudentNoExceptSelf(student);
        if (c != 0) {
            return new ResponseJson(false, "???????????????");
        }
        return new ResponseJson();
    }

    @PostMapping("/findStudentCountByStudentNo")
    @ApiOperation(value = "??????????????????????????????")
    public ResponseJson findStudentCountByStudentNo(
            @ApiParam(value = "???????????????,???????????????id", required = true)
            @RequestBody Student student) {
        student.setSchoolId(mySchoolId());
        long c = studentService.findStudentCountByNoAndSchoolId(student);
        if (c != 0) {
            return new ResponseJson(false, "???????????????");
        }
        return new ResponseJson();
    }


}
