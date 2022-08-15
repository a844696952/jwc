package com.yice.edu.cn.osp.controller.dm.ycVeriface;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcDataToRegisterPage;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcStuAddBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceOtherCheckBean;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.xw.kq.*;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifaceOtherCheckService;
import com.yice.edu.cn.osp.service.dm.ycVeriface.YcVerifacePersonEnterService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/YcVerifaceStudentEnter")
@Api(value = "/YcVerifaceStudentEnter", description = "亿策人脸识别学生人像录入")
public class YcVerifaceStudentEnterController {
    @Autowired
    private YcVerifacePersonEnterService ycVerifacePersonEnterService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private YcVerifaceOtherCheckService ycVerifaceOtherCheckService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private DdService ddService;
    @Autowired
    private JwClassesService jwClassesService;
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;

    //上传学生头像
    //批量上传图片校验
    @PostMapping("/ycBatchEnterStu")
    @ApiOperation(value = "亿策批量校验人员图像信息,更新学生数据", notes = "", response = KqStuAddBean.class)
    public ResponseJson ycBatchEnterStu(
            @ApiParam(value = "", required = true)
            @RequestBody YcStuAddBean ycStuAddBean) {
        //System.out.println(kqStuAddBean);
        if (ycStuAddBean.getStudentList().size() > 100) {
            return new ResponseJson(false, "每次录入人员不可超过100人");
        }
        //中移批量校验录入（超过50人后端分批次录入）
        List<Student> studentList = ycStuAddBean.getStudentList();
        List<List<Student>> batchList = ycVerifacePersonEnterService.getBatchList(studentList, 50);

        //请求人脸服务器校验并获得校验结果数据
        List<YcVerifacePersonEnter> reslist = ycVerifacePersonEnterService.requestYcStuEnter(isProd, batchList);
        if (CollectionUtil.isEmpty(reslist)) {
            return new ResponseJson(false, "请确认录入人员和头像信息");
        }

        //获得检验成功数据,剔除失败数据
        List<Student> enterStus = ycVerifacePersonEnterService.getEnterStuData(reslist, studentList);
        if (enterStus.size() == 0) {
            return new ResponseJson(reslist);
        }

        //批量上传校验成功数据至七牛并要跟新学生数据
        List<Student> qiniuStus = ycVerifacePersonEnterService.uploadImgUrl(enterStus);
        if (qiniuStus.size() == 0) {
            List<YcVerifacePersonEnter> res = ycVerifacePersonEnterService.updateSuccessStu(reslist, qiniuStus);
            return new ResponseJson(res);
        }

        //批量更新校验成功学生头像
        studentService.batchUpdateStudent(qiniuStus);

        //更新校验成功结果集
        List<YcVerifacePersonEnter> res = ycVerifacePersonEnterService.updateSuccessStu(reslist, qiniuStus);

        //返回校验结果数据
        return new ResponseJson(res);
    }

    //校验学生头像
      /**
     * 校验并更新学生
     * @param student
     * @return
     */
    @PostMapping("/checkStuPic")
    @ApiOperation(value = "中移批量校验考勤人员图像信息,更新学生数据", notes = "", response = KqStuAddBean.class)
    public ResponseJson checkStuPic(
            @ApiParam(value = "学生", required = true)
            @RequestBody Student student) {
        if (student.getPager()!=null){
            student.getPager().setPaging(false);
        }
        student.setSchoolId(mySchoolId());
        List<Student> studentList = ycVerifacePersonEnterService.getAllCheckStu(student);
        List<List<Student>> batchList = ycVerifacePersonEnterService.getBatchList(studentList, 50);
        if (CollectionUtil.isEmpty(studentList)){
            return new ResponseJson(false, "请确认录入人员和头像信息");
        }
        //请求人脸服务器校验并获得校验结果数据
        List<YcVerifacePersonEnter> reslist = ycVerifacePersonEnterService.requestYcStuEnter(isProd, batchList);
        if (CollectionUtil.isEmpty(reslist)) {
            return new ResponseJson(reslist);
        }
        //获得检验成功数据,剔除失败数据
        List<Student> enterStus = ycVerifacePersonEnterService.getEnterStuData(reslist, studentList);
        if (CollectionUtil.isEmpty(enterStus)) {
            return new ResponseJson(reslist);
        }
        //更新校验成功结果集
        List<YcVerifacePersonEnter> res = ycVerifacePersonEnterService.updateSuccessStu(reslist, enterStus);
        //返回校验结果数据
        return new ResponseJson(res);
    }



