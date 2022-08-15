package com.yice.edu.cn.tap.service.visitorManage;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDevicePerson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.personEnter.YcVerifacePersonEnter;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceDoorChangeResBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifacePersonBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceResBean;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.ycVerifaceSender.YcVerifaceSender;
import com.yice.edu.cn.common.util.zyDetector.AESUtil;
import com.yice.edu.cn.common.util.zyDetector.ZyDetector;
import com.yice.edu.cn.tap.controller.visitorManage.VisitorController;
import com.yice.edu.cn.tap.feignClient.stuInAndOut.KqDevicePersonFeign;
import com.yice.edu.cn.tap.feignClient.ycveriface.WsYcVerifaceFeign;
import com.yice.edu.cn.tap.feignClient.ycveriface.YcVerifacePersonEnterFeign;
import com.yice.edu.cn.tap.service.ycVeriface.YcVerifaceDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;


@Service
public class YcApplyVistorService {
    @Autowired
    private YcVerifaceDeviceService ycVerifaceDeviceService;
    @Autowired
    private YcVerifacePersonEnterFeign ycVerifacePersonEnterFeign;
    @Autowired
    private WsYcVerifaceFeign wsYcVerifaceFeign;

    @CreateCache(name = Constant.Redis.YC_VERIFACE_DOOR_DEVICE_PERSON_CHANGE_LIST, expire = Constant.Redis.YC_VERIFACE_DOOR_DEVICE_PERSON_CHANGE_LIST_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String, YcVerifaceDoorChangeResBean> ycVerifaceDoorDevicePersonChangeList;//key:设备id，value：变化列表

    private final static Logger logger = LoggerFactory.getLogger(YcApplyVistorService.class);



    public DataReceiveResBean requestVisitorEnter(Boolean isProd,  Visitor visitor) {
        DataReceiveResBean res = new DataReceiveResBean();
        //查找校门口分组门禁设备
       List<YcVerifaceDevice> inAndOutDevices = ycVerifaceDeviceService.findSchoolInAndOutDevice(mySchoolId());
        List<YcVerifaceDevice> deviceList = inAndOutDevices.stream().filter(device -> StrUtil.isNotEmpty(device.getDeviceSign())&&device.getType().equals("1")).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(deviceList)){
           res.setReturnCode("2999");
           return res;
       }
        //下载图片
        String imgUrl = visitor.getVisitorImg();
        try {
            byte[] bytes = HttpKit.downloadFileToServer(imgUrl);
            String   prsnAvtrUrlAddr = Base64.getEncoder().encodeToString(bytes);
            visitor.setVisitorImg("1,"+prsnAvtrUrlAddr);
        }catch (Exception e){
            logger.error("图片下载异常："+e);
            res.setReturnCode("0070");
            return res;
        }
        //录入校验图片
        DataReceiveResBean dataReceiveResBean = ycPhotoCheck(isProd, visitor);
        //校验成功，录入人员表
        KqDeviceGroupPerson enter = new KqDeviceGroupPerson();
        if (dataReceiveResBean.getReturnCode().equals("0000")){
            YcVerifacePersonEnter ycVerifacePersonEnter = new YcVerifacePersonEnter();
            ycVerifacePersonEnter.setPersonType(Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_VISITOR);
            ycVerifacePersonEnter.setPersonId(visitor.getVisitorName()+"MyTel"+visitor.getVisitorTel());
            ycVerifacePersonEnter.setSchoolId(visitor.getSchoolId());
            YcVerifacePersonEnter oneYcVerifacePersonEnterByCondition = ycVerifacePersonEnterFeign.findOneYcVerifacePersonEnterByCondition(ycVerifacePersonEnter);
            if (oneYcVerifacePersonEnterByCondition==null){
                ycVerifacePersonEnterFeign.saveYcVerifacePersonEnter(ycVerifacePersonEnter);
            }
            enter.setPersonId(ycVerifacePersonEnter.getPersonId());
            enter.setPersonType(ycVerifacePersonEnter.getPersonType());
            enter.setName(visitor.getVisitorName());
            enter.setSchoolId(visitor.getSchoolId());
        }else {
            res.setReturnCode("9999");
            return res;
        }
        //人员下发至门禁设备终端
        YcVerifaceDoorChangeResBean doorChangeResBean = ycVerifaceDoorPeoplePullFea(isProd, enter);
        //存到缓存，并给设备下发拉取指令
        List<YcVerifacePersonBean> insetList = doorChangeResBean.getInsetList();
        if (CollectionUtil.isEmpty(insetList)){
            res.setReturnCode("9999");
            return res;
        }
        doorChangeResBean.setInsetList(insetList);
        for (YcVerifaceDevice device:deviceList){
            String cacheKey = device.getDeviceSign()+"&"+System.currentTimeMillis()+"&"+Math.floor(Math.random()*1000);
            ycVerifaceDoorDevicePersonChangeList.put(cacheKey,doorChangeResBean);
            device.setCacheKey(cacheKey);
            toldDevicePeopleListHaveChange(device,Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_INSERT);
        }
        res.setReturnCode("0000");
        return res;
    }

