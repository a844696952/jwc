package com.yice.edu.cn.gateway.filter.api;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.gateway.ignore.ApiLoginIgnore;
import com.yice.edu.cn.gateway.kit.FilterKit;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
@Component
public class ApiLoginFilter extends AbstractGatewayFilterFactory {
    private static final Logger logger = LoggerFactory.getLogger(ApiLoginFilter.class);
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private ApiLoginIgnore apiLoginIgnore;
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();//获取路径
            if(apiLoginIgnore.getIgnores()!=null){
                boolean b = apiLoginIgnore.getIgnores().stream().anyMatch(urlPath -> antPathMatcher.match(urlPath, path));
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
                    String id = claims.getId();
                    if(id==null){
                        responseJson=new ResponseJson(false,Constant.HAVEN_LOGIN,"解析token id失败");
                    }
                }


            }
            if(responseJson!=null){
                return FilterKit.writeRes(exchange,responseJson);
            }
            return chain.filter(exchange.mutate().request(request).build());

        };


    }

}