 /*----------------------------*/
 //按条件查找学生
 @PostMapping("/find/findStudentsByCondition")
 @ApiOperation(value = "根据条件查找学生信息,传班级classesId", notes = "返回响应对象")
 public ResponseJson findStudentsByCondition(
         @ApiParam(value = "属性不为空则作为条件查询")
         @RequestBody Student student) {
     student.setSchoolId(mySchoolId());
     if (StrUtil.isNotEmpty(student.getZyCheckStatus())){
         YcDataToRegisterPage ycDataToRegisterPage = ycVerifacePersonEnterService.fingStudentByYcCheckStatus(student);
        return new ResponseJson(ycDataToRegisterPage.getStudentList(),ycDataToRegisterPage.getCount());
     }
     List<Student> data = studentService.findStudentListByCondition(student);
     long count = studentService.findStudentCountByCondition(student);
     //校验状态
     if (CollectionUtil.isNotEmpty(data)){
         data = ycVerifacePersonEnterService.addStuCheckStatus(data);
     }
     return new ResponseJson(data, count);

 }

    //按条件查找学生
    @PostMapping("/find/findStudentsByConditionWithPower")
    @ApiOperation(value = "根据条件查找学生信息,传班级classesId", notes = "返回响应对象")
    public ResponseJson findStudentsByConditionWithPower(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student) {
        List<TeacherPost> teacherPost = teacherService.findTeacherPost(myId());
        teacherPost = teacherPost.stream().filter(teacherPost1 -> teacherPost1.getSort()!=null).collect(Collectors.toList());
        teacherPost = teacherPost.stream().sorted(Comparator.comparing(TeacherPost::getSort)).collect(Collectors.toList());
        if (teacherPost.size()==0){
            return new ResponseJson();
        }
        String ddId = teacherPost.get(0).getDdId();
        final TeacherPost teacherPost1 = teacherPost.get(0);
        if (ddId.equals(Constant.TEACHER_POST.GRADE_LEADER)) {//段长
            if (student.getGradeId() != null && student.getGradeId().equals(teacherPost1.getGradeId())) {
                //返回本年段
                student.setGradeId(teacherPost1.getGradeId());
            } else if (student.getGradeId() == null) {
                //返回本年段
                student.setGradeId(teacherPost1.getGradeId());
            } else if (student.getGradeId() != null && !student.getGradeId().equals(teacherPost1.getGradeId())) {
                //是否是任班主任年段
                List<TeacherPost> classHeader = teacherPost.stream().filter(t -> t.getDdId().equals(Constant.TEACHER_POST.CLASS_TEACHER)).collect(Collectors.toList());
                if (classHeader.size() > 0) {
                    //是班主任，判断指定的年段是否是班主任所在年段
                    TeacherPost teacherPost2 = classHeader.get(0);
                    if (student.getGradeId().equals(teacherPost2.getGradeId())) {
                        student.setGradeId(teacherPost2.getGradeId());
                        student.setClassesId(teacherPost2.getClassId());
                    } else {
                        return new ResponseJson(false, "您在该年段没有查看的权限");
                    }
                }
            }
            student.setSchoolId(mySchoolId());
            List<Student> data = studentService.findStudentListByCondition(student);
            long count = studentService.findStudentCountByCondition(student);
            return new ResponseJson(data, count);
        } else if (ddId.equals(Constant.TEACHER_POST.CLASS_TEACHER)) {//班主任
            //返回本年段
            student.setClassesId(teacherPost1.getClassId());
            student.setSchoolId(mySchoolId());
            List<Student> data = studentService.findStudentListByCondition(student);
            long count = studentService.findStudentCountByCondition(student);
            return new ResponseJson(data, count);
        } else if (ddId.equals(Constant.TEACHER_POST.PRINCIPAL)) {//校长
            //返回所有年段
            student.setSchoolId(mySchoolId());
            List<Student> data = studentService.findStudentListByCondition(student);
            long count = studentService.findStudentCountByCondition(student);
            return new ResponseJson(data, count);
        }

        return new ResponseJson(false, "您没有查看的权限");
    }

