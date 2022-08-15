package com.yice.edu.cn.common.util.zyDetector;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.yice.edu.cn.common.util.zyDetector.CommonUtils.getTransNum;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.common.util.zyDetector
 * @Author: gzw
 * @CreateTime: 2019-02-28 06:16
 * @Description: 中移在线监视器
 */
public class ZyDetector {

    private final static Logger logger = LoggerFactory.getLogger(ZyDetector.class);
    /*@Value("#{'${spring.profiles.active}'=='prod'}")
    private static Boolean isProd;//判断是否是生产环境*/

    //中移接口路径
    private static final String testUrl = "http://120.194.46.246:31001/iscctsvs/";
    private static final String prodUrl = "http://221.181.128.248:20011/iscctsvs/";
    // private static final String version = "1.0.0";
    public static final String INIT_KEY = "FLsHLnpe";//初始化密钥
    public static final String PREFIX = "1085";//流水号前缀
    //设备类型
    public static final String CAMERA = "1";
    public static final String GATE_MACHINE = "2";
    public static final String ALL_DEVICES = "0";
    //业务编码
    public static final String GET_USER_ACCT = "GET_USER_ACCT";//初始化业务编码
    public static final String ADD_MEMBER = "ADD_MEMBER";//新增人员
    public static final String EDIT_MEMBER = "EDIT_MEMBER";//编辑人员
    public static final String DELETE_MEMBER = "DELETE_MEMBER";//编辑人员
    public static final String GET_DEVICES = "GET_DEVICES";//获得设备列表
    public static final String ADD_PERSON_DEVICE = "ADD_PERSON_DEVICE";//人设绑定（门禁）
    public static final String SEND_KEY = "SEND_KEY";//学校密钥操作（门禁）
    public static final String GENERATE_KEY = "generate";//获取密钥
    public static final String RENEW_KEY = "renew";//获取密钥
    public static final String ADD_VISITOR = "ADD_VISITOR";//访客申请
    public static final String FACE_DETECT = "FACE_DETECT";//图片校验

    //返回状态参数
    public  static final String SUCCESS = "0000";
    //中启IP
    private static final String prodIP = "47.99.127.78";
    private static final String testIP = "218.6.69.201";
    /**
     *  请求中移接口
     */
    public static String postRequest(Boolean isProd,String busiCode,String coCode,String requestSource,String version,String reqParam){
        //判断是否生产环境选择请求路径和IP
        String url = isProd?prodUrl:testUrl;
        String myIP = isProd?prodIP:testIP;
        Map<String, Object> map = new HashMap<>();
        map.put("busiCode",busiCode);
        map.put("coCode",coCode);
        map.put("requestSource",requestSource);
        map.put("transactionID",getTransNum(PREFIX, 6));
        map.put("version",version);
        map.put("reqParam",reqParam);
        //logger.info("发送给中移动的报文>" + JSON.toJSONString(map));
        ///加密操作
        logger.info("************************请求中移动"+busiCode+"返回*********************");
        String res = HttpRequest.post(url)
                .header("Proxy-Client-IP",myIP)
                .header("X-FORWARDED-FOR", myIP)
                .header("WL-proxy-Client-IP",myIP)
                .header("HTTP_CLIENT_IP",myIP)
                .header("X-Real-IP",myIP)
                .body(JSON.toJSONString(map))
                .execute().body();
        logger.info("************************请求中移动返回*********************");
        //logger.info("请求中移动返回"+res);
        return res;
        //String res = HttpUtil.post(url,map);
    }

    public static String postRequestVisitor(Boolean isProd,String busiCode,String coCode,String requestSource,String version,String reqParam,String prsnAvtrUrlAddr){
        //判断是否生产环境选择请求路径和IP
        String url = isProd?prodUrl:testUrl;
        String myIP = isProd?prodIP:testIP;
        Map<String, Object> map = new HashMap<>();
        map.put("busiCode",busiCode);
        map.put("coCode",coCode);
        map.put("requestSource",requestSource);
        map.put("transactionID",getTransNum(PREFIX, 6));
        map.put("version",version);
        map.put("reqParam",reqParam);
        map.put("visiPhoto",prsnAvtrUrlAddr);
       // logger.info("发送给中移动的报文>"+JSON.toJSONString(map));
        ///加密操作
        logger.info("************************请求中移动"+busiCode+"返回*********************");
        String res = HttpRequest.post(url)
                .header("Proxy-Client-IP",myIP)
                .header("X-FORWARDED-FOR", myIP)
                .header("WL-proxy-Client-IP",myIP)
                .header("HTTP_CLIENT_IP",myIP)
                .header("X-Real-IP",myIP)
                .body(JSON.toJSONString(map))
                .execute().body();
        logger.info("************************请求中移动返回*********************");
        //logger.info("请求中移动返回"+res);
        return res;
        //String res = HttpUtil.post(url,map);
    }


}
