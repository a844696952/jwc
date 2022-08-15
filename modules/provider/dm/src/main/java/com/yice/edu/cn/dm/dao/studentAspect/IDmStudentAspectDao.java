package com.yice.edu.cn.dm.dao.studentAspect;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.studentAspect.DmStudentAspect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmStudentAspectDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmStudentAspect> findDmStudentAspectListByCondition(DmStudentAspect dmStudentAspect);

    long findDmStudentAspectCountByCondition(DmStudentAspect dmStudentAspect);

    DmStudentAspect findOneDmStudentAspectByCondition(DmStudentAspect dmStudentAspect);

    DmStudentAspect findDmStudentAspectById(@Param("id") String id);

    void saveDmStudentAspect(DmStudentAspect dmStudentAspect);

    void updateDmStudentAspect(DmStudentAspect dmStudentAspect);

    void updateDmStudentAspectForAll(DmStudentAspect dmStudentAspect);

    void deleteDmStudentAspect(@Param("id") String id);

    void deleteDmStudentAspectByCondition(DmStudentAspect dmStudentAspect);

    void batchSaveDmStudentAspect(List<DmStudentAspect> dmStudentAspects);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
