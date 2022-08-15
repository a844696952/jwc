package com.yice.edu.cn.dm.dao.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmActivitySiginUpDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmActivitySiginUp> findDmActivitySiginUpListByCondition(DmActivitySiginUp dmActivitySiginUp);

    long findDmActivitySiginUpCountByCondition(DmActivitySiginUp dmActivitySiginUp);

    DmActivitySiginUp findOneDmActivitySiginUpByCondition(DmActivitySiginUp dmActivitySiginUp);

    DmActivitySiginUp findDmActivitySiginUpById(@Param("id") String id);

    void saveDmActivitySiginUp(DmActivitySiginUp dmActivitySiginUp);

    void updateDmActivitySiginUp(DmActivitySiginUp dmActivitySiginUp);

    void deleteDmActivitySiginUp(@Param("id") String id);

    void deleteDmActivitySiginUpByCondition(DmActivitySiginUp dmActivitySiginUp);

    void batchSaveDmActivitySiginUp(List<DmActivitySiginUp> dmActivitySiginUps);

    int checkGradeSingUp(DmActivityInfo dmActivityInfo);

    List<DmActivitySiginUp> checkItemSingUp(@Param("itemId")String itemId);

    List<DmActivitySiginUp> findDmActivitySiginUpListByItemId(@Param("itemId") String itemId,@Param("classesId") String classesId);

    void deleteDmActivitySiginUpByItemId(@Param("itemId") String itemId,@Param("classesId") String classesId);

    void deleteNotExist(@Param("itemIds") List<String> itemIds, @Param("activityId") String activityId);

    void deleteDmActivitySiginUpByActivityId(@Param("activityId") String activityId);

    void deleteDmActivitySiginUpByActivityIdAndGradeId(@Param("activityId") String activityId, @Param("classesIds")List<String> classesIds);



    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
