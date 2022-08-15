package com.yice.edu.cn.tap.feignClient.stuLeaveManage;

import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.NotifyPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "notifyPersonFeign",path = "/notifyPerson")
public interface NotifyPersonFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findNotifyPersonById/{id}")
    NotifyPerson findNotifyPersonById(@PathVariable("id") String id);
    @PostMapping("/saveNotifyPerson")
    NotifyPerson saveNotifyPerson(NotifyPerson notifyPerson);
    @PostMapping("/batchSaveNotifyPerson")
    void batchSaveNotifyPerson(List<NotifyPerson> notifyPersons);
    @PostMapping("/findNotifyPersonListByCondition")
    List<NotifyPerson> findNotifyPersonListByCondition(NotifyPerson notifyPerson);
    @PostMapping("/findOneNotifyPersonByCondition")
    NotifyPerson findOneNotifyPersonByCondition(NotifyPerson notifyPerson);
    @PostMapping("/findNotifyPersonCountByCondition")
    long findNotifyPersonCountByCondition(NotifyPerson notifyPerson);
    @PostMapping("/updateNotifyPerson")
    void updateNotifyPerson(NotifyPerson notifyPerson);
    @PostMapping("/updateNotifyPersonForAll")
    void updateNotifyPersonForAll(NotifyPerson notifyPerson);
    @GetMapping("/deleteNotifyPerson/{id}")
    void deleteNotifyPerson(@PathVariable("id") String id);
    @PostMapping("/deleteNotifyPersonByCondition")
    void deleteNotifyPersonByCondition(NotifyPerson notifyPerson);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
