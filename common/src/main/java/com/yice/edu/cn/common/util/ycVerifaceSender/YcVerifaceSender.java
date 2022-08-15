package com.yice.edu.cn.common.util.ycVerifaceSender;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class YcVerifaceSender {

    private final static Logger logger = LoggerFactory.getLogger(YcVerifaceSender.class);
    //中移接口路径
    private static final String testUrl = "http://192.168.102.110:80";
    private static final String prodUrl = "http://192.168.102.110:80";
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
    public static final String DELETE_MEMBER = "DELETE_MEMBER";//删除人员
    public static final String MATCH_BATCH_N = "MATCH_BATCH_N";//校验列表中人员是否存在，并返回人员id
    public static final String MATCH_BATCH_ID = "MATCH_BATCH_ID";//校验列表id人员是否存在
    public static final String ID_FEATURE = "ID_FEATURE";//获取算法服务器端生成好的特征值

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
    public static String postRequest(Boolean isProd,String busiCode,String schoolId,Object center){
        //判断是否生产环境选择请求路径和IP
        String url = isProd?prodUrl:testUrl;
        String myIP = isProd?prodIP:testIP;
        url = getUrlByBusiCode(busiCode, url);
        Map<String, Object> map = new HashMap<>();
        map.put("schoolID",schoolId);
        map.put("beans",center);
        logger.info("**********************************REQUEST_URL******************************************************");
        logger.info("请求亿策人脸算法服务器的地址："+url);
        logger.info("**********************************REQUEST_DATA*****************************************************");
        //logger.info("发送给人脸算法服务器的报文>" + JSON.toJSONString(map));
        String res = HttpRequest.post(url)
                .header("Proxy-Client-IP",myIP)
                .header("X-FORWARDED-FOR", myIP)
                .header("WL-proxy-Client-IP",myIP)
                .header("HTTP_CLIENT_IP",myIP)
                .header("X-Real-IP",myIP)
                .body(JSON.toJSONString(map))
                .execute().body();
        logger.info("**********************************RESPONSE*********************************************************");
       if (!busiCode.equals(ID_FEATURE)){
           logger.info("请求亿策人脸算法服务器的响应:"+res);
       }
        return res;
       /*return "0000";*/
    }
    private static String getUrlByBusiCode(String busiCode,String url){
        switch (busiCode){
            case "ADD_MEMBER":
                url= url+"/register_batch";break;
            case "EDIT_MEMBER":
                url= url+"/modify_batch";break;
            case "DELETE_MEMBER":
                url= url+"/delete";break;
            case "MATCH_BATCH_N":
                url= url+"/match_batch_n";break;
            case "MATCH_BATCH_ID":
                url= url+"/match_batch_id";break;
            case "ID_FEATURE":
                url= url+"/id_feature";break;
        };
       return url;
    }


}
