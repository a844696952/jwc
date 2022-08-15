package com.yice.edu.cn.osp.service.xw.kq;

import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.kq.ZyDeviceBean;
import com.yice.edu.cn.common.util.zyDetector.AESUtil;
import com.yice.edu.cn.common.util.zyDetector.ZyDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.osp.service.xw.kq
 * @Author: Administrator
 * @CreateTime: 2019-03-05 14:08
 * @Description: 中移设备管理
 */
@Service
public class KqSchoolZyDevicesService {
    private static final Logger logger = LoggerFactory.getLogger(KqSchoolZyDevicesService.class);
    public List<ZyDeviceBean> findSchoolDevices(Boolean isProd, KqSchoolInit kqSchool, String deviceType) {
            List<Object> list = new ArrayList<Object>();
            List<ZyDeviceBean> list1 = new ArrayList<ZyDeviceBean>();
            String response =  ZyDetector.postRequest(isProd,ZyDetector.GET_DEVICES,kqSchool.getCoCode(),kqSchool.getRequstSource(),kqSchool.getVersion(),null);
            DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
            list = dataReceiveResBean.getBeans();
            for (Object o:list){
                ZyDeviceBean zyDeviceBean = JSON.parseObject(o.toString(),ZyDeviceBean.class);
                if (zyDeviceBean.getDeviceType().equals(deviceType)){
                    list1.add(zyDeviceBean);
                }
            }
        return list1;
    }

    public Boolean boundDevices(Boolean isProd, List<String> enterStus, KqSchoolInit kqSchool, List<String> zyDeviceBeans) {
        //添加ZY请求参数
        Map<String, String> map = new HashMap<>();
        List<String> deviceList = new ArrayList<String>();
        List<String> staffKeyList = new ArrayList<String>();
        for (String z :zyDeviceBeans){
            deviceList.add(z);
        }
        for (String z :enterStus){
            staffKeyList.add(z);
        }
        String devices = deviceList.toString();
        String  devices1 = devices.substring(1,devices.length()-1).replaceAll(", ",",");
       if (devices1==null||devices1.equals("")){
           devices1="00000000000000";
           //未找到要绑定的设备，进行解绑所有设备操作
       }
        String staffKeys = staffKeyList.toString();
        String  staffKeys1 = staffKeys.substring(1,staffKeys.length()-1).replaceAll(", ",",");
        map.put("staffKey", staffKeys1);// 替换，人员标识，多个以英文逗号分隔，注意是和校园的人员标识
        map.put("devices", devices1);// 替换，设备编码，多个以英文逗号分隔
        map.put("custId",kqSchool.getCustId());
        logger.info("请求中移人设绑定接口：{人员id列表："+staffKeys1+";设备列表："+devices1+"}");
        String reqParam = AESUtil.encrypt(JSON.toJSONString(map), kqSchool.getKey());
        String response = ZyDetector.postRequest(isProd,ZyDetector.ADD_PERSON_DEVICE,kqSchool.getCoCode(),kqSchool.getRequstSource(),kqSchool.getVersion(), reqParam);
        DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
        if (!dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)){
            //System.out.println("人设绑定失败"+dataReceiveResBean);
            return false;
        }
        //System.out.println("人设绑定成功"+dataReceiveResBean);
        return true;
    }
}
