package com.yice.edu.cn.jy.dao.collectivePlan;

import java.util.List;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.CollectivePlanTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ICollectivePlanTeacherDao {
    List<CollectivePlanTeacher> findCollectivePlanTeacherListByCondition(CollectivePlanTeacher collectivePlanTeacher);

    CollectivePlanTeacher findOneCollectivePlanTeacherByCondition(CollectivePlanTeacher collectivePlanTeacher);

    long findCollectivePlanTeacherCountByCondition(CollectivePlanTeacher collectivePlanTeacher);

    CollectivePlanTeacher findCollectivePlanTeacherById(@Param("id") String id);

    void saveCollectivePlanTeacher(CollectivePlanTeacher collectivePlanTeacher);

    void updateCollectivePlanTeacher(CollectivePlanTeacher collectivePlanTeacher);

    void deleteCollectivePlanTeacher(@Param("id") String id);

    void deleteCollectivePlanTeacherByCondition(CollectivePlanTeacher collectivePlanTeacher);

    void batchSaveCollectivePlanTeacher(List<CollectivePlanTeacher> collectivePlanTeachers);

    //查询讨论组中的教师
    List<CollectivePlanTeacher> findOneCollectivePlanByCollectivePlanId(CollectivePlanTeacher collectivePlanTeacher);
}
