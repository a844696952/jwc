package com.yice.edu.cn.gateway.filter.cc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.gateway.kit.FilterKit;

import io.jsonwebtoken.Claims;

/**
 * 登录拦截器
 */
@Component
public class CcLoginFilter extends AbstractGatewayFilterFactory {

	private static final Logger logger = LoggerFactory.getLogger(CcLoginFilter.class);
	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			Object object = exchange.getAttribute("cachedRequestBodyObject");
			ServerHttpRequest request = exchange.getRequest();
			logger.info("IP:{},URL:{}",request.getRemoteAddress());
			String path = request.getURI().getPath();// 获取路径
			if (path.contains("/cc/login") || path.contains("/swagger") || path.contains("/v2/api-docs")
					|| path.contains("/webjars") || path.contains("/h5nl/cloudCourse")) {
				return chain.filter(exchange);
			}
			String token = "";
			// srs回调判断各个事件
			if (path.contains("/cc/srsEvent")) {
				return chain.filter(exchange);
			} 
			
			HttpHeaders requestHeaders = request.getHeaders();
			List<String> tokens = requestHeaders.get(Constant.TOKEN);
			if (tokens == null || tokens.size() == 0) {
				return FilterKit.writeRes(exchange, new ResponseJson(false, Constant.HAVEN_LOGIN, "登录信息失效"));
			}
			token = tokens.get(0);

			Claims claims = null;
			try {
				claims = JwtUtil.parseJWT(token);
			} catch (Exception e) {
				System.out.println("解析token失败:");
				System.out.println(e.getMessage());
				return FilterKit.writeRes(exchange, new ResponseJson(false, Constant.HAVEN_LOGIN, "解析token失败"));
			}
			if (claims != null) {
				String id = claims.getId();
				if (id == null) {
					return FilterKit.writeRes(exchange, new ResponseJson(false, Constant.HAVEN_LOGIN, "解析token id失败"));
				}
				request = exchange.getRequest().mutate().header(Constant.Redis.CC_USER_ID, id).build();
			}

			return chain.filter(exchange.mutate().request(request).build());
		};

	}
}