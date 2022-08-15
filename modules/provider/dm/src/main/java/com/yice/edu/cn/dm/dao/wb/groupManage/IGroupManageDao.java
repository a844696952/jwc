package com.yice.edu.cn.dm.dao.wb.groupManage;

import com.yice.edu.cn.common.pojo.wb.groupManage.GroupManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IGroupManageDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<GroupManage> findGroupManageListByCondition(GroupManage groupManage);

    long findGroupManageCountByCondition(GroupManage groupManage);

    GroupManage findOneGroupManageByCondition(GroupManage groupManage);

    GroupManage findGroupManageById(@Param("id") String id);

    void saveGroupManage(GroupManage groupManage);

    void updateGroupManage(GroupManage groupManage);

    void deleteGroupManage(@Param("id") String id);

    void deleteGroupManageByCondition(GroupManage groupManage);

    void batchSaveGroupManage(List<GroupManage> groupManages);

    Integer selectMaxGroupNumber(@Param("classesId") String classesId);

    int selectRepeatNameByName(GroupManage groupManage);

    void moveGroupManage(GroupManage groupManage);

    void deleteGroupManageByClassIds(List<String> classIdList);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
