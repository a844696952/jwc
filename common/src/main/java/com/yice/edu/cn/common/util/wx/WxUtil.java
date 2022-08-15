package com.yice.edu.cn.common.util.wx;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxData;
import com.yice.edu.cn.common.pojo.mes.wxPush.WxPushDetail;
import com.yice.edu.cn.common.util.http.HttpClinet;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
@Log4j2
public class WxUtil {



    public static String getAccessToken(String appId,String appSerect){
        if(StringUtils.isEmpty(appId) || StringUtils.isEmpty(appSerect)){
            return null;
        }
        String url = ("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_" +
                "credential&appid=").concat(appId).concat("&secret=").concat(appSerect);
        JSONObject jsonObject = JSON.parseObject(HttpClinet.get(url));
        return jsonObject.get("access_token").toString();
    }

    /**
     * @param access_token 接口调用凭证
     * @param temId 模板Id
     * @param openId 推送的对象Id
     * @param map 构建的Data对象
     * @param page 页面请求跳转的路径
     * */
    public static WxPushDetail push(String access_token,String temId, String openId, Map<String, WxData> map,String imgUrl,String page){
        WxPushDetail pushDetail = new WxPushDetail();
        pushDetail.setTemplate_id(temId);
        pushDetail.setTouser(openId);
        //pages/index/index
        pushDetail.setImgUrl(imgUrl);
        pushDetail.setPage(page);
        pushDetail.setData(map);
        pushDetail.setCreateTime(DateUtil.now());
        JSONObject jsonObject = JSON.parseObject(HttpClinet.postJson(
                "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="
                        +access_token, JSON.toJSONString(pushDetail)));
        log.info(jsonObject);
        pushDetail.setErrCode(jsonObject.get("errcode").toString());
        pushDetail.setIsRead(false);
        return pushDetail;
    }

    public static Map<String, WxData>  structureDataByMessage(String studentName,String value){
        Map<String, WxData> map = new HashMap<>(16);
        //因为都是通用一个模板，所以只需要构建模板一次
        WxData wxData1 = new WxData();
        wxData1.setValue(studentName);
        map.put("name1",wxData1);
        WxData wxData2 = new WxData();
        wxData2.setValue(value);
        map.put("thing2",wxData2);
        WxData wxData4 = new WxData();
        wxData4.setValue(DateUtil.now());
        map.put("time4",wxData4);
        return map;
    }

}
