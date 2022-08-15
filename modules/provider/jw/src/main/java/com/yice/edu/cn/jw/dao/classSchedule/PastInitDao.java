package com.yice.edu.cn.jw.dao.classSchedule;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.classSchedule.PastInit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PastInitDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<PastInit> findPastInitListByCondition(PastInit pastInit);

    long findPastInitCountByCondition(PastInit pastInit);

    PastInit findOnePastInitByCondition(PastInit pastInit);

    PastInit findPastInitById(@Param("id") String id);

    void savePastInit(PastInit pastInit);

    void updatePastInit(PastInit pastInit);

    void updatePastInitForAll(PastInit pastInit);

    void deletePastInit(@Param("id") String id);

    void deletePastInitByCondition(PastInit pastInit);

    void batchSavePastInit(List<PastInit> pastInits);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
