package com.yice.edu.cn.jw.service.classSchedule;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.classSchedule.PastInit;
import com.yice.edu.cn.jw.dao.classSchedule.PastInitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PastInitService {
    @Autowired
    private PastInitDao pastInitDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public PastInit findPastInitById(String id) {
        return pastInitDao.findPastInitById(id);
    }
    @Transactional
    public void savePastInit(PastInit pastInit) {
        pastInit.setId(sequenceId.nextId());
        pastInitDao.savePastInit(pastInit);
    }
    @Transactional(readOnly = true)
    public List<PastInit> findPastInitListByCondition(PastInit pastInit) {
        return pastInitDao.findPastInitListByCondition(pastInit);
    }
    @Transactional(readOnly = true)
    public PastInit findOnePastInitByCondition(PastInit pastInit) {
        return pastInitDao.findOnePastInitByCondition(pastInit);
    }
    @Transactional(readOnly = true)
    public long findPastInitCountByCondition(PastInit pastInit) {
        return pastInitDao.findPastInitCountByCondition(pastInit);
    }
    @Transactional
    public void updatePastInit(PastInit pastInit) {
        pastInitDao.updatePastInit(pastInit);
    }
    @Transactional
    public void updatePastInitForAll(PastInit pastInit) {
        pastInitDao.updatePastInitForAll(pastInit);
    }
    @Transactional
    public void deletePastInit(String id) {
        pastInitDao.deletePastInit(id);
    }
    @Transactional
    public void deletePastInitByCondition(PastInit pastInit) {
        pastInitDao.deletePastInitByCondition(pastInit);
    }
    @Transactional
    public void batchSavePastInit(List<PastInit> pastInits){
        pastInits.forEach(pastInit -> pastInit.setId(sequenceId.nextId()));
        pastInitDao.batchSavePastInit(pastInits);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
