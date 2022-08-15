
package com.yice.edu.cn.ecc.interceptor;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.ecc.service.classCard.ClassCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<DmClassCard> tl=new ThreadLocal<>();

    @Autowired
    private ClassCardService classCardService;


    public static DmClassCard currentDmClassCard(){
        return tl.get();
    }
    public static String mySchoolId(){
        return currentDmClassCard().getSchoolId();
    }
    public static String myId(){
        return currentDmClassCard().getId();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String DmClassCardId = request.getHeader(Constant.Redis.ECC_TEACHER_ID_HEADER);
        if(DmClassCardId==null){
           return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
        }else{
            final DmClassCard exist = classCardService.findClassCardForCache(DmClassCardId);
            if(exist==null){
                return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
            }
            tl.set(exist);
            return true;

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        tl.remove();
    }

    private boolean writeResponse(ResponseJson responseJson, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=1");
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(responseJson));
        return false;
    }


}

