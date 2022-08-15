package com.yice.edu.cn.dm.dao.parentMsg;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.parentMsg.DmParentQuickReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDmParentQuickReplyDao {
    List<DmParentQuickReply> findDmParentQuickReplyListByCondition(DmParentQuickReply dmParentQuickReply);

    long findDmParentQuickReplyCountByCondition(DmParentQuickReply dmParentQuickReply);

    DmParentQuickReply findOneDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply);

    DmParentQuickReply findDmParentQuickReplyById(@Param("id") String id);

    void saveDmParentQuickReply(DmParentQuickReply dmParentQuickReply);

    void updateDmParentQuickReply(DmParentQuickReply dmParentQuickReply);

    void deleteDmParentQuickReply(@Param("id") String id);

    void deleteDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply);

    void batchSaveDmParentQuickReply(List<DmParentQuickReply> dmParentQuickReplys);
}
