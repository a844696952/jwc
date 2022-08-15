package com.yice.edu.cn.osp.interceptor;

import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpaceConfig;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.osp.service.xw.cms.XwCmsSchoolSpaceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.regex.Pattern;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<Teacher> tl=new ThreadLocal<>();
    public static ThreadLocal<String> domain=new ThreadLocal<>();
    @Autowired
    private XwCmsSchoolSpaceConfigService xwCmsSchoolSpaceConfigService;

    /**
     * teacher对象里只有若干个常用属性，详见
     * {@link com.yice.edu.cn.osp.controller.login.LoginController#login}
     * @return
     */
    public static Teacher currentTeacher(){
        return tl.get();
    }
    public static String currentSchoolId(){return domain.get();}
    public static String mySchoolId(){
        return currentTeacher().getSchoolId();
    }
    public static String myId(){
        return currentTeacher().getId();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String secondDomain=request.getParameter("secondDomain");
        String url=request.getRequestURI();
        if(url.contains("/xwCmsOfficialWebsite")){
            if(StringUtils.isNotEmpty(secondDomain)){
                String schoolIdBySecondDomain = xwCmsSchoolSpaceConfigService.findSchoolIdBySecondDomain(secondDomain);
                if(StringUtils.isNotEmpty(schoolIdBySecondDomain)) {
                    domain.set(schoolIdBySecondDomain);
                    return true;
                }
            }
            return writeResponse(new ResponseJson(false,404,"域名不存在或者被关闭"),response);
        }
        String subject = URLDecoder.decode(request.getHeader(Constant.TEACHER),"UTF-8");
        if(subject==null||subject.length()<5){
           return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
        }else{
            final Teacher exist = JSONUtil.toBean(subject, Teacher.class);
            tl.set(exist);
            return true;

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        domain.remove();
    }

    private boolean writeResponse(ResponseJson responseJson, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=1");
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(responseJson));
        return false;
    }
}
