package com.yice.edu.cn.dm.dao.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmActivityItemDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmActivityItem> findDmActivityItemListByCondition(DmActivityItem dmActivityItem);

    long findDmActivityItemCountByCondition(DmActivityItem dmActivityItem);

    DmActivityItem findOneDmActivityItemByCondition(DmActivityItem dmActivityItem);

    DmActivityItem findDmActivityItemById(@Param("id") String id);

    void saveDmActivityItem(DmActivityItem dmActivityItem);

    void updateDmActivityItem(DmActivityItem dmActivityItem);

    void deleteDmActivityItem(@Param("id") String id);

    void deleteDmActivityItemByCondition(DmActivityItem dmActivityItem);

    void batchSaveDmActivityItem(List<DmActivityItem> dmActivityItems);

    List<DmActivityItem> findDmActivityItemListByActivityId(@Param("activityId") String activityId);

    List<DmActivityItem> findDmActivityItemListByActivityIdAndClassesId(DmActivityItem dmActivityItem);

    void updateDmActivityItemById(DmActivityItem dmActivityItem);

    void deleteDmActivityItemByActivityId(@Param("activityId") String activityId);

    void deleteNotExist(@Param("itemIds") List<String> itemIds, @Param("activityId") String activityId);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
