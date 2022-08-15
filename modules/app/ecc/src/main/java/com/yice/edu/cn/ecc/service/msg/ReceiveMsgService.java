package com.yice.edu.cn.ecc.service.msg;

import com.yice.edu.cn.common.pojo.dm.msg.StudentReceiveMsg;
import com.yice.edu.cn.ecc.feignClient.msg.StudentReceiveMsgFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiveMsgService {
    @Autowired
    private StudentReceiveMsgFeign studentReceiveMsgFeign;

    public StudentReceiveMsg findReceiveMsgById(String id) {
        return studentReceiveMsgFeign.findReceiveMsgById(id);
    }

    public StudentReceiveMsg saveReceiveMsg(StudentReceiveMsg receiveMsg) {
        return studentReceiveMsgFeign.saveReceiveMsg(receiveMsg);
    }

    public List<StudentReceiveMsg> findReceiveMsgListByCondition(StudentReceiveMsg receiveMsg) {
        return studentReceiveMsgFeign.findReceiveMsgListByCondition(receiveMsg);
    }

    public StudentReceiveMsg findOneReceiveMsgByCondition(StudentReceiveMsg receiveMsg) {
        return studentReceiveMsgFeign.findOneReceiveMsgByCondition(receiveMsg);
    }

    public long findReceiveMsgCountByCondition(StudentReceiveMsg receiveMsg) {
        return studentReceiveMsgFeign.findReceiveMsgCountByCondition(receiveMsg);
    }

    public void updateReceiveMsg(StudentReceiveMsg receiveMsg) {
        studentReceiveMsgFeign.updateReceiveMsg(receiveMsg);
    }

    public void deleteReceiveMsg(String id) {
        studentReceiveMsgFeign.deleteReceiveMsg(id);
    }

    public void deleteReceiveMsgByCondition(StudentReceiveMsg receiveMsg) {
        studentReceiveMsgFeign.deleteReceiveMsgByCondition(receiveMsg);
    }


    public void readTextMsgs(List<String> ids){
        studentReceiveMsgFeign.readMsgs(ids);
    };

    public void readAudioMsg(String id){
        studentReceiveMsgFeign.readMsg(id);
    };

}
