package com.yice.edu.cn.osp.service.xw.stuLeaveManage;

import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.NotifyPerson;
import com.yice.edu.cn.osp.feignClient.xw.stuLeaveManage.NotifyPersonFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyPersonService {
    @Autowired
    private NotifyPersonFeign notifyPersonFeign;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public NotifyPerson findNotifyPersonById(String id) {
        return notifyPersonFeign.findNotifyPersonById(id);
    }

    public NotifyPerson saveNotifyPerson(NotifyPerson notifyPerson) {
        return notifyPersonFeign.saveNotifyPerson(notifyPerson);
    }

    public void batchSaveNotifyPerson(List<NotifyPerson> notifyPersons) {
        notifyPersonFeign.batchSaveNotifyPerson(notifyPersons);
    }

    public List<NotifyPerson> findNotifyPersonListByCondition(NotifyPerson notifyPerson) {
        return notifyPersonFeign.findNotifyPersonListByCondition(notifyPerson);
    }

    public NotifyPerson findOneNotifyPersonByCondition(NotifyPerson notifyPerson) {
        return notifyPersonFeign.findOneNotifyPersonByCondition(notifyPerson);
    }

    public long findNotifyPersonCountByCondition(NotifyPerson notifyPerson) {
        return notifyPersonFeign.findNotifyPersonCountByCondition(notifyPerson);
    }

    public void updateNotifyPerson(NotifyPerson notifyPerson) {
        notifyPersonFeign.updateNotifyPerson(notifyPerson);
    }

    public void updateNotifyPersonForAll(NotifyPerson notifyPerson) {
        notifyPersonFeign.updateNotifyPersonForAll(notifyPerson);
    }

    public void deleteNotifyPerson(String id) {
        notifyPersonFeign.deleteNotifyPerson(id);
    }

    public void deleteNotifyPersonByCondition(NotifyPerson notifyPerson) {
        notifyPersonFeign.deleteNotifyPersonByCondition(notifyPerson);
    }

    public void batchSaveNotifyPerson(NotifyPerson notifyPerson) {
        notifyPersonFeign.batchSaveNotifyPerson(notifyPerson);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
