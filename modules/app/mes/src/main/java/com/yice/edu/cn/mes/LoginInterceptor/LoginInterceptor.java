package com.yice.edu.cn.mes.LoginInterceptor;

import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<Teacher> tl = new ThreadLocal<>();
    public static ThreadLocal<Student> t2 = new ThreadLocal<>();
    @CreateCache(name = Constant.Redis.MES_TEACHER_CACHE)
    private Cache<String, Teacher> teacherCache;
    @CreateCache(name = Constant.Redis.MES_STUDENT_CACHE)
    private Cache<String, Student> studentCache;

    public static Teacher currentTeacher() {
        return tl.get();
    }

    public static String mySchoolId() {
        return currentStudent().getSchoolId();
    }

    public static String myId() {
        return currentTeacher().getId();
    }


    public static Student currentStudent() {
        return t2.get();
    }

    public static String myStudentId() {
        return currentStudent().getId();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String teacherId = request.getHeader(Constant.Redis.MES_TEACHER_ID_HEADER);
        String studentId = request.getHeader(Constant.Redis.MES_STUDENT_ID_HEADER);

        if (teacherId == null && studentId == null) {
            return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN, "登录信息失效"), response);
        } else {
            final Teacher exist = teacherCache.get(teacherId);
            final Student exist1 = studentCache.get(studentId);

            if (exist == null && exist1 == null) {
                return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN, "登录信息失效"), response);
            } else if (exist != null) {
                tl.set(exist);
                return true;
            } else {
                t2.set(exist1);
                return true;
            }
        }
    }

    private boolean writeResponse(ResponseJson responseJson, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=1");
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(responseJson));
        return false;
    }
}
