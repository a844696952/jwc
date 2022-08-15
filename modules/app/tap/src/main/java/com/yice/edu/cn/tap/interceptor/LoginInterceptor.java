package com.yice.edu.cn.tap.interceptor;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<Teacher> tl=new ThreadLocal<>();

    @CreateCache(name=Constant.Redis.TAP_TEACHER_CACHE)
    private Cache<String,Teacher> teacherCache;

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
        String teacherId = request.getHeader(Constant.Redis.TAP_TEACHER_ID_HEADER);
        if(teacherId==null){
           return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
        }else{
            final Teacher exist = teacherCache.get(teacherId);
            if(exist==null){
                return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
            }
            teacherCache.put(teacherId,exist);
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
