package com.yice.edu.cn.gateway.filter.ws;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.gateway.kit.FilterKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class WsLoginFilter extends AbstractGatewayFilterFactory {
    private static final Logger logger = LoggerFactory.getLogger(WsLoginFilter.class);
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            logger.info("IP:{},URL:{}",request.getRemoteAddress());
            String token = request.getQueryParams().toSingleValueMap().get("token");
            ResponseJson responseJson=null ;
            if(token==null){
                responseJson=new ResponseJson(false, Constant.HAVEN_LOGIN,"token必传");
            }
            try {
                JwtUtil.parseJWT(token);
            } catch (Exception e) {
                responseJson = new ResponseJson(false, Constant.HAVEN_LOGIN,"解析token失败");
            }
            if(responseJson!=null){
                return FilterKit.writeRes(exchange,responseJson);
            }
            return chain.filter(exchange.mutate().request(request).build());

        };
    }
}
