package com.yice.edu.cn.gateway.filter.bmp;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.gateway.kit.FilterKit;
import com.yice.edu.cn.gateway.service.bmp.ParentService;
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
public class BmpLoginFilter extends AbstractGatewayFilterFactory {
    @Autowired
    private ParentService parentService;
    Pattern pattern = Pattern.compile("^/bmp/login|^/bmp/register|^/bmp$|\\..*|.*/swagger.*|.*/webjars.*|.*/v2/api-docs.*$");
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
                    String existToken=parentService.findTokenByKey(id+Constant.Redis.BMP_TOKEN_SUFFIX);
                    if(existToken==null){
                        responseJson=new ResponseJson(false,Constant.HAVEN_LOGIN,"登录信息失效");
                    }else if(!existToken.equals(token)){
                        responseJson=new ResponseJson(false,Constant.LOGIN_INVALID,"已在其他手机登录,请重新登录!");
                    }
                    Parent parent=parentService.findParentById(id);
                    if(parent==null){
                        responseJson=new ResponseJson(false,Constant.LOGIN_INVALID,"已在其他手机登录,请重新登录!");
                    }
                    request=exchange.getRequest().mutate().header(Constant.Redis.BMP_PARENT_ID_HEADER, id).build();
                }


            }
            if(responseJson!=null){
                return FilterKit.writeRes(exchange,responseJson);
            }

            return chain.filter(exchange.mutate().request(request).build());

        };


    }


}
