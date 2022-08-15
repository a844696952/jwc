package com.yice.edu.cn.gateway.filter.yed;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.gateway.ignore.YedLoginIgnore;
import com.yice.edu.cn.gateway.kit.FilterKit;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 权限拦截器
 */
@Component
public class YedLoginFilter extends AbstractGatewayFilterFactory {
    @Autowired
    private YedLoginIgnore yedLoginIgnore;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();//获取路径
            if(yedLoginIgnore.getIgnores()!=null){
                boolean b = yedLoginIgnore.getIgnores().stream().anyMatch(urlPath -> antPathMatcher.match(urlPath, path));
                if(b){
                    return chain.filter(exchange);
                }
            }
            HttpHeaders requestHeaders = request.getHeaders();
            List<String> tokens = requestHeaders.get(Constant.TOKEN);
            ResponseJson responseJson=null ;
            if(tokens==null||tokens.size()==0){
                responseJson= new ResponseJson(false,Constant.HAVEN_LOGIN,"登录信息失效");
            }else {
                String token = tokens.get(0);
                Claims claims = null;
                try {
                    claims = JwtUtil.parseJWT(token);
                } catch (Exception e) {
                    responseJson = new ResponseJson(false, Constant.HAVEN_LOGIN,"解析token失败");
                }
                if(claims!=null){
                    String subject = claims.getSubject();
                    if(subject==null||subject.length()<5){
                        responseJson=new ResponseJson(false,Constant.HAVEN_LOGIN,"解析token subject失败");
                    }
                    Admin admin = JSONUtil.toBean(subject, Admin.class);
                    Date expiration = claims.getExpiration();
                    DateTime dateTime = DateUtil.offsetMinute(expiration, -5);
                    if(dateTime.before(new Date())){//5分钟后过期的token要续期一下
                        String newToken = JwtUtil.createJWT(admin.getId(), subject, null, Constant.Redis.YED_ADMIN_TIMEOUT*1000);
                        exchange.getResponse().getHeaders().add(Constant.TOKEN,newToken);
                    }
                    try {
                        request=exchange.getRequest().mutate().header(Constant.ADMIN, URLEncoder.encode(subject, "UTF-8")).header(Constant.TOKEN,"").build();
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalArgumentException(e);
                    }
                }


            }
            if(responseJson!=null){
                return FilterKit.writeRes(exchange,responseJson);
            }

            return chain.filter(exchange.mutate().request(request).build());

        };


    }

    public static void main(String[] args) {
        String path="/yed/abc/abcaf-bds.abc";
        Pattern pattern = Pattern.compile("^/yed/login|^/yed$|\\..*$");
        System.out.println(pattern.matcher(path).find());
    }
}
