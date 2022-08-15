package com.yice.edu.cn.tap.feignClient.dm.kq;

import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqSetting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "eccStudentKqSettingFeign",path = "/studentKqSetting")
public interface EccStudentKqSettingFeign {

    @PostMapping("/saveEccStudentKqSetting")
    EccStudentKqSetting saveEccStudentKqSetting(EccStudentKqSetting setting);

    @PostMapping("/updateEccStudentKqSetting")
    void updateEccStudentKqSetting(EccStudentKqSetting setting);

    @PostMapping("/findEccStudentKqSettingListByCondition")
    List<EccStudentKqSetting> findEccStudentKqSettingListByCondition(EccStudentKqSetting setting);

    @PostMapping("/currentKqSetting")
    EccStudentKqSetting currentKqSetting(EccStudentKqSetting setting);
}
