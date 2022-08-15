package com.yice.edu.cn.osp.service.dm.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.NotificationMode;
import com.yice.edu.cn.osp.feignClient.dm.modeManage.NotificationModeFeign;
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

    public int saveNotificationMode(NotificationMode notificationMode) {
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

    public int updateNotificationMode(NotificationMode notificationMode) {
        return notificationModeFeign.updateNotificationMode(notificationMode);
    }

    public void deleteNotificationMode(String id) {
        notificationModeFeign.deleteNotificationMode(id);
    }

    public void deleteNotificationModeByCondition(NotificationMode notificationMode) {
        notificationModeFeign.deleteNotificationModeByCondition(notificationMode);
    }

    public void closeNotificationMode(String id) {
        notificationModeFeign.closeNotificationMode(id);
    }
}
