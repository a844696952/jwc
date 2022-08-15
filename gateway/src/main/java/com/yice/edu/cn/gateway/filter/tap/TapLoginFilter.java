package com.yice.edu.cn.gateway.filter.tap;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.gateway.kit.FilterKit;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 权限拦截器
 */
@Component
public class TapLoginFilter extends AbstractGatewayFilterFactory {
    Pattern pattern = Pattern.compile("^/tap/login|^/tap$|\\..*|.*/swagger.*|.*/webjars.*|.*/v2/api-docs.*$");
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();//获取路径
            int lastIndex = path.lastIndexOf("?");
            if(lastIndex!=-1){
                path=path.substring(0, lastIndex);
            }
            if(pattern.matcher(path).find()){
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
                    System.out.println("解析token失败:");
                    System.out.println(e.getMessage());
                    responseJson = new ResponseJson(false, Constant.HAVEN_LOGIN,"解析token失败");
                }
                if(claims!=null){
                    String id = claims.getId();
                    if(id==null){
                        responseJson=new ResponseJson(false,Constant.HAVEN_LOGIN,"解析token id失败");
                    }
                    request=exchange.getRequest().mutate().header(Constant.Redis.TAP_TEACHER_ID_HEADER, id).build();
                }


            }
            if(responseJson!=null){
                return FilterKit.writeRes(exchange,responseJson);
            }

            return chain.filter(exchange.mutate().request(request).build());

        };


    }


}
