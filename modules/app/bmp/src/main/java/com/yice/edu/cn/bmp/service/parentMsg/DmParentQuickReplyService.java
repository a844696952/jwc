package com.yice.edu.cn.bmp.service.parentMsg;

import com.yice.edu.cn.bmp.feignClient.parentMsg.DmParentQuickReplyFeign;
import com.yice.edu.cn.common.pojo.dm.parentMsg.DmParentQuickReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmParentQuickReplyService {
    @Autowired
    private DmParentQuickReplyFeign dmParentQuickReplyFeign;

    public DmParentQuickReply findDmParentQuickReplyById(String id) {
        return dmParentQuickReplyFeign.findDmParentQuickReplyById(id);
    }

    public DmParentQuickReply saveDmParentQuickReply(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyFeign.saveDmParentQuickReply(dmParentQuickReply);
    }

    public List<DmParentQuickReply> findDmParentQuickReplyListByCondition(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyFeign.findDmParentQuickReplyListByCondition(dmParentQuickReply);
    }

    public DmParentQuickReply findOneDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyFeign.findOneDmParentQuickReplyByCondition(dmParentQuickReply);
    }

    public long findDmParentQuickReplyCountByCondition(DmParentQuickReply dmParentQuickReply) {
        return dmParentQuickReplyFeign.findDmParentQuickReplyCountByCondition(dmParentQuickReply);
    }

    public void updateDmParentQuickReply(DmParentQuickReply dmParentQuickReply) {
        dmParentQuickReplyFeign.updateDmParentQuickReply(dmParentQuickReply);
    }

    public void deleteDmParentQuickReply(String id) {
        dmParentQuickReplyFeign.deleteDmParentQuickReply(id);
    }

    public void deleteDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply) {
        dmParentQuickReplyFeign.deleteDmParentQuickReplyByCondition(dmParentQuickReply);
    }
}
