package com.yice.edu.cn.jw.dao.appIndex;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.appIndex.SchoolAppIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISchoolAppIndexDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<SchoolAppIndex> findSchoolAppIndexListByCondition(SchoolAppIndex schoolAppIndex);

    long findSchoolAppIndexCountByCondition(SchoolAppIndex schoolAppIndex);

    SchoolAppIndex findOneSchoolAppIndexByCondition(SchoolAppIndex schoolAppIndex);

    SchoolAppIndex findSchoolAppIndexById(@Param("id") String id);

    void saveSchoolAppIndex(SchoolAppIndex schoolAppIndex);

    void updateSchoolAppIndex(SchoolAppIndex schoolAppIndex);

    void deleteSchoolAppIndex(@Param("id") String id);

    void deleteSchoolAppIndexByCondition(SchoolAppIndex schoolAppIndex);

    void batchSaveSchoolAppIndex(List<SchoolAppIndex> schoolAppIndexs);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    void moveAppIndexes(List<SchoolAppIndex> schoolAppIndices);

    void upsertSchoolAppIndex(SchoolAppIndex schoolAppIndex);
}
