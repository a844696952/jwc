package com.yice.edu.cn.api.interceptor;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.api.service.cms.XwCmsOfficialWebsiteService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ApiInterceptor implements HandlerInterceptor {

    public static ThreadLocal<String> domain=new ThreadLocal<>();


    public static String currentSchoolId(){return domain.get();}

    @Autowired
    XwCmsOfficialWebsiteService xwCmsOfficialWebsiteService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String secondDomain=request.getParameter("secondDomain");
        String url=request.getRequestURI();
        if(url.contains("/open/xwCmsOfficialWebsite")){
            if(StringUtils.isNotEmpty(secondDomain)){
                String schoolIdBySecondDomain = xwCmsOfficialWebsiteService.findSchoolIdBySecondDomain(secondDomain);
                if(StringUtils.isNotEmpty(schoolIdBySecondDomain)) {
                    domain.set(schoolIdBySecondDomain);
                    return true;
                }
            }
            return writeResponse(new ResponseJson(false,404,"域名不存在或者被关闭"),response);
        }
        return true;
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
