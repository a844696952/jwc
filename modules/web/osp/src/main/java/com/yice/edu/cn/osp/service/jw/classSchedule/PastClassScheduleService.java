package com.yice.edu.cn.osp.service.jw.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.PastClassSchedule;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.PastClassScheduleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastClassScheduleService {
    @Autowired
    private PastClassScheduleFeign pastClassScheduleFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PastClassSchedule findPastClassScheduleById(String id) {
        return pastClassScheduleFeign.findPastClassScheduleById(id);
    }

    public PastClassSchedule savePastClassSchedule(PastClassSchedule pastClassSchedule) {
        return pastClassScheduleFeign.savePastClassSchedule(pastClassSchedule);
    }

    public void batchSavePastClassSchedule(List<PastClassSchedule> pastClassSchedules){
        pastClassScheduleFeign.batchSavePastClassSchedule(pastClassSchedules);
    }

    public List<PastClassSchedule> findPastClassScheduleListByCondition(PastClassSchedule pastClassSchedule) {
        return pastClassScheduleFeign.findPastClassScheduleListByCondition(pastClassSchedule);
    }

    public PastClassSchedule findOnePastClassScheduleByCondition(PastClassSchedule pastClassSchedule) {
        return pastClassScheduleFeign.findOnePastClassScheduleByCondition(pastClassSchedule);
    }

    public long findPastClassScheduleCountByCondition(PastClassSchedule pastClassSchedule) {
        return pastClassScheduleFeign.findPastClassScheduleCountByCondition(pastClassSchedule);
    }

    public void updatePastClassSchedule(PastClassSchedule pastClassSchedule) {
        pastClassScheduleFeign.updatePastClassSchedule(pastClassSchedule);
    }

    public void updatePastClassScheduleForAll(PastClassSchedule pastClassSchedule) {
        pastClassScheduleFeign.updatePastClassScheduleForAll(pastClassSchedule);
    }

    public void deletePastClassSchedule(String id) {
        pastClassScheduleFeign.deletePastClassSchedule(id);
    }

    public void deletePastClassScheduleByCondition(PastClassSchedule pastClassSchedule) {
        pastClassScheduleFeign.deletePastClassScheduleByCondition(pastClassSchedule);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<PastClassSchedule> findListPastClassScheduleByCountKong(PastClassSchedule pastClassSchedule){
        return pastClassScheduleFeign.findListPastClassScheduleByCountKong(pastClassSchedule);
    }
}
