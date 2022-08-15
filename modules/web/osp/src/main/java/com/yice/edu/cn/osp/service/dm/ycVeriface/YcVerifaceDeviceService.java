package com.yice.edu.cn.osp.service.dm.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.*;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.ycVerifaceSender.YcVerifaceSender;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceAccountFeign;
import com.yice.edu.cn.osp.feignClient.dm.ycVeriface.YcVerifaceDeviceFeign;
import com.yice.edu.cn.osp.feignClient.dm.zyDevice.KqDeviceGroupPersonFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.feignClient.ws.WsYcVerifaceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class YcVerifaceDeviceService {
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;
    @Autowired
    private YcVerifaceDeviceFeign ycVerifaceDeviceFeign;
    @Autowired
    private YcVerifaceAccountFeign ycVerifaceAccountFeign;
    @Autowired
    private YcVerifaceAccountService ycVerifaceAccountService;
    @Autowired
    private WsYcVerifaceFeign wsYcVerifaceFeign;
    @Autowired
    private KqDeviceGroupPersonFeign kqDeviceGroupPersonFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private StudentFeign studentFeign;

    private final static Logger logger = LoggerFactory.getLogger(YcVerifaceDeviceService.class);


    @CreateCache(name = Constant.Redis.YC_VERIFACE_DOOR_DEVICE_PERSON_CHANGE_LIST, expire = Constant.Redis.YC_VERIFACE_DOOR_DEVICE_PERSON_CHANGE_LIST_TIMEOUT, timeUnit = TimeUnit.DAYS)
    private Cache<String, YcVerifaceDoorChangeResBean> ycVerifaceDoorDevicePersonChangeList;//key:设备id，value：变化列表

    @CreateCache(name = Constant.Redis.YC_VERIFACE_ONLINE_DOOR_DEVICE_LIST )
    private Cache<String,String> ycVerifaceOnlineDoorDeviceList;//key:设备id，value：变化列表

    @CreateCache(name = Constant.Redis.YC_VERIFACE_ONLINE_DOOR_DEVICE_RESET_FAILED_NUM, expire = Constant.Redis.YC_VERIFACE_ONLINE_DOOR_DEVICE_RESET_FAILED_NUM_TIMEOUT, timeUnit = TimeUnit.MINUTES)
    private Cache<String,Integer> ycVerifaceOnlineDoorDeviceResetFailedNum;//key:设备id，value：失败次数


    public YcVerifaceDevice findYcVerifaceDeviceById(String id) {
        return ycVerifaceDeviceFeign.findYcVerifaceDeviceById(id);
    }

    public YcVerifaceDevice saveYcVerifaceDevice(YcVerifaceDevice ycVerifaceDevice) {
        return ycVerifaceDeviceFeign.saveYcVerifaceDevice(ycVerifaceDevice);
    }

    public List<YcVerifaceDevice> findYcVerifaceDeviceListByCondition(YcVerifaceDevice ycVerifaceDevice) {
        return ycVerifaceDeviceFeign.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
    }

    public YcVerifaceDevice findOneYcVerifaceDeviceByCondition(YcVerifaceDevice ycVerifaceDevice) {
        return ycVerifaceDeviceFeign.findOneYcVerifaceDeviceByCondition(ycVerifaceDevice);
    }

    public long findYcVerifaceDeviceCountByCondition(YcVerifaceDevice ycVerifaceDevice) {
        return ycVerifaceDeviceFeign.findYcVerifaceDeviceCountByCondition(ycVerifaceDevice);
    }

    public void updateYcVerifaceDevice(YcVerifaceDevice ycVerifaceDevice) {
        ycVerifaceDeviceFeign.updateYcVerifaceDevice(ycVerifaceDevice);
    }

    public void deleteYcVerifaceDevice(String id) {
        ycVerifaceDeviceFeign.deleteYcVerifaceDevice(id);
    }

    public void deleteYcVerifaceDeviceByCondition(YcVerifaceDevice ycVerifaceDevice) {
        ycVerifaceDeviceFeign.deleteYcVerifaceDeviceByCondition(ycVerifaceDevice);
    }
/*------------------------------------------------------------------------------------------------------------*/
    public List<YcVerifaceAccount> findYcVerifaceCameraDevicesWithAccount() {
        YcVerifaceAccount ycVerifaceAccount = new YcVerifaceAccount();
        ycVerifaceAccount.setSchoolId(mySchoolId());
        ycVerifaceAccount.setType("0");
        List<YcVerifaceAccount> ycVerifaceAccountListByCondition = ycVerifaceAccountFeign.findYcVerifaceAccountListByCondition(ycVerifaceAccount);
        YcVerifaceDevice ycVerifaceDevice = new YcVerifaceDevice();
        ycVerifaceDevice.setSchoolId(mySchoolId());
        ycVerifaceDevice.setType("0");
        List<YcVerifaceDevice> ycVerifaceDeviceListByCondition = ycVerifaceDeviceFeign.findYcVerifaceDeviceListByCondition(ycVerifaceDevice);
        if (CollectionUtil.isEmpty(ycVerifaceDeviceListByCondition)){
            for (YcVerifaceAccount account:ycVerifaceAccountListByCondition){
                account.setLevel(1);
            }
            return ycVerifaceAccountListByCondition;
        }
        /*id: "1", name: "教师" },{ id: "2", name: "学生" },
                            { id: "3", name: "其他职工"},{ id: "5", name: "访客" },
                            { id: "-1", name: "陌生人" },{ id: "0", name: "黑名单"*/
       for (YcVerifaceDevice d:ycVerifaceDeviceListByCondition){
            List<String> capturePersonTypeList = d.getCapturePersonTypeList();
            String type = "";
            if (CollectionUtil.isNotEmpty(capturePersonTypeList)){
                for(String t:capturePersonTypeList){
                    switch (t){
                        case "1":type=type+"教师、";break;
                        case "2":type=type+"学生、";break;
                        case "3":type=type+"其他职工、";break;
                        case "5":type=type+"访客、";break;
                        case "-1":type=type+"陌生人、";break;
                        case "0":type=type+"黑名单、";break;
                    }
                }
            }
            if (type.length()>0){
                type =  type.substring(0,type.length()-1);
            }
           d.setCapturePersonTypeListShow(type);
        }
        //查找缓存中的心跳设备
        Map<String, YcVerifaceAccount> heartbeatAccountMap = ycVerifaceAccountService.findHeartbeatAccount(ycVerifaceAccountListByCondition);
        for (YcVerifaceAccount account:ycVerifaceAccountListByCondition){
            account.setLevel(1);
            YcVerifaceAccount heartbeatAcc = heartbeatAccountMap.get(account.getId());
            if (heartbeatAcc==null){
                account.setConnectStatus(0);
                heartbeatAcc = new YcVerifaceAccount();
                heartbeatAcc.setChildren(new ArrayList<>());
            }else {
                account.setConnectStatus(1);
                if (account.getChildren()==null){
                    account.setChildren(new ArrayList<>());
                }
            }
            List<YcVerifaceDevice> heartbeatChildren = heartbeatAcc.getChildren();
            List<YcVerifaceDevice> child = new ArrayList<>();
            for (YcVerifaceDevice device:ycVerifaceDeviceListByCondition){
                boolean equals = device.getAccountId().equals(account.getId());
                if (equals){
                    device.setLevel(2);
                    device.setDeviceStatus("-1");
                    if (CollectionUtil.isNotEmpty(heartbeatChildren)){
                        for (YcVerifaceDevice y:heartbeatChildren){
                            if (y.getDeviceId().equals(device.getId())){
                                device.setDeviceStatus(y.getDeviceStatus());
                            }
                        }
                    }
                    child.add(device);
                }
            }
            if (CollectionUtil.isNotEmpty(child)){
                account.setChildren(child);
            }
        }
        return ycVerifaceAccountListByCondition;
    }



    public void toldDevicePeopleListHaveChange(YcVerifaceDevice device,String changeCode){
        YcVerifaceDevice u = new YcVerifaceDevice();
        u.setDeviceId(device.getDeviceSign()/*myId()*/);
        u.setChangeCode(changeCode);
        u.setCacheKey(device.getCacheKey());
        wsYcVerifaceFeign.toldDevicePeopleHaveChange(u);
    }

    public YcVerifaceDoorChangeResBean ycVerifaceDoorPullPeopleChange(YcVerifaceDevice ycVerifaceDevice) {
        String changeCode = ycVerifaceDevice.getChangeCode();
        YcVerifaceDoorChangeResBean doorChangeResBean = null;
        switch (changeCode){
            case Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_RESET:
                doorChangeResBean = ycVerifaceDoorPeoplePullFea(ycVerifaceDevice);
                break;
            case Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_INSERT:
                doorChangeResBean = ycVerifaceDoorPeoplePullFeaForChange(ycVerifaceDevice);
                break;
            case Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_DELETE:
                doorChangeResBean = ycVerifaceDoorPeoplePullFeaForChange(ycVerifaceDevice);
                break;
            case Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_UPDATE:
                doorChangeResBean = ycVerifaceDoorPeoplePullFeaForChange(ycVerifaceDevice);
                break;
        }

        return doorChangeResBean;
    }

    private YcVerifaceDoorChangeResBean ycVerifaceDoorPeoplePullFeaForChange(YcVerifaceDevice ycVerifaceDevice) {

        YcVerifaceDoorChangeResBean ycVerifaceDoorChangeResBean =
                ycVerifaceDoorDevicePersonChangeList.get(ycVerifaceDevice.getCacheKey())==null?new YcVerifaceDoorChangeResBean(): ycVerifaceDoorDevicePersonChangeList.get(ycVerifaceDevice.getCacheKey());
        return ycVerifaceDoorChangeResBean;
    }

    public YcVerifaceDoorChangeResBean ycVerifaceDoorPeoplePullFea(YcVerifaceDevice device){
            String deviceGroupId = device.getDeviceGroupId();
            if (StrUtil.isEmpty(deviceGroupId)) {
                return new YcVerifaceDoorChangeResBean();
            }
            KqDeviceGroupPerson kqDeviceGroupPerson = new KqDeviceGroupPerson();
            kqDeviceGroupPerson.setDeviceGroupId(deviceGroupId);
            kqDeviceGroupPerson.setFactoryType("0");
            kqDeviceGroupPerson.setSchoolId(device.getSchoolId());
            List<KqDeviceGroupPerson> personList =CollectionUtil.isNotEmpty(device.getToGetfeasList() )?device.getToGetfeasList() :kqDeviceGroupPersonFeign.findKqDeviceGroupPersonListByCondition(kqDeviceGroupPerson);
            if(CollectionUtil.isEmpty(personList)){
                return new YcVerifaceDoorChangeResBean();
            }
            List<String> ids = personList.stream().map(KqDeviceGroupPerson::getPersonId).collect(Collectors.toList());
            List<YcEnterBean> sendContent = new ArrayList<>();
            for (String id : ids){
                YcEnterBean bean = new YcEnterBean();
                bean.setUserID(id);
                sendContent.add(bean);
            }
            //用personList获取特征值
            String res = YcVerifaceSender.postRequest(isProd, YcVerifaceSender.ID_FEATURE,device.getSchoolId(), sendContent);
            YcVerifaceResBean ycVerifaceResBean = JSON.parseObject(res, YcVerifaceResBean.class);
            if (!ycVerifaceResBean.getMessage().equals("turn id to feature success")){
                //System.out.println("获取特征值失败");
                return null;
            }
            List<Object> beans1 = ycVerifaceResBean.getBeans();
            List<YcVerifacePersonBean> resetList = new ArrayList<>();
            for (Object o: beans1){
                YcVerifacePersonBean personWithFea_128 = JSON.parseObject(o.toString(), YcVerifacePersonBean.class);
                if (personWithFea_128.getResult().equals("success")){
                    KqDeviceGroupPerson  groupPerson1 = personList.stream().filter(k -> k.getPersonId().equals(personWithFea_128.getUserId())).findFirst().get();
                    String personType = groupPerson1.getPersonType();
                    String personId = groupPerson1.getPersonId();
                    String sex = "";
                    String userName = "";
                    String userType = "";
                    switch (personType){
                        case Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER:
                            Teacher teacherById = teacherFeign.findTeacherById(personId);
                            if (teacherById!=null){
                                sex=teacherById.getSex()!=null?teacherById.getSex():"4";
                                userName=teacherById.getName();
                                userType = Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_TEACHER;
                            }
                            break;
                        case Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT:
                            Student studentById = studentFeign.findStudentById(personId);
                           if (studentById!=null){
                               sex=studentById.getSex()!=null?studentById.getSex():"4";
                               userName=studentById.getName();
                               userType = Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STUDENT;
                           }
                            break;
                        case Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF:
                            Teacher staff = teacherFeign.findTeacherById(personId);
                           if (staff!=null){
                               sex=staff.getSex()!=null?staff.getSex():"4";
                               userName=staff.getName();
                               userType = Constant.YC_VERIFACE_PERSON_ENTER.PERSON_TYPE_STAFF;
                           }
                           break;
                    }
                    if (!sex.equals("")){
                        personId = personId +"#"+sex+"#"+userName+"#"+userType;
                        personWithFea_128.setUserId(personId);
                        personWithFea_128.setResult(null);
                        resetList.add(personWithFea_128);
                    }

                }
            }
            YcVerifaceDoorChangeResBean changeResBean = new YcVerifaceDoorChangeResBean();
            changeResBean.setResetList(resetList);
            return changeResBean;
    }


    public void checkPullResultAndAdjustCache(YcVerifaceDevice ycVerifaceDevice) {
        String cacheKey = ycVerifaceDevice.getCacheKey();
        String pullResult = ycVerifaceDevice.getPullResult();
        if (pullResult.equals("success")){
            YcVerifaceDoorChangeResBean cacheData = ycVerifaceDoorDevicePersonChangeList.get(cacheKey);
            if (cacheData!=null){
                ycVerifaceDoorDevicePersonChangeList.remove(cacheKey);
            }
            logger.info(cacheKey+"操作执行成功");
        }else if (pullResult.equals("failed")){
            String deviceSign = ycVerifaceDevice.getDeviceId();
            ycVerifaceDevice.setDeviceSign(deviceSign);
            ycVerifaceDevice.setCacheKey("reset");
            Integer failedNum = ycVerifaceOnlineDoorDeviceResetFailedNum.get(deviceSign)==null?1:ycVerifaceOnlineDoorDeviceResetFailedNum.get(ycVerifaceDevice.getDeviceId());
            if (failedNum<=5){
                logger.info(deviceSign+"第"+failedNum+"次操作执行失败");
                toldDevicePeopleListHaveChange(ycVerifaceDevice,Constant.YC_VERIFACE_TOLD_DEVICE_CHANGE_CODE.PERSON_LIST_RESET);
                failedNum=failedNum+1;
                ycVerifaceOnlineDoorDeviceResetFailedNum.put(deviceSign,failedNum);
            }else {
                logger.info(deviceSign+"重置操作连续执行失败次数超过5次,设备故障！！！");
            }
        }
    }

    public List<YcVerifaceDevice> findAndChangeDoorStatus(List<YcVerifaceDevice> data) {
        Set<String> singnList = data.stream().filter(ycVerifaceDevice -> StrUtil.isNotEmpty(ycVerifaceDevice.getDeviceSign())).map(YcVerifaceDevice::getDeviceSign).collect(Collectors.toSet());
        System.out.println(singnList);
        Map<String, String> allOnline = ycVerifaceOnlineDoorDeviceList.getAll(singnList);
        System.out.println(allOnline);
        for (YcVerifaceDevice ycVerifaceDevice:data){
            String deviceSign = ycVerifaceDevice.getDeviceSign();
            if (StrUtil.isNotEmpty(deviceSign)){
                String s = allOnline.get(deviceSign);
                if (StrUtil.isNotEmpty(s)){
                    ycVerifaceDevice.setDeviceStatus("1");
                }else {
                    ycVerifaceDevice.setDeviceStatus("0");
                }
            }else {
                ycVerifaceDevice.setDeviceStatus("-1");
            }
        }

        return data;
    }
}
