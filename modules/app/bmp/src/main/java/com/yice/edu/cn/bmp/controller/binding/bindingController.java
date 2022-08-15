package com.yice.edu.cn.bmp.controller.binding;

import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.bmp.interceptor.LoginInterceptor;
import com.yice.edu.cn.bmp.service.appPerm.AppPermService;
import com.yice.edu.cn.bmp.service.parent.ParentService;
import com.yice.edu.cn.bmp.service.perm.PermService;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentNameRelationship;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.validateClass.GroupFive;
import com.yice.edu.cn.common.pojo.validateClass.GroupTwo;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.*;


@RestController
@RequestMapping("/binding")
public class bindingController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private PermService permService;

    @Autowired
    private AppPermService appPermService;

    @CreateCache(name = Constant.Redis.BMP_VERIFICATION_CODE, expire = 180)
    private Cache<String, String> codeCache;

    //查询监护人
    @GetMapping("/checkParent/{tel}")
    @ApiOperation(value = "查询监护人", notes = "返回结果")
    public ResponseJson checkParent(@ApiParam(value = "手机号") @Length(min = 11, max = 11, message = "手机号码长度必须为11位") @PathVariable String tel) {
        Student student = new Student();
        student.setGuardianContact(tel);
        List<Student> list = studentService.findStudentListByCondition(student);
        if (list.size() == 0) {
            return new ResponseJson(false, "该监护人不存在");
        } else {
            String code = parentService.getVerificationCode(tel);
            if (!code.equals("")) {
                return new ResponseJson(true, "验证码发送成功");
            } else {
                return new ResponseJson(true, "验证码发送失败");
            }
        }

    }

    //绑定孩子
    @PostMapping("/bindingChild")
    @ApiOperation(value = "绑定孩子，传入手机号/验证码/姓名", notes = "返回结果")
    public ResponseJson bingdingChild(@ApiParam(value = "例如__:{guardianContact:'*****',code:'*****',name:'*****'}") @RequestBody @Validated(GroupFive.class) Student student) {
        String parentId = myParentId();
        boolean verifyFlag = parentService.compareIdentifyingCode(student.getGuardianContact(), student.getCode());
        if (!verifyFlag) {
            return new ResponseJson(false, "验证码错误");
        }
        Student student1 = new Student();
        student1.setName(student.getName());
        student1.setGuardianContact(student.getGuardianContact());
        List<Student> studentList = studentService.findStudentListByCondition(student1);
        if (studentList.size() == 0) {
            return new ResponseJson(false, "您输入的信息错误或学生不存在");
        }

        ParentStudent ps = new ParentStudent();
        ps.setStudentId(studentList.get(0).getId());
        List<ParentStudent> parentStudentList = parentService.findParentStudentListByCondition(ps);
        if (parentStudentList.size() != 0) {
            return new ResponseJson(false, "该学生已被其他用户绑定");
        } else {
            Parent parentByStudentid= parentService.findParentById(parentId);
            if(parentByStudentid.getStudentId()==null||parentByStudentid.getStudentId().equals("")){
                Parent parent = new Parent();
                parent.setId(parentId);
                parent.setStudentId(studentList.get(0).getId());
                parent.setSchoolId(studentList.get(0).getSchoolId());
                parentService.updateParent(parent);
            }
            ParentStudent parentStudent = new ParentStudent();
            parentStudent.setParentId(parentId);
            parentStudent.setStudentId(studentList.get(0).getId());
            parentStudent.setSchoolId(studentList.get(0).getSchoolId());
            parentService.saveParentStudent(parentStudent);

            final Parent parentById = parentService.findParentById(parentId);
            parentService.saveParenToCache(parentById);
            Student currentStudent=new Student();
            currentStudent.setId(studentList.get(0).getId());
            currentStudent.setSchoolId(studentList.get(0).getSchoolId());
            currentStudent.setClassesId(studentList.get(0).getClassesId());
            currentStudent.setEnrollYear(studentList.get(0).getEnrollYear());
            codeCache.remove(student.getGuardianContact());
            return new ResponseJson(currentStudent);
        }

    }


    //绑定孩子后，亲属关系和家长姓名更新
    @PostMapping("/bindingRelationship")
    @ApiOperation(value = "绑定后亲属关系和家长姓名更新", notes = "返回更新结果")
    public ResponseJson bindingRelationship(@ApiParam(value = "parentName:'*****',relationship:'*****',studentId:'*****' ") @RequestBody ParentNameRelationship parentNameRelationship) {
        //1.获取登录的家长
        Parent parent = currentParent();
        //2.填充家长学生关系对象
        ParentStudent ps = new ParentStudent();
        ps.setParentId(parent.getId());
        ps.setRelationship(parentNameRelationship.getRelationship());
        parent.setName(parentNameRelationship.getParentName());
        //3.查当前家长展示学生的id
        String studentId = parentNameRelationship.getStudentId();
        Student s = studentService.findStudentById(studentId);
        if(s==null){
            return new ResponseJson(false,"该学生不存在");
        }
        if (studentId != null && !studentId.equals("") && s!=null) {
            ps.setStudentId(studentId);
        } else {
            return new ResponseJson(false, "家长与孩子关系未绑定");
        }
        parentNameRelationship.setParentStudent(ps);
        parentNameRelationship.setParent(parent);
        parentService.updateRelationshipAndParentName(parentNameRelationship);
        return new ResponseJson(true, "设置家长孩子亲属关系和家长姓名成功");
    }



    //个人中心亲属关系更新
    @PostMapping("/bindingRelationshipInCenter")
    @ApiOperation(value = "个人中心亲属关系更新", notes = "返回更新结果")
    public ResponseJson bindingRelationship(@ApiParam(value = "家长学生对象，需要学生studentId和relationship")
                                            @RequestBody @Validated(GroupTwo.class) ParentStudent parentStudent) {

        //获得登录后的家长id

        parentStudent.setParentId(myParentId());
        //更新家长学生亲属关系
        parentService.updateParentStudent(parentStudent);
        return new ResponseJson(true, "设置家长孩子亲属关系成功");
    }

    //切换当前显示的学生
    @PostMapping("/changeCurrentBandingStudent")
    @ApiOperation(value = "切换当前绑定的孩子", notes = "返回切换结果")
    public ResponseJson changeCurrentBandingStudent(
            @ApiParam(value = "学生对象，预绑定孩子id,传appPermType")
            @RequestBody Student student) {
        //1.获取孩子id
        String studentId = student.getId();
        Parent parent = new Parent();
        parent.setId(currentParent().getId());
        //3.更新家长表
        parent.setStudentId(studentId);
        Student currentStudent = studentService.findStudentById(studentId);

        ResponseJson responseJson =appPermService.findSchoolExpireOrSchoolYear(currentStudent.getSchoolId());
        if (!responseJson.getResult().isSuccess()){
            return responseJson;
        }

        if(currentStudent==null){
            ParentStudent parentStudent = new ParentStudent();
            parentStudent.setStudentId(studentId);
            parentService.deleteParentStudentByCondition(parentStudent);
            return new ResponseJson(true,"该学生不存在");
        }
        parent.setSchoolId(currentStudent.getSchoolId());
        parentService.updateParent(parent);
        Parent parent1 = parentService.findParentById(parent.getId());
        parent1.setName(null);
        ParentStudent ps = new ParentStudent();
        ps.setParentId(currentParent().getId());
        List<ParentStudentFile> parStuList = parentService.getBoundStudentListInCenter(ps);
        for (ParentStudentFile psf :parStuList){
            if (currentStudent.getId().equals(psf.getId())) {
                currentStudent.setRelationship(psf.getRelationship());
                currentStudent.setParentName(psf.getParentName());
                currentStudent.setSchoolName(psf.getSchoolName());
            }
        }
        //Student currentStudent = studentService.findStudentById(parent1.getStudentId());
        if (parent1 != null && parent1.getStudentId().equals(studentId)) {
            //缓存新的parent对象 @Cacheput 是每次先删除指定对象再缓存新的对象
            parentService.saveParenToCache(parent1);
            AppPerm appPerm=new AppPerm();
            appPerm.setWhatApp(student.getAppPermType());
            appPerm.setSchoolId(parent1.getSchoolId());
            List<AppPerm> appPermList = appPermService.findAppPermListTreeByClass(appPerm);
            return new ResponseJson(currentStudent,appPermList);
        } else {
            return new ResponseJson(false, "切换失败");
        }
    }

    //解绑孩子
    @PostMapping("/UntieChild")
    @ApiOperation(value = "个人中心解绑孩子，传入学生id", notes = "返回更新结果")
    public ResponseJson UntieChild(@ApiParam(value = "学生id") @RequestBody Student student1) {
        String parentId = myParentId();
        ParentStudent parentStudent = new ParentStudent();
        parentStudent.setStudentId(student1.getId());
        parentService.deleteParentStudentByCondition(parentStudent);//根据学生id删除关系表中的学生
        ParentStudent ps = new ParentStudent();
        ps.setParentId(myParentId());

        parentService.deleteParentStudentByParentId(ps);
        List<ParentStudent> list = parentService.findSchoolByParentId(ps);//根据学生id查询Parent表中是否存在绑定学生
        Parent parent = new Parent();
        boolean flag=false;
        if (list.size() != 0) {
            for (ParentStudent s : list) {
                //如果该默认孩子学校过期，查询该家长剩余的孩子列表（如果其余孩子有学校未过期的则将默认孩子切换至该孩子）
                ResponseJson  responseJson1 = appPermService.findSchoolExpireOrSchoolYear(s.getSchoolId());
                if (responseJson1.getResult().isSuccess()) {
                    parent.setId(s.getParentId());
                    parent.setStudentId(s.getStudentId());
                    parent.setSchoolId(s.getSchoolId());
                    parentService.updateParent1(parent);
                    final Parent parentById = parentService.findParentById(parentId);
                    parentService.saveParenToCache(parentById);

                    flag=false;
                    break;
                }else {
                    flag=true;
                }
            }
            if(flag){
                parent.setStudentId(null);
                parent.setSchoolId(null);
                parent.setId(parentId);
                parentService.updateParent1(parent);
                final Parent parentById = parentService.findParentById(parentId);
                parentService.saveParenToCache(parentById);
                return new ResponseJson(false,923,"该账号绑定的孩子不可用,请联系学校!");
            }
            return new ResponseJson();
        }else{
            parent.setStudentId(null);
            parent.setSchoolId(null);
            parent.setId(parentId);
            parentService.updateParent1(parent);
            final Parent parentById = parentService.findParentById(parentId);
            parentService.saveParenToCache(parentById);
            return new ResponseJson();
        }
    }


    /*//获得已绑定的孩子列表
    @PostMapping("/getBoundStudentList")
    @ApiOperation(value = "获得所有已经绑定的孩子", notes = "返回孩子列表")
    public ResponseJson getBoundStudentList() {
        //1.获取家长
        String parentId = myParentId();
        ParentStudent ps = new ParentStudent();
        ps.setParentId(parentId);
        List<Student> parStuList = parentService.getBoundStudentList(ps);
        Student currentStudent = null;
        for (Student s : parStuList) {
            if (s.getId().equals(myStudentId())) {
                currentStudent = s;
            }
        }
        *//*System.out.println(parStuList);
        System.out.println("lock");*//*

        return new ResponseJson(parStuList, currentStudent);
//        if (parStuList.size() != 0) {
//            return new ResponseJson(parStuList, currentStudent);
//        } else {
//            return new ResponseJson(true, "");
//        }

    }*/


    //获得已绑定的孩子列表给个人中心
    @PostMapping("/getBoundStudentListInCenter")
    @ApiOperation(value = "获得已绑定的孩子列表和当前默认绑定孩子给个人中心", notes = "返回孩子列表")
    public ResponseJson getBoundStudentListInCenter(@ApiParam(value = "学生id,访问来源类型appPermType") @RequestBody Parent parent) {
        //1.获取家长
        String parentId = myParentId();
        ParentStudent ps = new ParentStudent();
        ps.setParentId(parentId);
        List<ParentStudentFile> parStuList = new ArrayList<>();
         parStuList = parentService.getBoundStudentListInCenter(ps);
        if (parStuList.size()==0 ){
            return new ResponseJson(parStuList);
        }
        Student currentStudent=new Student();
        if (myStudentId()!=null && !myStudentId().equals("")) {
            currentStudent = studentService.findStudentById(myStudentId());
            if (currentStudent == null) {
                return new ResponseJson(true,201, "该学生不存在");
            }
            for (ParentStudentFile psf : parStuList) {
                if (currentStudent.getId().equals(psf.getId())) {
                    currentStudent.setRelationship(psf.getRelationship());
                    currentStudent.setParentName(psf.getParentName());
                    currentStudent.setSchoolName(psf.getSchoolName());
                }
            }
            //排序
            int index = 0;
            for (ParentStudentFile psf : parStuList) {
                if (currentStudent.getId().equals(psf.getId())) {
                   break;
                }
                index++;
            }
            Collections.swap(parStuList,index,0);
            AppPerm appPerm = new AppPerm();
            appPerm.setWhatApp(parent.getAppPermType());
            appPerm.setSchoolId(currentStudent.getSchoolId());
            List<AppPerm> appPermList = appPermService.findAppPermListTreeByClass(appPerm);
            return new ResponseJson(parStuList, currentStudent, appPermList);
        } else {
                return new ResponseJson(currentStudent,parStuList);
        }

    }


    //家长改名
    @GetMapping("/changeParentName/{name}")
    @ApiOperation(value = "个人中心更改家长名字", notes = "返回更改后的名称")
    public ResponseJson changeParentName(@PathVariable String name) {
        //1.获取家长
        Parent parent = LoginInterceptor.currentParent();
        //2.更新家长名字
        parent.setName(name);
        parentService.updateParent(parent);
        Parent parent1 = parentService.findParentById(parent.getId());

        if (parent1 != null && parent1.getName().equals(name)) {
            parent1.setPassword(null);
            if(parent1.getStudentId()!=null) {
                Student student = studentService.findStudentById(parent1.getStudentId());
                if(student==null){
                    return new ResponseJson(false,"该学生不存在");
                }
                parent1.setRegisterStatus(student.getRegisterStatus());
            }
            parentService.saveParenToCache(parent1);
            return new ResponseJson(parent1);
        } else {
            return new ResponseJson(false, "改名失败");
        }
    }
}
