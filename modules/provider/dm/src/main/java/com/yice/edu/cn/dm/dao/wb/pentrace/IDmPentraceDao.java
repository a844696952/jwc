package com.yice.edu.cn.dm.dao.wb.pentrace;

import java.util.List;

import com.yice.edu.cn.common.pojo.wb.pentrace.DmPentrace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmPentraceDao {
    List<DmPentrace> findDmPentraceListByCondition(DmPentrace dmPentrace);

    long findDmPentraceCountByCondition(DmPentrace dmPentrace);

    DmPentrace findOneDmPentraceByCondition(DmPentrace dmPentrace);

    DmPentrace findDmPentraceById(@Param("id") String id);

    void saveDmPentrace(DmPentrace dmPentrace);

    void updateDmPentrace(DmPentrace dmPentrace);

    void deleteDmPentrace(@Param("id") String id);

    void deleteDmPentraceByCondition(DmPentrace dmPentrace);

    void batchSaveDmPentrace(List<DmPentrace> dmPentraces);
}
