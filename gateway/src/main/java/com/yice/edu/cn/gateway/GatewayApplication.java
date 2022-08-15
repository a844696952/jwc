package com.yice.edu.cn.gateway;

import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.yice.edu.cn.common.util.net.IpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@SpringBootApplication
@RestController
@EnableMethodCache(basePackages = "com.yice.edu.cn.gateway")
@EnableCreateCacheAnnotation
@EnableFeignClients
@EnableHystrix
public class GatewayApplication {
    @Value("${company.ip}")
    private String companyIp;
    @Value("${openSwagger:false}")
    private boolean openSwagger;//是否开放swagger
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
    @Bean
    @Order(10101)
    @ConditionalOnExpression("'${spring.profiles.active}'=='prod'")
    public GlobalFilter http2Https() {
        return (exchange, chain) -> {
            Object uriObj = exchange.getAttributes().get(GATEWAY_REQUEST_URL_ATTR);
            if (uriObj != null) {
                URI uri = (URI) uriObj;
                uri = this.upgradeConnection(uri, "http");
                exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            }
            return chain.filter(exchange);
        };
    }
    private URI upgradeConnection(URI uri, String scheme) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(uri).scheme(scheme);
        if (uri.getRawQuery() != null) {
            uriComponentsBuilder.replaceQuery(uri.getRawQuery().replace("+", "%20"));
        }
        return uriComponentsBuilder.build(true).toUri();
    }

    @Bean
    @Order(-1)
    @ConditionalOnExpression("'${spring.profiles.active}'!='prod'")
    public GlobalFilter swaggerFilter(){
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if(!path.contains("swagger-ui.html")){
                return chain.filter(exchange);
            }
            InetSocketAddress remoteAddress = request.getRemoteAddress();
            String ip = remoteAddress.getHostString();
            //1.是否开放openSwagger
            if(openSwagger){
                return chain.filter(exchange);
            }
            //2.公司的ip通过
            if(companyIp.contains(ip)){
                return chain.filter(exchange);
            }
            //3.局域网
            if(IpUtil.isInner(ip)){
                return chain.filter(exchange);
            }
            //4.本机ip
            if("127.0.0.1".equals(ip)||"0:0:0:0:0:0:0:1".equals(ip)){
                return chain.filter(exchange);
            }
            ServerHttpResponse response = exchange.getResponse();
            DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr("").getBytes());
            return response.writeWith(Mono.just(bodyDataBuffer));
        };
    }
}
