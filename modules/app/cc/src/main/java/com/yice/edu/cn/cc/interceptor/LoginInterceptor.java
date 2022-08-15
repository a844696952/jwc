package com.yice.edu.cn.cc.interceptor;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<String> tl=new ThreadLocal<>();

    public static String myId(){
        return tl.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String id = request.getHeader(Constant.Redis.CC_USER_ID);
        if(id==null){
           return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
        }else{
            tl.set(id);
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
