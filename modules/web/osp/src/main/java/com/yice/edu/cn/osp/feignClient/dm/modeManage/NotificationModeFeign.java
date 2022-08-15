package com.yice.edu.cn.osp.feignClient.dm.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.NotificationMode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "notificationModeFeign",path = "/notificationMode")
public interface NotificationModeFeign {
    @GetMapping("/findNotificationModeById/{id}")
    NotificationMode findNotificationModeById(@PathVariable("id") String id);
    @PostMapping("/saveNotificationMode")
    int saveNotificationMode(NotificationMode notificationMode);
    @PostMapping("/findNotificationModeListByCondition")
    List<NotificationMode> findNotificationModeListByCondition(NotificationMode notificationMode);
    @PostMapping("/findOneNotificationModeByCondition")
    NotificationMode findOneNotificationModeByCondition(NotificationMode notificationMode);
    @PostMapping("/findNotificationModeCountByCondition")
    long findNotificationModeCountByCondition(NotificationMode notificationMode);
    @PostMapping("/updateNotificationMode")
    int updateNotificationMode(NotificationMode notificationMode);
    @GetMapping("/deleteNotificationMode/{id}")
    void deleteNotificationMode(@PathVariable("id") String id);
    @PostMapping("/deleteNotificationModeByCondition")
    void deleteNotificationModeByCondition(NotificationMode notificationMode);

    @GetMapping("/closeNotificationMode/{id}")
    void closeNotificationMode(@PathVariable("id") String id);
}
