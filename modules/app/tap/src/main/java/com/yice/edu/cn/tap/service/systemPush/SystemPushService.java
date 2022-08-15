package com.yice.edu.cn.tap.service.systemPush;

import com.yice.edu.cn.common.pojo.jw.systemPush.SystemPush;
import com.yice.edu.cn.tap.feignClient.systemPush.SystemPushFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemPushService {
    @Autowired
    private SystemPushFeign systemPushFeign;

    public SystemPush findSystemPushById(String id) {
        return systemPushFeign.findSystemPushById(id);
    }

    public SystemPush saveSystemPush(SystemPush systemPush) {
        return systemPushFeign.saveSystemPush(systemPush);
    }

    public List<SystemPush> findSystemPushListByCondition(SystemPush systemPush) {
        return systemPushFeign.findSystemPushListByCondition(systemPush);
    }

    public SystemPush findOneSystemPushByCondition(SystemPush systemPush) {
        return systemPushFeign.findOneSystemPushByCondition(systemPush);
    }

    public long findSystemPushCountByCondition(SystemPush systemPush) {
        return systemPushFeign.findSystemPushCountByCondition(systemPush);
    }

    public void updateSystemPush(SystemPush systemPush) {
        systemPushFeign.updateSystemPush(systemPush);
    }

    public void deleteSystemPush(String id) {
        systemPushFeign.deleteSystemPush(id);
    }

    public void deleteSystemPushByCondition(SystemPush systemPush) {
        systemPushFeign.deleteSystemPushByCondition(systemPush);
    }
}
