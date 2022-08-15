package com.yice.edu.cn.ewb.service.nb;

import com.yice.edu.cn.common.pojo.ResponseJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class NBCourseService {

    @Value("${nb.appKey}")
    private String appKey;

    @Value("${nb.appSecret}")
    private String appSecret;

    @Value("${nb.nbUrl}")
    private String nbUrl;

    /**
     * 获取NB连接
     * @param type
     * @param name
     * @param uId
     * @return
     */
    public ResponseJson getNbUrlByCourse(int type, String name, String uId){
        String pid="";
        long timestamp=System.currentTimeMillis()/1000;
        boolean res=false;
        switch (type){
            case 1:
                pid="NiFEjb83nJL4";
                break;
            case 2:
                pid="IfKiInEcZu9c";
                break;
            case 3:
                pid="JuFhE84jRhEh";
                break;
            case 4:
                pid="EjEViMk33jNr";
                break;
            case 5:
                pid="JeFJeFvReEI9";
                break;
            default:
                    res=true;
                    break;
        }
        if(res){
            return new ResponseJson("错误的课程类型");
        }
        String str=String.format("%1$s?uid=%2$s&appkey=%3$s&timestamp=%4$s&nickname=%5$s&pid=%6$s",nbUrl,uId,appKey,timestamp,name,pid);
        StringBuilder stringBuilder=new StringBuilder();
        StringBuilder append = stringBuilder.append(appKey).append(appSecret).append(name).append(pid).append(timestamp).append(uId);
        String encoderParam= DigestUtils.md5DigestAsHex(append.toString().getBytes()).toLowerCase();
        return new ResponseJson(String.format("%1$s&sign=%2$s",str,encoderParam)) ;
    }

}
