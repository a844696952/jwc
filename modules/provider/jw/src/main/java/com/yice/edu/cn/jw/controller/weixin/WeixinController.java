package com.yice.edu.cn.jw.controller.weixin;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.jw.weixin.Jscode2session;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@RestController
@RequestMapping("/wx")
@ApiModel(value = "微信对接相关信息")
public class WeixinController {
    @PostMapping("/jsCode2Session")
    @ApiOperation("微信小程序通过js_code查询openid")
    public String jsCode2Session(@RequestBody Jscode2session jscode2session){
        CloseableHttpClient hp = null;
        try{
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy(){
                //信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            hp = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            StringBuffer url = new StringBuffer("https://api.weixin.qq.com/sns/jscode2session?")
                    .append("appid=").append(jscode2session.getAppId()).append("&secret=").append(jscode2session.getSecret())
                    .append("&grant_type=authorization_code").append("&js_code=").append(jscode2session.getJsCode());
            HttpGet hg = new HttpGet(url.toString());
            hg.setHeader("Content-Type","application/json");
            CloseableHttpResponse response = hp.execute(hg);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity,"utf-8");
            System.out.println(content);
            JSONObject jsonObject = JSONUtil.parseObj(content);
            hp.close();
            if (null==jscode2session.getNeedAll()){
                return jsonObject.get("openid").toString();
            }else {
                return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                hp.close();
            }catch (Exception e){

            }
        }
        return null;
    }
}
