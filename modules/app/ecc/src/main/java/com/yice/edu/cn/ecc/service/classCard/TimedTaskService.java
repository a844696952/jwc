package com.yice.edu.cn.ecc.service.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import com.yice.edu.cn.ecc.feignClient.classCard.TimedTaskFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimedTaskService {
    @Autowired
    private TimedTaskFeign dmTimedTaskFeign;

    public List<DmTimedTask> findDmTimedTaskListByCondition(DmTimedTask dmTimedTask) {
        return dmTimedTaskFeign.findDmTimedTaskListByCondition(dmTimedTask);
    }


}
