package com.yice.edu.cn.osp.controller.dm.kq;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqSetting;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.osp.service.dm.kq.EccStudentKqSettingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

/**
 * @author dengfengfeng
 */
@RequestMapping("/studentKqSetting")
@RestController
@Api(value = "/studentKqSetting",description = "云班牌考勤设置")
public class EccStudentKqSettingController {

    @Autowired
    private EccStudentKqSettingService eccStudentKqSettingService;

    @PostMapping("/save")
    public ResponseJson save(@RequestBody @Validated(GroupOne.class) EccStudentKqSetting setting){
        setting.setSchoolId(mySchoolId());
        EccStudentKqSetting result = eccStudentKqSettingService.saveEccStudentKqSetting(setting);
        EccStudentKqSetting cache = eccStudentKqSettingService.cacheKqSetting(mySchoolId());
        return new ResponseJson(result,cache);
    }

    @PostMapping("/update")
    public ResponseJson update(@RequestBody @Validated(GroupOne.class) EccStudentKqSetting setting){
        setting.setSchoolId(mySchoolId());
        eccStudentKqSettingService.updateEccStudentKqSetting(setting);
        return new ResponseJson(true,"修改成功");
    }

    @PostMapping("/list")
    public ResponseJson get(@RequestBody EccStudentKqSetting setting){
        setting.setSchoolId(mySchoolId());
        List<EccStudentKqSetting> list = eccStudentKqSettingService.findEccStudentKqSettingListByCondition(setting);
        return new ResponseJson(list);
    }

    @GetMapping("/currentAndCacheKqSetting")
    public ResponseJson currentKqSetting(){
        EccStudentKqSetting setting = new EccStudentKqSetting();
        setting.setSchoolId(mySchoolId());
        EccStudentKqSetting result = eccStudentKqSettingService.currentKqSetting(setting);
        EccStudentKqSetting cache = eccStudentKqSettingService.cacheKqSetting(mySchoolId());
        if(result==null){
            return new ResponseJson(false,"考勤未设置");
        }
        return new ResponseJson(result,cache);
    }

    @GetMapping("/cacheKqSetting")
    public ResponseJson cacheKqSetting(){
        EccStudentKqSetting result = eccStudentKqSettingService.cacheKqSetting(mySchoolId());

        return new ResponseJson(result);
    }

    @PostMapping("/saveAndRefresh")
    public ResponseJson saveAndRefresh(@RequestBody @Validated(GroupOne.class) EccStudentKqSetting setting){
        setting.setSchoolId(mySchoolId());
        EccStudentKqSetting result = eccStudentKqSettingService.saveEccStudentKqSetting(setting);
        eccStudentKqSettingService.refresh(result);
        return new ResponseJson(result,result);
    }
}
