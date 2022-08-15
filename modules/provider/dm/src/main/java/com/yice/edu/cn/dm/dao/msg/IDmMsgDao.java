package com.yice.edu.cn.dm.dao.msg;

import com.yice.edu.cn.common.pojo.dm.msg.DmMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmMsgDao {
    List<DmMsg> findDmMsgListByCondition(DmMsg dmMsg);

    long findDmMsgCountByCondition(DmMsg dmMsg);

    DmMsg findOneDmMsgByCondition(DmMsg dmMsg);

    DmMsg findDmMsgById(@Param("id") String id);

    void saveDmMsg(DmMsg dmMsg);

    void updateDmMsg(DmMsg dmMsg);

    void deleteDmMsg(@Param("id") String id);

    void deleteDmMsgByCondition(DmMsg dmMsg);

    void batchSaveDmMsg(List<DmMsg> dmMsgs);
}
