package com.yice.edu.cn.pcd.interceptor;

import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehAccount;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<EehAccount> tl=new ThreadLocal<>();
    @CreateCache(name=Constant.Redis.PCD_ACCOUNT_CACHE)
    private Cache<String, EehAccount> accountCache;

    public static EehAccount currentAccount() { return tl.get();}
    public static String currentEehId(){
        return tl.get().getEehId();
    }
    public static String currentId(){
        return tl.get().getId();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String accountId = request.getHeader(Constant.Redis.PCD_ACCOUNT_ID_HEADER);
        if (accountId == null) {
            return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN, "登录信息失效"), response);
        } else {
            EehAccount eehAccount = accountCache.get(accountId);
            if(eehAccount==null){
                return writeResponse(new ResponseJson(false, Constant.HAVEN_LOGIN,"登录信息失效"),response);
            }
            accountCache.put(accountId,eehAccount);
            tl.set(eehAccount);
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
