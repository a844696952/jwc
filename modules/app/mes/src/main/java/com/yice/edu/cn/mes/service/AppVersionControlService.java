package com.yice.edu.cn.mes.service;

import com.yice.edu.cn.common.pojo.jw.appVersionControl.AppVersionControl;
import com.yice.edu.cn.mes.feign.AppVersionControlFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppVersionControlService {
    @Autowired
    private AppVersionControlFeign appVersionControlFeign;

    public AppVersionControl findVersionControlUptodate(AppVersionControl appVersionControl) {
        return appVersionControlFeign.findVersionControlUptodate(appVersionControl);
    }

}
