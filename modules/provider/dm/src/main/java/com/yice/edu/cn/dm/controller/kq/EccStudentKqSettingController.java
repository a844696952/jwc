package com.yice.edu.cn.dm.controller.kq;

import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqSetting;
import com.yice.edu.cn.common.pojo.dm.setting.EccGlobalSetting;
import com.yice.edu.cn.dm.service.kq.EccStudentKqSettingService;
import com.yice.edu.cn.dm.service.setting.EccGlobalSettingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengfengfeng
 */
@RequestMapping("/studentKqSetting")
@RestController
@Api(value = "/studentKqSetting",description = "云班牌考勤设置")
public class EccStudentKqSettingController {

    @Autowired
    private EccStudentKqSettingService eccStudentKqSettingService;

    @Autowired
    private EccGlobalSettingService eccGlobalSettingService;

    @PostMapping("/saveEccStudentKqSetting")
    public EccStudentKqSetting saveEccStudentKqSetting(@RequestBody EccStudentKqSetting setting){
        eccStudentKqSettingService.saveEccStudentKqSetting(setting);
        EccGlobalSetting globalSetting = buildEccGlobalSetting(setting);
        eccGlobalSettingService.putSetting(globalSetting);
        return setting;
    }

    private EccGlobalSetting buildEccGlobalSetting( EccStudentKqSetting setting) {
        EccGlobalSetting<EccStudentKqSetting> globalSetting = new EccGlobalSetting<>();
        globalSetting.setSchoolId(setting.getSchoolId());
        globalSetting.setKey("EccStudentKqSetting");
        globalSetting.setValue(setting);
        return globalSetting;
    }

    @PostMapping("/updateEccStudentKqSetting")
    public EccStudentKqSetting updateEccStudentKqSetting(@RequestBody EccStudentKqSetting setting){
        eccStudentKqSettingService.updateEccStudentKqSetting(setting);
        EccGlobalSetting globalSetting = buildEccGlobalSetting(setting);
        eccGlobalSettingService.putSetting(globalSetting);
        return setting;
    }

    @PostMapping("/findEccStudentKqSettingListByCondition")
    public List<EccStudentKqSetting> findEccStudentKqSettingListByCondition(@RequestBody EccStudentKqSetting setting){
        List<EccStudentKqSetting> list = eccStudentKqSettingService.findEccStudentKqSettingListByCondition(setting);
        return list;
    }

    @PostMapping("/currentKqSetting")
    public EccStudentKqSetting currentKqSetting(@RequestBody EccStudentKqSetting setting){
        EccGlobalSetting globalSetting = buildEccGlobalSetting(setting);
        EccGlobalSetting result = eccGlobalSettingService.getSetting(globalSetting);
        if(result!=null){
            Object value = result.getValue();
            if(value!=null ){
                return (EccStudentKqSetting)value;
            }
        }
        return null;
    }

}
