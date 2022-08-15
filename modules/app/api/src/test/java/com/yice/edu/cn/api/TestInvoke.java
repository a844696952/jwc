package com.yice.edu.cn.api;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestInvoke {
    private String appKey="JI887H4OM4H4D93";//第三方放在配置文件里，当发现泄露时，可更改
    //第三方放在一个静态变量里
    private String token="12.eyJqdGkiOiIxIiwiaWF0IjoxNTU5MTEzNDM1LCJzdWIiOiJ7fSIsImV4cCI6MTU1OTExNzAzNX0.Lmqvgwa3j_LWnpuw5_uwY1TJ-re47QWCo6k8gOYjQdY";
    @Test
    public void login(){
        Map<String,Object> map=new HashMap<>();
        map.put("appKey",appKey);
        HttpResponse response = HttpRequest.post("http://localhost:8999/api/login/login").body(JSONUtil.toJsonStr(map), ContentType.JSON.toString()).execute();
        ResponseJson responseJson = JSONUtil.toBean(response.body(), ResponseJson.class);
        if(responseJson.getResult().isSuccess()){
            this.token=responseJson.getData().toString();
        }
        //查看下内容
        System.out.println(JSONUtil.toJsonStr(responseJson));
    }
    @Test
    public void testQuery(){


        String ospToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNzQxNTk2OTM3ODExNzQ2ODE3IiwiaWF0IjoxNTYzNDU2ODU3LCJzdWIiOiJ7XCJpbWdVcmxcIjpcIi91cGxvYWQvYXZhdGFyLzIwMTgvMTAyOC82ZWQxMWYzZjIyNjc0MzJmLmpwZ1wiLFwic2Nob29sXCI6e1widHlwZUlkXCI6XCIxMjJcIn0sXCJzY2hvb2xJZFwiOlwiMTczNTg3Mzc3MzYzMDc0MjUyOFwiLFwibmFtZVwiOlwi5LqM6Zi_5ZOlXCIsXCJ0ZWxcIjpcIjEzMjU1MDU4ODkxXCIsXCJpZFwiOlwiMTc0MTU5NjkzNzgxMTc0NjgxN1wiLFwic2Nob29sTmFtZVwiOlwi56aP5bu65biI5aSn6ZmE5LitXCIsXCJzdGF0dXNcIjpcIjQwXCJ9IiwiZXhwIjoxNTYzNDYwNDU3fQ.oDPcSYym8tfcKmbob_w29e1bPfcWlkP67vE-hjRbqLU";
        //String url="http://wh.ycjdedu.com:8999/api/teacher/findTeacherByToken";
        String url="http://localhost:8999/api/teacher/findTeacherByToken";
        ResponseJson r = query(url, "post", ContentType.FORM_URLENCODED, "token="+ospToken);
        System.out.println(r.getData());
    }
    //封装个方法用于获取业务数据,根据返回的code判断token是否过期，过期了去登录然后递归
    public ResponseJson query(String url,String method,ContentType contentType,String body){
        HttpResponse response;
        if(method.equalsIgnoreCase("get")){
            response = HttpRequest.get(url).header("token", this.token).execute();
        }else{
            response = HttpRequest.post(url).body(body, contentType.toString()).header("token", this.token).execute();
        }
        ResponseJson responseJson = JSONUtil.toBean(response.body(), ResponseJson.class);
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        System.out.println("长度:"+stackTrace.length);
        if(!responseJson.getResult().isSuccess()&& Constant.HAVEN_LOGIN==responseJson.getResult().getCode()){
            //说明token过期或者错误,重新获取
            this.login();
            return this.query(url,method,contentType,body);
        }
        return responseJson;
    }


    @Test
    public void  test(){
    /*    String currentDate="2019-08-06";
        if(DateUtil.parse(currentDate,"yyyy-MM-dd").isAfterOrEquals(DateUtil.parse(DateUtil.now(),"yyyy-MM-dd"))){
            System.out.println(true);
        }*/
        long between = DateUtil.between(DateUtil.parse("2019-08-02:11:20:10", "yyyy-MM-dd"), DateUtil.parse("2019-08-03:11:20:10", "yyyy-MM-dd"), DateUnit.DAY);
        System.out.println();

    }
}
