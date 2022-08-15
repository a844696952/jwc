package com.yice.edu.cn.osp.controller.tab;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmIot.DmIot;
import com.yice.edu.cn.osp.service.dm.dmIot.DmIotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RequestMapping("/tab")
@RestController
public class TabController {
    @Autowired
    private DmIotService dmIotService;

    /**
     * https://218.6.69.201:8012/iotp/web/WebHome/login?key=94b292ce645b465111f32bc831ebb39a&username=admin
     * @return
     */
    @GetMapping("/toSundrayIndex")
    public ResponseJson toSundrayIndex(){
        DmIot dmIot = new DmIot();
        dmIot.setSchoolId(mySchoolId());
        DmIot exist = dmIotService.findOneDmIotByCondition(dmIot);
        if(exist!=null){
            String url="https://"+exist.getIp()+":"+exist.getPort()+"/iotp/web/WebHome/login?key="+ DigestUtil.md5Hex(DateUtil.format(new Date(),"yyyyMMdd")+exist.getRows()+"sundray")+"&username="
                    +(currentTeacher().getStatus().equals("99")?exist.getAccountNumber():exist.getOrdinaryAccount());
            System.out.println(url);
            return new ResponseJson(url);
        }
        return new ResponseJson(false,"设备入校里的物联网未配置");
    }
}
