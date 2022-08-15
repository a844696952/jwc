package com.yice.edu.cn.dm.dao.dmTimedTask;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmTimedTaskDao {
    List<DmTimedTask> findDmTimedTaskListByCondition(DmTimedTask dmTimedTask);

    DmTimedTask findOneDmTimedTaskByCondition(DmTimedTask dmTimedTask);

    long findDmTimedTaskCountByCondition(DmTimedTask dmTimedTask);

    DmTimedTask findDmTimedTaskById(@Param("id") String id);

    void saveDmTimedTask(DmTimedTask dmTimedTask);

    void updateDmTimedTask(DmTimedTask dmTimedTask);

    void deleteDmTimedTask(@Param("id") String id);

    void deleteDmTimedTaskAll(@Param("ids")List<String> ids);

    void deleteDmTimedTaskByCondition(DmTimedTask dmTimedTask);

    void batchSaveDmTimedTask(List<DmTimedTask> dmTimedTasks);

    DmTimedTask findDmTimedTaskByEquipmentId(@Param("equipmentId") String equipmentId);

    void updateDmTimedTaskByEquipmentId(DmTimedTask dmTimedTask);


}
