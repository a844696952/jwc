package com.yice.edu.cn.gateway.filter.websocket;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.gateway.kit.FilterKit;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;


@Component
public class WebSocketLoginFilter extends AbstractGatewayFilterFactory {


    private static final Logger logger = LoggerFactory.getLogger(WebSocketLoginFilter.class);
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            String token = queryParams.getFirst("token");
            ResponseJson responseJson=null ;
            if(token==null){
                responseJson= new ResponseJson(false,Constant.HAVEN_LOGIN,"登录信息失效");
            }else {
                Claims claims = null;
                try {
                    claims = JwtUtil.parseJWT(token);
                } catch (Exception e) {
                    responseJson = new ResponseJson(false, Constant.HAVEN_LOGIN, "解析token失败");
                }
                if (claims != null) {
                    String id = claims.getId();
                    if (id == null) {
                        responseJson = new ResponseJson(false, Constant.HAVEN_LOGIN, "解析token id失败");
                    }
                }
            }

            if(responseJson!=null){
                return FilterKit.writeRes(exchange,responseJson);
            }
            return chain.filter(exchange);

        };


    }

}
