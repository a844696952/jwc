package com.yice.edu.cn.gateway.kit;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class FilterKit {
    public static Mono<Void> writeRes(ServerWebExchange exchange, ResponseJson responseJson){
        ServerHttpResponse response = exchange.getResponse();
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=1");
        //设置body
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(responseJson).getBytes());
        return exchange.getResponse().writeWith(Mono.just(bodyDataBuffer));
    }
}
