package com.yice.edu.cn.ecc.controller.kq;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentKqSetting;
import com.yice.edu.cn.ecc.service.kq.EccStudentKqSettingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.mySchoolId;

/**
 * @author dengfengfeng
 */
@RequestMapping("/studentKqSetting")
@RestController
@Api(value = "/studentKqSetting",description = "云班牌考勤设置")
public class EccStudentKqSettingController {

    @Autowired
    private EccStudentKqSettingService eccStudentKqSettingService;


    @GetMapping("/currentKqSetting")
    public ResponseJson currentKqSetting(){
        EccStudentKqSetting setting = new EccStudentKqSetting();
        setting.setSchoolId(mySchoolId());
        EccStudentKqSetting result = eccStudentKqSettingService.currentKqSetting(setting);
        if(result==null){
            return new ResponseJson(false,"考勤未设置");
        }
        return new ResponseJson(result);
    }
}
