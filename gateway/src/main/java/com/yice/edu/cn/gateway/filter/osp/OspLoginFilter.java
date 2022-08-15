package com.yice.edu.cn.gateway.filter.osp;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.gateway.ignore.OspLoginIgnore;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * 权限拦截器
 */
@Component
public class OspLoginFilter extends AbstractGatewayFilterFactory {
    private static final Logger logger = LoggerFactory.getLogger(OspLoginFilter.class);
    @Autowired
    private OspLoginIgnore ospLoginIgnore;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @CreateCache(name = Constant.Redis.SCHOOL_VALID)
    private Cache<String,String[]> schoolValidCache;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();//获取路径
            if(ospLoginIgnore.getIgnores()!=null){
                boolean b = ospLoginIgnore.getIgnores().stream().anyMatch(urlPath -> antPathMatcher.match(urlPath, path));
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
                    //验证学校账号是否过期,暂时不验证,以后统一
                    /*String[] validStatus = schoolValidCache.get(teacher.getSchoolId());
                    if(validStatus!=null){
                        //null的情况下就当做未过期
                        boolean valid=validStatus[0].equals("42")&&validStatus[1].compareTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))>0;
                        if(!valid){
                            responseJson=new ResponseJson(false,"学校账号已经失效,请联系亿策公司");
                        }
                    }*/
                    String subject = claims.getSubject();
                    if(subject==null||subject.length()<5){
                        responseJson=new ResponseJson(false,Constant.HAVEN_LOGIN,"解析token subject失败");
                    }
                    Teacher teacher = JSONUtil.toBean(subject, Teacher.class);
                    logger.info("IP:{},URL:{},教师名称:{},教师ID:{}",request.getRemoteAddress(),path,teacher.getName(),teacher.getId());
                    Date expiration = claims.getExpiration();
                    DateTime dateTime = DateUtil.offsetMinute(expiration, -5);
                    if(dateTime.before(new Date())){//5分钟后过期的token要续期一下
                        String newToken = JwtUtil.createJWT(teacher.getId(), subject, null, Constant.Redis.OSP_TEACHER_TIMEOUT*1000);
                        exchange.getResponse().getHeaders().add(Constant.TOKEN,newToken);
                    }
                    try {
                        request=exchange.getRequest().mutate().header(Constant.TEACHER, URLEncoder.encode(subject, "UTF-8")).header(Constant.TOKEN,"").build();
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


}
