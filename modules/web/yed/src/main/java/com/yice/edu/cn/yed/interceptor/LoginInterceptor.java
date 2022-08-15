package com.yice.edu.cn.yed.interceptor;

import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<Admin> tl=new ThreadLocal<>();

    public static Admin currentAdmin(){
        return tl.get();
    }
    public static String myId(){
        return currentAdmin().getId();
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String subject = URLDecoder.decode(request.getHeader(Constant.ADMIN),"UTF-8");

        if(subject==null||subject.length()<5){
            return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
        }else{
            final Admin exist = JSONUtil.toBean(subject, Admin.class);
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
