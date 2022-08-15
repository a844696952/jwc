package com.yice.edu.cn.osp.service.dm.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import com.yice.edu.cn.osp.feignClient.dm.classCard.DmTimedTaskFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmTimedTaskService {
    @Autowired
    private DmTimedTaskFeign dmTimedTaskFeign;

    public DmTimedTask findDmTimedTaskById(String id) {
        return dmTimedTaskFeign.findDmTimedTaskById(id);
    }

    public DmTimedTask saveOrUpdateDmTimedTaskAll(DmTimedTask dmTimedTask) {
        return dmTimedTaskFeign.saveOrUpdateDmTimedTaskAll(dmTimedTask);
    }

    public DmTimedTask saveOrUpdateDmTimedTask(DmTimedTask dmTimedTask) {
        return dmTimedTaskFeign.saveOrUpdateDmTimedTask(dmTimedTask);
    }

    public List<DmTimedTask> findDmTimedTaskListByCondition(DmTimedTask dmTimedTask) {
        return dmTimedTaskFeign.findDmTimedTaskListByCondition(dmTimedTask);
    }

    public DmTimedTask findOneDmTimedTaskByCondition(DmTimedTask dmTimedTask) {
        return dmTimedTaskFeign.findOneDmTimedTaskByCondition(dmTimedTask);
    }

    public long findDmTimedTaskCountByCondition(DmTimedTask dmTimedTask) {
        return dmTimedTaskFeign.findDmTimedTaskCountByCondition(dmTimedTask);
    }

    public void updateDmTimedTask(DmTimedTask dmTimedTask) {
        dmTimedTaskFeign.updateDmTimedTask(dmTimedTask);
    }

    public void deleteDmTimedTask(String id) {
        dmTimedTaskFeign.deleteDmTimedTask(id);
    }

    public void deleteDmTimedTaskByCondition(DmTimedTask dmTimedTask) {
        dmTimedTaskFeign.deleteDmTimedTaskByCondition(dmTimedTask);
    }

    public DmTimedTask informVersion(DmTimedTask dmTimedTask) {

        return dmTimedTaskFeign.informVersion(dmTimedTask);
    }
}
