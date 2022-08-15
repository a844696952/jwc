package com.yice.edu.cn.dm.dao.dmScreenSaver;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaverMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmScreenSaverMsgDao {
    List<DmScreenSaverMsg> findDmScreenSaverMsgListByCondition(DmScreenSaverMsg dmScreenSaverMsg);

    long findDmScreenSaverMsgCountByCondition(DmScreenSaverMsg dmScreenSaverMsg);

    DmScreenSaverMsg findOneDmScreenSaverMsgByCondition(DmScreenSaverMsg dmScreenSaverMsg);

    DmScreenSaverMsg findDmScreenSaverMsgById(@Param("id") String id);

    void saveDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg);

    void updateDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg);

    void deleteDmScreenSaverMsg(@Param("id") String id);

    void deleteDmScreenSaverMsgByCondition(DmScreenSaverMsg dmScreenSaverMsg);

    void batchSaveDmScreenSaverMsg(List<DmScreenSaverMsg> dmScreenSaverMsgs);

    void batchUpdateDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg);

    void batchDeleteDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg);


    void batchUpdateDmScreenSaverMsgStatus(DmScreenSaverMsg dmScreenSaverMsg);

    DmScreenSaverMsg getRunNingDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg);

}
