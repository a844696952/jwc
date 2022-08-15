package com.yice.edu.cn.ecc.service.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.NotificationMode;
import com.yice.edu.cn.ecc.feignClient.modeManage.NotificationModeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationModeService {
    @Autowired
    private NotificationModeFeign notificationModeFeign;

    public NotificationMode findNotificationModeById(String id) {
        return notificationModeFeign.findNotificationModeById(id);
    }

    public NotificationMode saveNotificationMode(NotificationMode notificationMode) {
        return notificationModeFeign.saveNotificationMode(notificationMode);
    }

    public List<NotificationMode> findNotificationModeListByCondition(NotificationMode notificationMode) {
        return notificationModeFeign.findNotificationModeListByCondition(notificationMode);
    }

    public NotificationMode findOneNotificationModeByCondition(NotificationMode notificationMode) {
        return notificationModeFeign.findOneNotificationModeByCondition(notificationMode);
    }

    public long findNotificationModeCountByCondition(NotificationMode notificationMode) {
        return notificationModeFeign.findNotificationModeCountByCondition(notificationMode);
    }

    public void updateNotificationMode(NotificationMode notificationMode) {
        notificationModeFeign.updateNotificationMode(notificationMode);
    }

    public void deleteNotificationMode(String id) {
        notificationModeFeign.deleteNotificationMode(id);
    }

    public void deleteNotificationModeByCondition(NotificationMode notificationMode) {
        notificationModeFeign.deleteNotificationModeByCondition(notificationMode);
    }

    public List<NotificationMode> findNotificationModeByNowTime(String schoolId) {
        return notificationModeFeign.findNotificationModeByNowTime(schoolId);
    }
}
