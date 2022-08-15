package com.yice.edu.cn.tap.service.ycVeriface;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.kqDevice.KqDeviceGroupPerson;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.account.YcVerifaceAccount;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.device.YcVerifaceDevice;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcEnterBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceDoorChangeResBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifacePersonBean;
import com.yice.edu.cn.common.pojo.dm.ycVeriface.reqResBean.YcVerifaceResBean;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.util.ycVerifaceSender.YcVerifaceSender;
import com.yice.edu.cn.tap.feignClient.ycveriface.YcVerifaceDeviceFeign;
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
@Service
public class YcVerifaceDeviceService {
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;
    @Autowired
    private YcVerifaceDeviceFeign ycVerifaceDeviceFeign;

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

    public List<YcVerifaceDevice> findSchoolInAndOutDevice(String mySchoolId) {
      return   ycVerifaceDeviceFeign.findSchoolInAndOutDevice(mySchoolId);
    }
}
