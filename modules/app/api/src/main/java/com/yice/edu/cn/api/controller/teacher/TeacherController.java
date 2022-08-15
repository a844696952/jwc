package com.yice.edu.cn.api.controller.teacher;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.api.service.teacher.TeacherClassesService;
import com.yice.edu.cn.api.service.teacher.TeacherService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.api.anoah.TeacherModel;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/teacher")
@Api(value = "/teacher", description = "教师信息模块")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherClassesService teacherClassesService;


    @PostMapping("/findTeachersBySchoolId")
    @ApiOperation(value = "根据学校id查找所有老师", notes = "返回响应对象")
    public ResponseJson findTeachersByCondition(
            @ApiParam(value = "学校id") @NotNull String schoolId) {
        Teacher teacher = new Teacher();
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setSchoolId(schoolId);
        teacher.setPager(new Pager());
        teacher.getPager().setPaging(false);
        teacher.getPager().addExcludes("password");
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> data = teacherService.findTeacherListByCondition4Like(teacher);
        return new ResponseJson(data, 0);
    }

    @PostMapping("/findTeacherClassesBySchoolId")
    @ApiOperation(value = "根据条件查询教师在班级任职教学信息")
    public ResponseJson findTeacherClassPostCourseListBySchoolId(
            @ApiParam(value = "学校id")
            @RequestParam String schoolId) {
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setPager(new Pager());
        teacherClasses.getPager().setPaging(false);
        teacherClasses.setSchoolId(schoolId);
        List<Map<String, Object>> t = teacherClassesService.findTeacherClassPostCourseListBySchoolId(teacherClasses);
        return new ResponseJson(t);
    }

    @PostMapping("/findTeacherByToken")
    @ApiOperation(value = "根据票据token查询用户的信息")
    public ResponseJson findTeacherByToken(
            @ApiParam(value = "票据token")
            @RequestParam String token) {

        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            return new ResponseJson(false, Constant.HAVEN_LOGIN,"解析token失败");
        }
        if(Objects.nonNull(claims)) {
            String subject = claims.getSubject();
            if (subject == null || subject.length() < 5) {
                return new ResponseJson(false, Constant.HAVEN_LOGIN, "解析token subject失败");
            }
            Teacher teacher = JSONUtil.toBean(subject, Teacher.class);

            TeacherModel teacherModel = new TeacherModel();
            teacherModel.setId(teacher.getId());
            teacherModel.setName(teacher.getName());
            teacherModel.setTel(teacher.getTel());

            return new ResponseJson(teacherModel);

        }

        return new ResponseJson(false, "该token无效");

    }


}
