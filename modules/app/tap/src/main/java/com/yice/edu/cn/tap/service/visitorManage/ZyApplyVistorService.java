package com.yice.edu.cn.tap.service.visitorManage;

import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.zyDetector.AESUtil;
import com.yice.edu.cn.common.util.zyDetector.Base64;
import com.yice.edu.cn.common.util.zyDetector.ZyDetector;
import com.yice.edu.cn.tap.feignClient.stuInAndOut.KqDevicePersonFeign;
import com.yice.edu.cn.tap.service.ycVeriface.YcVerifaceDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;


/**
 * @BelongsProject: yep
 * @BelongsPackage: com.yice.edu.cn.tap.service.visitor
 * @Author: Administrator
 * @CreateTime: 2019-04-12 09:35
 * @Description: ${Description}
 */
@Service
public class ZyApplyVistorService {
    @Autowired
   private KqDevicePersonFeign kqDevicePersonFeign;

    public DataReceiveResBean requestZyVisitorEnter(Boolean isProd, KqSchoolInit kqSchool,  Visitor visitor) {
        //添加zy请求参数（部分参数加密）
        Map<String, Object> bean = new HashMap<String, Object>();
        String prsnAvtrUrlAddr = null;
        if (visitor.getVisitorWay().equals("0")) {//0刷脸，1人证
            try {
                //下载七牛图片转base64
                byte[] bytes = HttpKit.downloadFileToServer(visitor.getVisitorImg());
               if (bytes==null||bytes.length==0){
                   DataReceiveResBean dataReceiveResBean = new DataReceiveResBean();
                   dataReceiveResBean.setReturnCode("0070");
                   dataReceiveResBean.setReturnMessage("网络异常");
                   return dataReceiveResBean;
               }
                prsnAvtrUrlAddr = Base64.encode(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                DataReceiveResBean dataReceiveResBean = new DataReceiveResBean();
                dataReceiveResBean.setReturnCode("0070");
                dataReceiveResBean.setReturnMessage("图片异常");
                return dataReceiveResBean;
            }
        } else if (visitor.getVisitorWay().equals("1")) {

        } else {
            DataReceiveResBean dataReceiveResBean = new DataReceiveResBean();
            dataReceiveResBean.setReturnCode("2999");
            dataReceiveResBean.setReturnMessage("访问信息缺失");
            return dataReceiveResBean;
        }
        if (visitor.getVisitorCard() != null) {
            bean.put("visiIdCard", visitor.getVisitorCard());
        } else {
            bean.put("visiIdCard", "410222198706134038");
        }
        bean.put("custId", kqSchool.getCustId());
        bean.put("visiTypeCd", "5");
        bean.put("visiName", visitor.getVisitorName());
        bean.put("visiPhone", visitor.getVisitorTel());
        bean.put("visiBeginTime", visitor.getStartTime());
        bean.put("visiFinishTime", visitor.getEndTime());
        //查找出本校所有的设备
      KqDevicePerson kqDevicePerson = new KqDevicePerson();
        kqDevicePerson.setSchoolId(mySchoolId());
        List<KqDevicePerson> kqDevicePersonListByCondition = kqDevicePersonFeign.findKqDevicePersonListByCondition(kqDevicePerson);
        List<KqDevicePerson> doorDeviceList = kqDevicePersonListByCondition.stream().filter(d -> d.getGroupType() != null && d.getGroupType().size() > 0 && d.getGroupType().contains("0")).collect(Collectors.toList());
        if (doorDeviceList==null||doorDeviceList.size()==0){
            DataReceiveResBean dataReceiveResBean = new DataReceiveResBean();
            dataReceiveResBean.setReturnCode("2999");
            dataReceiveResBean.setReturnMessage("学校没有设备信息");
            return dataReceiveResBean;
        }
        List<String> deviceNo = doorDeviceList.stream().map(KqDevicePerson::getDeviceNo).collect(Collectors.toList());
        String devices = deviceNo.toString();
        String devices1 = devices.substring(1, devices.length() - 1).replaceAll(", ", ",");
        bean.put("devices", devices1);
        String reqParam = AESUtil.encrypt(JSON.toJSONString(bean), kqSchool.getKey());
        //请求中移录入接口并返回结果
        String response = ZyDetector.postRequestVisitor(isProd, ZyDetector.ADD_VISITOR, kqSchool.getCoCode(), kqSchool.getRequstSource(), kqSchool.getVersion(), reqParam, prsnAvtrUrlAddr);
        DataReceiveResBean dataReceiveResBean2 = JSON.parseObject(response, DataReceiveResBean.class);
        return dataReceiveResBean2;
    }


}