    @GetMapping("/find/findGradesCurrentSchool")
    @ApiOperation(value = "查找当前学校包含的年级", notes = "返回响应对象")
    public ResponseJson findGradesCurrentSchool() {
        Dd dd = new Dd();
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        List<Dd> data = ddService.findDdListByCondition(dd);
        List<TeacherPost> teacherPost = teacherService.findTeacherPost(myId());
        teacherPost = teacherPost.stream().filter(teacherPost1 -> teacherPost1.getSort()!=null).collect(Collectors.toList());
        teacherPost = teacherPost.stream().sorted(Comparator.comparing(TeacherPost::getSort)).collect(Collectors.toList());
        if (teacherPost.size()==0){
            return new ResponseJson(false, "您的权限有误,请重新配置您的职务");
        }
        String ddId = teacherPost.get(0).getDdId();
        final TeacherPost teacherPost1 = teacherPost.get(0);
        if (ddId.equals(Constant.TEACHER_POST.GRADE_LEADER)) {//段长
            //返回本年段
            List<Dd> data2 = data.stream().filter(t -> t.getId().equals(teacherPost1.getGradeId())).collect(Collectors.toList());
            //同时是班主任，将班主任的年段返回
            List<TeacherPost> classHearder = teacherPost.stream().filter(t -> t.getDdId().equals(Constant.TEACHER_POST.CLASS_TEACHER)).collect(Collectors.toList());
            if (classHearder.size() > 0 && !classHearder.get(0).getGradeId().equals(teacherPost1.getGradeId())) {
                List<Dd> data3 = data.stream().filter(t -> t.getId().equals(classHearder.get(0).getGradeId())).collect(Collectors.toList());
                data2.addAll(data3);
            }
            return new ResponseJson(data2);
        } else if (ddId.equals(Constant.TEACHER_POST.CLASS_TEACHER)) {//班主任
            //返回本年段
            data = data.stream().filter(t -> t.getId().equals(teacherPost1.getGradeId())).collect(Collectors.toList());
            return new ResponseJson(data);
        } else if (ddId.equals(Constant.TEACHER_POST.PRINCIPAL)) {//校长
            //返回所有年段
            return new ResponseJson(data);
        }
        return new ResponseJson();
    }

    @GetMapping("/find/findClassesByGradeId/{gradeId}")
    public ResponseJson findClassesByGradeId(@PathVariable String gradeId) {
        List<TeacherPost> teacherPost = teacherService.findTeacherPost(myId());
        List<TeacherPost> classHearder = teacherPost.stream().filter(t -> t.getDdId().equals(Constant.TEACHER_POST.CLASS_TEACHER)).collect(Collectors.toList());
        try{
            teacherPost = teacherPost.stream().sorted(Comparator.comparing(TeacherPost::getSort)).collect(Collectors.toList());
        }catch (Exception e){
            return new ResponseJson(false, "您的权限有误,请重新配置您的职务");
        }
        if (teacherPost.size()==0){
            return new ResponseJson(false, "您的权限有误,请重新配置您的职务");
        }
        String ddId = teacherPost.get(0).getDdId();
        final TeacherPost teacherPost1 = teacherPost.get(0);
        JwClasses c = new JwClasses();
        c.setGradeId(gradeId);
        c.setSchoolId(mySchoolId());
        if (!ddId.equals(Constant.TEACHER_POST.PRINCIPAL) && classHearder.size() > 0) {
            final TeacherPost teacherPost2 = classHearder.get(0);
            if (ddId.equals(Constant.TEACHER_POST.GRADE_LEADER) && teacherPost1.getGradeId().equals(teacherPost2.getGradeId()) && teacherPost2.getGradeId().equals(gradeId)) {
                return new ResponseJson(jwClassesService.findJwClassesListByCondition(c));
            }
            if (teacherPost2.getGradeId().equals(gradeId)) {//班主任
                //返回本年段
                JwClasses jwClassesById = jwClassesService.findJwClassesById(teacherPost2.getClassId());
                List<JwClasses> jwClasses = new ArrayList<>();
                jwClasses.add(jwClassesById);
                return new ResponseJson(jwClasses);
            }
        }
        return new ResponseJson(jwClassesService.findJwClassesListByCondition(c));
    }



//---本公司end人脸识别项目------------

    /************************************************************************************************************************/
    @PostMapping("/checkPersonExistWithoutUserId")
    @ApiOperation(value = "亿策批量校验人员图像信息,更新学生数据", notes = "", response = YcEnterBean.class)
    public ResponseJson checkPersonExistWithoutUserId(
            @ApiParam(value = "", required = true)
            @RequestBody YcEnterBean ycEnterBean) {
        YcVerifaceOtherCheckBean aBoolean = ycVerifaceOtherCheckService.checkPersonExistWithoutUserId(ycEnterBean);
        return new ResponseJson(aBoolean);
    }

    @PostMapping("/checkPersonExistByUserId")
    @ApiOperation(value = "亿策批量校验人员图像信息,更新学生数据", notes = "", response = YcEnterBean.class)
    public ResponseJson checkPersonExistByUserId(
            @ApiParam(value = "", required = true)
            @RequestBody YcEnterBean ycEnterBean) {
        YcVerifaceOtherCheckBean aBoolean = ycVerifaceOtherCheckService.checkPersonExistByUserId(ycEnterBean);
        return new ResponseJson(aBoolean);
    }
}
