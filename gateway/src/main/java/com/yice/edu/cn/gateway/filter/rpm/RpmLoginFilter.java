package com.yice.edu.cn.gateway.filter.rpm;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.gateway.kit.FilterKit;
import com.yice.edu.cn.gateway.service.osp.TeacherService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 登录拦截器
 */
@Component
public class RpmLoginFilter extends AbstractGatewayFilterFactory {
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();//获取路径
            if(path.contains("/rpm/login")||path.contains("/swagger")||path.contains("/v2/api-docs")||path.contains("/webjars")){
                return chain.filter(exchange);
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
                    if(subject.length()<5){
                        responseJson=new ResponseJson(false,Constant.HAVEN_LOGIN,"解析token subject失败");
                    }
                    request=exchange.getRequest().mutate().header(Constant.TEACHER, subject).header(Constant.TOKEN,"").build();
                }


            }
            if(responseJson!=null){
                return FilterKit.writeRes(exchange,responseJson);
            }

            return chain.filter(exchange.mutate().request(request).build());

        };


    }


}
