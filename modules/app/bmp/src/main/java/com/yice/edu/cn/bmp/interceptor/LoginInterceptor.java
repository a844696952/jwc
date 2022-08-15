package com.yice.edu.cn.bmp.interceptor;

import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<Parent> tl = new ThreadLocal<>();
    @CreateCache(name=Constant.Redis.BMP_PARENT_CACHE)
    private Cache<String,Parent> parentCache;
    @Autowired
    StudentService studentService;

    public static Parent currentParent() { return tl.get();}


    public static String myParentId() {
        return currentParent().getId();
    }

    public static String myStudentId() {
        return currentParent().getStudentId();
    }

    public static String mySchoolId() {
        return currentParent().getSchoolId();
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String parentId = request.getHeader(Constant.Redis.BMP_PARENT_ID_HEADER);
        if (parentId == null) {
            return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN, "登录信息失效"), response);
        } else {
            Parent  parent = parentCache.get(parentId);
            if(parent==null){
                return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
            }
            parentCache.put(parentId,parent);
            tl.set(parent);
            return true;

        }
    }

    private boolean writeResponse(ResponseJson responseJson, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=1");
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(responseJson));
        return false;
    }
}
