package com.yice.edu.cn.osp.controller.xw.kq;


import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.xw.kq.*;
import com.yice.edu.cn.common.util.zyDetector.ZyDetector;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.student.StudentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolInitService;
import com.yice.edu.cn.osp.service.xw.kq.KqSchoolZyDevicesService;
import com.yice.edu.cn.osp.service.xw.kq.KqStuEnterService;
import com.yice.edu.cn.osp.service.xw.kq.KqZyPhotoCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqStuEnter")
@Api(value = "/kqStuEnter", description = "考勤管理人像录入")
public class KqStuEnterController {
    @Autowired
    private KqStuEnterService kqStuAddService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private KqSchoolZyDevicesService kqSchoolZyDevicesService;
    @Autowired
    private DdService ddService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JwClassesService jwClassesService;
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;

    //批量校验
    @PostMapping("/save/batchEnterStu")
    @ApiOperation(value = "中移批量校验考勤人员图像信息,绑定门禁设备,更新学生数据", notes = "", response = KqStuAddBean.class)
    public ResponseJson saveKqBasicRules(
            @ApiParam(value = "考勤管理基础规则表对象,包含学生列表", required = true)
            @RequestBody KqStuAddBean kqStuAddBean) {
        //System.out.println(kqStuAddBean);
        if (kqStuAddBean.getStudentList().size() > 100) {
            return new ResponseJson(false, "每次录入人员不可超过100人");
        }
        //获得本校密钥
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return new ResponseJson(false,"本校暂未开通本服务，请联系管理员");
        }
        String myKey = kqSchool.getKey();

        //中移批量校验录入（超过50人后端分批次录入）
        List<Student> studentList = kqStuAddBean.getStudentList();
        List<List<Student>> batchList = kqStuAddService.getBatchList(studentList, 50);

        //请求中移校验并获得校验结果数据
        List reslist = kqStuAddService.requestZyStuEnter(isProd, myKey, batchList, kqSchool);
        if (reslist.size() == 0) {
            return new ResponseJson(false, "服务器繁忙");
        }

        //获得检验成功数据,剔除失败数据
        List<Student> enterStus = kqStuAddService.getEnterStuData(reslist, studentList);
        if (enterStus.size() == 0) {
            return new ResponseJson(reslist);
        }
        //-------新版本去除这步骤操作---------------
        //获得所有门禁设备
        List<ZyDeviceBean> zyDeviceBeans = kqSchoolZyDevicesService.findSchoolDevices(isProd, kqSchool, ZyDetector.GATE_MACHINE);
        //门禁设备绑定校验通过人员
        if (zyDeviceBeans.size() > 0) {
            Boolean boundSuccess = kqStuAddService.boundDevices(isProd, enterStus, kqSchool, zyDeviceBeans);
            if (!boundSuccess) {
                List<ZyStuBean> res = kqStuAddService.updateSuccessStu(reslist, new ArrayList<Student>());
                return new ResponseJson(res);
            }
        }
        //-------------------------
        //批量上传校验成功数据至七牛并要跟新学生数据
        List<Student> qiniuStus = kqStuAddService.uploadImgUrl(enterStus);
        if (qiniuStus.size() == 0) {
            List<ZyStuBean> res = kqStuAddService.updateSuccessStu(reslist, qiniuStus);
            return new ResponseJson(res);
        }

        //批量更新校验成功学生头像
        studentService.batchUpdateStudent(qiniuStus);

        //更新校验成功结果集
        List<ZyStuBean> res = kqStuAddService.updateSuccessStu(reslist, qiniuStus);

