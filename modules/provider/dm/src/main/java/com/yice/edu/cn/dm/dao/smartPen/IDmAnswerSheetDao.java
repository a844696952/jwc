package com.yice.edu.cn.dm.dao.smartPen;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.smartPen.DmAnswerSheet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmAnswerSheetDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmAnswerSheet> findDmAnswerSheetListByCondition(DmAnswerSheet dmAnswerSheet);

    long findDmAnswerSheetCountByCondition(DmAnswerSheet dmAnswerSheet);

    DmAnswerSheet findOneDmAnswerSheetByCondition(DmAnswerSheet dmAnswerSheet);

    DmAnswerSheet findDmAnswerSheetById(@Param("id") String id);

    void saveDmAnswerSheet(DmAnswerSheet dmAnswerSheet);

    void updateDmAnswerSheet(DmAnswerSheet dmAnswerSheet);

    void deleteDmAnswerSheet(@Param("id") String id);

    void deleteDmAnswerSheetByCondition(DmAnswerSheet dmAnswerSheet);

    void batchSaveDmAnswerSheet(List<DmAnswerSheet> dmAnswerSheets);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
