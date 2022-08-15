package com.yice.edu.cn.dm.dao.dmScreenSaver;

import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.AreaByDmClassVo;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmScreenSaverDao {
    List<DmScreenSaver> findDmScreenSaverListByCondition(DmScreenSaver dmScreenSaver);

    long findDmScreenSaverCountByCondition(DmScreenSaver dmScreenSaver);

    DmScreenSaver findOneDmScreenSaverByCondition(DmScreenSaver dmScreenSaver);

    DmScreenSaver findDmScreenSaverById(@Param("id") String id);

    void saveDmScreenSaver(DmScreenSaver dmScreenSaver);

    void updateDmScreenSaver(DmScreenSaver dmScreenSaver);

    void deleteDmScreenSaver(@Param("id") String id);

    void deleteDmScreenSaverByCondition(DmScreenSaver dmScreenSaver);

    void batchSaveDmScreenSaver(List<DmScreenSaver> dmScreenSavers);

    void batchUpdateDmScreenSaver(DmScreenSaver dmScreenSaver);

    void batchDeleteDmScreenSaver(DmScreenSaver dmScreenSaver);
//
//    //获取所有的楼栋信息
//    List<JwAcademicBuilding> getBuildingList(DmScreenSaver dmScreenSaver);

    //获取所有的楼层信息
    String[] getSpaceList(DmScreenSaver dmScreenSaver);

    //获取班级名称和班牌的用户名
    List<AreaByDmClassVo> getAreaByDmClass(DmScreenSaver dmScreenSaver);

    //根据班级编号获取到云班牌用户名
    String getUserNameByClassId(DmScreenSaver dmScreenSaver);

    void batchUpdateDmScreenSaverStatus(DmScreenSaver dmScreenSaver);

    DmScreenSaver getRunNingDmScreenSaver(DmScreenSaver dmScreenSaver);

}