        //返回校验结果数据
        return new ResponseJson(res);
    }
    //按条件查找学生
    @PostMapping("/find/findStudentsByCondition")
    @ApiOperation(value = "根据条件查找学生信息,传班级classesId", notes = "返回响应对象")
    public ResponseJson findStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student) {
            student.setSchoolId(mySchoolId());
           // student.setStatus("50");
            List<Student> data = studentService.findStudentListByCondition(student);
            long count = studentService.findStudentCountByCondition(student);
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

    //按更新学生在校状态
    @PostMapping("/update/updateStudentNowStatus")
    @ApiOperation(value = "根据条件查找学生信息,传班级classesId", notes = "返回响应对象")
    public ResponseJson updateStudentNowStatus(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Student student) {
        //是否是该班班主任
        Student student1 = new Student();
        student1.setId(student.getId());
        student1.setNowStatus(student.getNowStatus());
        boolean isHeadmaster = kqStuAddService.checkIsHeadmaster(student);
        if (!isHeadmaster) {
            return new ResponseJson(false, "您不是本班班主任，不能修改学生状态");
        }
        studentService.updateStudent(student1);
        //发推送给家长
        kqStuAddService.sendStuNowStatusToParent(student);
        return new ResponseJson();
    }

    @PostMapping("/find/zyPhotoCheck")
    @ApiOperation(value = "根据条件查找学生信息,传班级classesId", notes = "返回响应对象")
    public ResponseJson zyPhotoCheck(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqStuAddBean kqStuAddBean) {

        //获得本校密钥
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return new ResponseJson("失败");
        }
        KqZyPhotoCheckService kqZyPhotoCheckService = new KqZyPhotoCheckService();
        DataReceiveResBean res = kqZyPhotoCheckService.zyPhotoCheck(isProd, kqSchool, kqStuAddBean.getStudentList().get(0).getPrsnAvtrUrlAddr());
        //System.out.println(res.getBean().get("state").equals("1"));
        return new ResponseJson(res.getBean().get("state").equals("1")?"成功":"失败");
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
    //批量校验
    @PostMapping("/save/batchEnterStuWithoutBindDevice")
    @ApiOperation(value = "中移批量校验考勤人员图像信息,绑定门禁设备,更新学生数据", notes = "", response = KqStuAddBean.class)
    public ResponseJson batchEnterStuWithoutBindDevice(
            @ApiParam(value = "考勤管理基础规则表对象,包含学生列表", required = true)
            @RequestBody KqStuAddBean kqStuAddBean) {
        if (kqStuAddBean.getStudentList().size() > 100) {
            return new ResponseJson(false, "每次录入人员不可超过100人");
        }
        //获得本校密钥
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return new ResponseJson(false,"本校暂未开通本服务，请联系管理员");
        }
        String myKey = kqSchool.getKey();
        //中移批量校验录入（超过50人后端分批次录入）
        List<Student> studentList = kqStuAddBean.getStudentList();
        List<List<Student>> batchList = kqStuAddService.getBatchList(studentList, 50);
        //请求中移校验并获得校验结果数据
        List reslist = kqStuAddService.requestZyStuEnter(isProd, myKey, batchList, kqSchool);
        if (reslist.size() == 0) {
            return new ResponseJson(false, "服务器繁忙");
        }
        //获得检验成功数据,剔除失败数据
        List<Student> enterStus = kqStuAddService.getEnterStuData(reslist, studentList);
        if (enterStus.size() == 0) {
            return new ResponseJson(reslist);
        }
        //批量上传校验成功数据至七牛并要跟新学生数据
        List<Student> qiniuStus = kqStuAddService.uploadImgUrl(enterStus);
        if (qiniuStus.size() == 0) {
            List<ZyStuBean> res = kqStuAddService.updateSuccessStu(reslist, qiniuStus);
            return new ResponseJson(res);
        }
        //批量更新校验成功学生头像
        studentService.batchUpdateStudent(qiniuStus);
        //更新校验成功结果集
        List<ZyStuBean> res = kqStuAddService.updateSuccessStu(reslist, qiniuStus);
        //返回校验结果数据
        return new ResponseJson(res);
    }

    /**
     * 校验并更新学生
     * @param student
     * @return
     */
    @PostMapping("/save/checkOneStuPic")
    @ApiOperation(value = "中移批量校验考勤人员图像信息,更新学生数据", notes = "", response = KqStuAddBean.class)
    public ResponseJson checkOneStuPic(
            @ApiParam(value = "学生", required = true)
            @RequestBody Student student) {
        if (student.getPager()!=null){
            student.getPager().setPaging(false);
        }
        student.setSchoolId(mySchoolId());
        //获得本校密钥
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        kqSchoolInit.setCustId(mySchoolId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        if (kqSchool==null){
            return new ResponseJson(false,"本校暂未开通本服务，请联系管理员");
        }
        String myKey = kqSchool.getKey();
        List<Student> studentList = kqStuAddService.getAllCheckStu(student);
        List<List<Student>> batchList = kqStuAddService.getBatchList(studentList, 50);
        //请求中移校验并获得校验结果数据
        List reslist = kqStuAddService.requestZyStuEnterforCheck(isProd, myKey, batchList, kqSchool);
        if (reslist.size() == 0) {
            return new ResponseJson(false, "服务器繁忙");
        }
        //获得检验成功数据,剔除失败数据
        List<Student> enterStus = kqStuAddService.getEnterStuData(reslist, studentList);
        if (enterStus.size() == 0) {
            return new ResponseJson(reslist);
        }
        //批量上传校验成功数据至七牛并要跟新学生数据
        List<Student> qiniuStus = kqStuAddService.uploadImgUrlForCheck(enterStus);
        if (qiniuStus.size() == 0) {
            List<ZyStuBean> res = kqStuAddService.updateSuccessStu(reslist, qiniuStus);
            return new ResponseJson(res);
        }
        //批量更新校验成功学生头像
        studentService.batchUpdateStudent(qiniuStus);
        //更新校验成功结果集
        List<ZyStuBean> res = kqStuAddService.updateSuccessStu(reslist, qiniuStus);
        //返回校验结果数据
        return new ResponseJson(res);
    }


//---本公司人脸识别项目begin----------
    //上传学生头像
    //上传教职工头像
    //校验学生头像
    //校验教职工头像
//---本公司人脸识别项目end------------

}
