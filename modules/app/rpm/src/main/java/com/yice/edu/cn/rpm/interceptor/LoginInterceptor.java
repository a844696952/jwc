package com.yice.edu.cn.rpm.interceptor;

import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.rpm.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<Teacher> tl=new ThreadLocal<>();


    public static Teacher currentTeacher(){
        return tl.get();
    }
    public static String mySchoolId(){
        return currentTeacher().getSchoolId();
    }
    public static String myId(){
        return currentTeacher().getId();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String subject = request.getHeader(Constant.TEACHER);
        if(subject==null||subject.length()<5){
            return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
        }else{
            final Teacher exist = JSONUtil.toBean(subject, Teacher.class);
            tl.set(exist);
            return true;

        }
    }

    private boolean writeResponse(ResponseJson responseJson,HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=1");
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(responseJson));
        return false;
    }


}