    public DataReceiveResBean ycPhotoCheck(Boolean isProd,Visitor visitor){
        DataReceiveResBean res = new DataReceiveResBean();
        List<YcEnterBean> regist = new ArrayList<>();
        YcEnterBean ycEnterBean = new YcEnterBean();
        String userid = visitor.getVisitorName()+"MyTel"+visitor.getVisitorTel();
        ycEnterBean.setUserID(userid);
        //logger.info(visitor.getVisitorImg());
        ycEnterBean.setImg_base64(visitor.getVisitorImg().split(",")[1]);
        regist.add(ycEnterBean);
        String ycres = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.ADD_MEMBER, visitor.getSchoolId(), regist);
        YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(ycres, YcVerifaceResBean.class);
        List<Object> beans1 = ycVerifaceResBean.getBeans();
        YcVerifacePersonBean person = JSON.parseObject(beans1.get(0).toString(), YcVerifacePersonBean.class);
        if (person.getResult().equals("success")) {
            res.setReturnCode("0000");
            HashMap bean = new HashMap<String ,String>();
            bean.put("state","1");
            res.setBean(bean);
        }else if (person.getResult().equals("this user is already existed!") ){
            ycres = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.EDIT_MEMBER, visitor.getSchoolId(), regist);
            YcVerifaceResBean editYcVerifaceResBean = JSON.parseObject(ycres, YcVerifaceResBean.class);
            List<Object> beans2 = editYcVerifaceResBean.getBeans();
            YcVerifacePersonBean person1 = JSON.parseObject(beans2.get(0).toString(), YcVerifacePersonBean.class);
            if (person1.getResult().equals("success")) {
                res.setReturnCode("0000");
                HashMap bean = new HashMap<String ,String>();
                bean.put("state","1");
                res.setBean(bean);
            }else {
                res.setReturnCode("9999");
                HashMap bean = new HashMap<String ,String>();
                bean.put("state","-1");
                res.setBean(bean);
            }
        }else {
            res.setReturnCode("9999");
            HashMap bean = new HashMap<String ,String>();
            bean.put("state","-1");
            res.setBean(bean);
        }

        return res;
    }


    public YcVerifaceDoorChangeResBean ycVerifaceDoorPeoplePullFea(Boolean isProd,KqDeviceGroupPerson visitor){


        List<YcEnterBean> sendContent = new ArrayList<>();

            YcEnterBean bean = new YcEnterBean();
            bean.setUserID(visitor.getPersonId());
            sendContent.add(bean);

        //用personList获取特征值
        String res = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.ID_FEATURE,visitor.getSchoolId(), sendContent);
        YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(res, YcVerifaceResBean.class);
        if (!ycVerifaceResBean.getMessage().equals("turn id to feature success")){
            //System.out.println("获取特征值失败");
            return null;
        }
        List<Object> beans1 = ycVerifaceResBean.getBeans();
        List<YcVerifacePersonBean> resetList = new ArrayList<>();
        YcVerifacePersonBean personWithFea_128 = JSON.parseObject(beans1.get(0).toString(), YcVerifacePersonBean.class);
        if (personWithFea_128.getResult().equals("success")){
            KqDeviceGroupPerson  groupPerson1 = visitor;
            String personId = groupPerson1.getPersonId();
            String sex = "4";
            String userName = groupPerson1.getName();
            String personType = groupPerson1.getPersonType();
            if (!personId.equals("")){
                personId = personId +"#"+sex+"#"+userName+"#"+personType;
                personWithFea_128.setUserId(personId);
                personWithFea_128.setResult(null);
                resetList.add(personWithFea_128);
            }

        }else {
            logger.info("拉取算法服务器访客特征值失败");
        }

        YcVerifaceDoorChangeResBean changeResBean = new YcVerifaceDoorChangeResBean();
        changeResBean.setInsetList(resetList);
        return changeResBean;
    }



    public void toldDevicePeopleListHaveChange(YcVerifaceDevice device,String changeCode){
        YcVerifaceDevice u = new YcVerifaceDevice();
        u.setDeviceId(device.getDeviceSign());
        u.setChangeCode(changeCode);
        u.setCacheKey(device.getCacheKey());
        wsYcVerifaceFeign.toldDevicePeopleHaveChange(u);
    }
}
