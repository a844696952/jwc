package com.yice.edu.cn.mes.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.LoginErrorInfo;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesUserAuthInstitution;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.mes.service.MesUserAuthInstitutionService;
import com.yice.edu.cn.mes.service.SchoolService;
import com.yice.edu.cn.mes.service.StudentService;
import com.yice.edu.cn.mes.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
@Api(value = "/login", description = "登录")
public class LoginController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MesUserAuthInstitutionService mesUserAuthInstitutionService;
    @Autowired
    private SchoolService schoolService;

    @CreateCache(name = Constant.Redis.MES_STUDENT_LOGIN_ERROR, expire = 1, timeUnit = TimeUnit.DAYS)
    private Cache<String, String> loginStudentErrorInfoCache;

    @PostMapping("/studentLogin")
    @ApiOperation(value = "学生平板端登录：传入学生学号(studentNo)，密码(password)，schoolId", notes = "返回学生信息")
    public ResponseJson studentLogin(
            @RequestBody Student student
    ) {
        //加入升学判断业务
        ResponseJson responseJson = schoolService.findSchoolExpireOrSchoolYear(student.getSchoolId());
        if (!responseJson.getResult().isSuccess()) {
            return responseJson;
        }
        String password=student.getPassword();
        String key = student.getStudentNo() + Constant.Redis.MES_STUDENT_LOGIN_ERROR;
        String value = loginStudentErrorInfoCache != null ? loginStudentErrorInfoCache.get(key) : null;
        student.setPassword("");
        List<Student> students = studentService.findStudentLogin(student);
        if(CollUtil.isEmpty(students)){
            return new ResponseJson(false, "学号不存在!");
        }
        if(CollUtil.isNotEmpty(students) && !Objects.equals(DigestUtil.sha1Hex(password.toLowerCase()),students.get(0).getPassword())){
            return new ResponseJson(false, "账号密码错误!");
        }
        LoginErrorInfo errorInfo = value != null ? JSONUtil.toBean(value, LoginErrorInfo.class) : null;
        if (errorInfo == null) {
            errorInfo = new LoginErrorInfo();
            errorInfo.setOk(true);
            errorInfo.setNum(0);
            String errorInfoStr = JSONUtil.toJsonStr(errorInfo);
            studentService.saveLoginErrorInfoToRedis(errorInfoStr, key);
            if (CollectionUtil.isNotEmpty(students)) {
                if (!haveCheckPermission(student.getSchoolId(), students.get(0).getId())) {
                    return new ResponseJson(false, "该账号无权限登录");
                }
                students.get(0).setPassword(null);
                studentService.saveStudentToRedis(students.get(0));
                String token = JwtUtil.createJWT(students.get(0).getId(), "{\"name\":\"student\"}", null, -1);
                return new ResponseJson(token, studentService.getStudentLoginInfo(students.get(0).getId()));
            }
        } else {
            if (errorInfo.getWrongTime() != null) {
                long diff = DateUtil.date().getTime() - errorInfo.getWrongTime().getTime();
                boolean flag = diff > Constant.Redis.EWB_STUDENT_LOGIN_ERROR_TIME * 60 * 1000;
                if (flag && CollectionUtil.isNotEmpty(students)) {
                    if (!haveCheckPermission(student.getSchoolId(), students.get(0).getId())) {
                        return new ResponseJson(false, "该账号无权限登录");
                    }
                    studentService.saveStudentToRedis(students.get(0));
                    String token = JwtUtil.createJWT(students.get(0).getId(), "{\"name\":\"student\"}", null, -1);
                    teacherService.clearLoginErrorInfoCache(key);
                    return new ResponseJson(token, studentService.getStudentLoginInfo(students.get(0).getId()));
                }
                if (flag && CollectionUtil.isEmpty(students)) {
                    errorInfo.setNum(1);
                    errorInfo.setWrongTime(null);
                    errorInfo.setOk(true);
                    String errorInfoStr = JSONUtil.toJsonStr(errorInfo);
                    studentService.updateLoginErrorInfoToRedis(errorInfoStr, key);
                }
            }
            if (errorInfo.isOk() && CollectionUtil.isNotEmpty(students)) {
                if (!haveCheckPermission(student.getSchoolId(), students.get(0).getId())) {
                    return new ResponseJson(false, "该账号无权限登录");
                }
                studentService.saveStudentToRedis(students.get(0));
                String token = JwtUtil.createJWT(students.get(0).getId(), "{\"name\":\"student\"}", null, -1);
                studentService.clearLoginErrorInfoCache(key);
                return new ResponseJson(token, studentService.getStudentLoginInfo(students.get(0).getId()));
            }

            int num = errorInfo.getNum();
            if (errorInfo.isOk() && num < 5) {
                errorInfo.setNum(num + 1);
                if (num == 4) {
                    errorInfo.setWrongTime(DateUtil.date());
                    errorInfo.setOk(false);
                }
                String errorInfoStr = JSONUtil.toJsonStr(errorInfo);
                studentService.updateLoginErrorInfoToRedis(errorInfoStr, key);
            }
            if (errorInfo.getNum() > 4) {
                return new ResponseJson(false, "密码错误超过5次" + Constant.Redis.MES_STUDENT_LOGIN_ERROR_TIME + "分钟后重试");
            }
        }
        return new ResponseJson(false, "账号密码错误");
    }

    private boolean haveCheckPermission(String schoolId, String studentId) {
        MesUserAuthInstitution mesUserAuthInstitution = new MesUserAuthInstitution();
        mesUserAuthInstitution.setUserType(1);
        mesUserAuthInstitution.setUserId(studentId);
        mesUserAuthInstitution.setSchoolId(schoolId);
        return mesUserAuthInstitutionService.haveCheckPermission(mesUserAuthInstitution);
    }

}
