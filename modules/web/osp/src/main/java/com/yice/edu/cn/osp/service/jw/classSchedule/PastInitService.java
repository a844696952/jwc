package com.yice.edu.cn.osp.service.jw.classSchedule;

import com.yice.edu.cn.common.pojo.jw.classSchedule.PastInit;
import com.yice.edu.cn.osp.feignClient.jw.classSchedule.PastInitFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastInitService {
    @Autowired
    private PastInitFeign pastInitFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public PastInit findPastInitById(String id) {
        return pastInitFeign.findPastInitById(id);
    }

    public PastInit savePastInit(PastInit pastInit) {
        return pastInitFeign.savePastInit(pastInit);
    }

    public void batchSavePastInit(List<PastInit> pastInits){
        pastInitFeign.batchSavePastInit(pastInits);
    }

    public List<PastInit> findPastInitListByCondition(PastInit pastInit) {
        return pastInitFeign.findPastInitListByCondition(pastInit);
    }

    public PastInit findOnePastInitByCondition(PastInit pastInit) {
        return pastInitFeign.findOnePastInitByCondition(pastInit);
    }

    public long findPastInitCountByCondition(PastInit pastInit) {
        return pastInitFeign.findPastInitCountByCondition(pastInit);
    }

    public void updatePastInit(PastInit pastInit) {
        pastInitFeign.updatePastInit(pastInit);
    }

    public void updatePastInitForAll(PastInit pastInit) {
        pastInitFeign.updatePastInitForAll(pastInit);
    }

    public void deletePastInit(String id) {
        pastInitFeign.deletePastInit(id);
    }

    public void deletePastInitByCondition(PastInit pastInit) {
        pastInitFeign.deletePastInitByCondition(pastInit);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
