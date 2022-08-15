package com.yice.edu.cn.bmp.service.dm.msg;

import com.yice.edu.cn.bmp.feignClient.dm.msg.ParentReceiveMsgFeign;
import com.yice.edu.cn.common.pojo.dm.msg.ParentReceiveMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiveMsgService {
    @Autowired
    private ParentReceiveMsgFeign parentReceiveMsgFeign;

    public ParentReceiveMsg findReceiveMsgById(String id) {
        return parentReceiveMsgFeign.findReceiveMsgById(id);
    }

    public ParentReceiveMsg saveReceiveMsg(ParentReceiveMsg receiveMsg) {
        return parentReceiveMsgFeign.saveReceiveMsg(receiveMsg);
    }

    public List<ParentReceiveMsg> findReceiveMsgListByCondition(ParentReceiveMsg receiveMsg) {
        return parentReceiveMsgFeign.findReceiveMsgListByCondition(receiveMsg);
    }

    public ParentReceiveMsg findOneReceiveMsgByCondition(ParentReceiveMsg receiveMsg) {
        return parentReceiveMsgFeign.findOneReceiveMsgByCondition(receiveMsg);
    }

    public long findReceiveMsgCountByCondition(ParentReceiveMsg receiveMsg) {
        return parentReceiveMsgFeign.findReceiveMsgCountByCondition(receiveMsg);
    }

    public void updateReceiveMsg(ParentReceiveMsg receiveMsg) {
        parentReceiveMsgFeign.updateReceiveMsg(receiveMsg);
    }

    public void deleteReceiveMsg(String id) {
        parentReceiveMsgFeign.deleteReceiveMsg(id);
    }

    public void deleteReceiveMsgByCondition(ParentReceiveMsg receiveMsg) {
        parentReceiveMsgFeign.deleteReceiveMsgByCondition(receiveMsg);
    }


    public void readTextMsgs(List<String> ids){
        parentReceiveMsgFeign.readMsgs(ids);
    };

    public void readAudioMsg(String id){
        parentReceiveMsgFeign.readMsg(id);
    };
}
