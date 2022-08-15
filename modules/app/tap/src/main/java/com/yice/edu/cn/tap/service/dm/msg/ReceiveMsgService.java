package com.yice.edu.cn.tap.service.dm.msg;

import com.yice.edu.cn.common.pojo.dm.msg.TeacherReceiveMsg;
import com.yice.edu.cn.tap.feignClient.dm.msg.TeacherReceiveMsgFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiveMsgService {
    @Autowired
    private TeacherReceiveMsgFeign teacherReceiveMsgFeign;

    public TeacherReceiveMsg findReceiveMsgById(String id) {
        return teacherReceiveMsgFeign.findReceiveMsgById(id);
    }

    public TeacherReceiveMsg saveReceiveMsg(TeacherReceiveMsg receiveMsg) {
        return teacherReceiveMsgFeign.saveReceiveMsg(receiveMsg);
    }

    public List<TeacherReceiveMsg> findReceiveMsgListByCondition(TeacherReceiveMsg receiveMsg) {
        return teacherReceiveMsgFeign.findReceiveMsgListByCondition(receiveMsg);
    }

    public TeacherReceiveMsg findOneReceiveMsgByCondition(TeacherReceiveMsg receiveMsg) {
        return teacherReceiveMsgFeign.findOneReceiveMsgByCondition(receiveMsg);
    }

    public long findReceiveMsgCountByCondition(TeacherReceiveMsg receiveMsg) {
        return teacherReceiveMsgFeign.findReceiveMsgCountByCondition(receiveMsg);
    }

    public void updateReceiveMsg(TeacherReceiveMsg receiveMsg) {
        teacherReceiveMsgFeign.updateReceiveMsg(receiveMsg);
    }

    public void deleteReceiveMsg(String id) {
        teacherReceiveMsgFeign.deleteReceiveMsg(id);
    }

    public void deleteReceiveMsgByCondition(TeacherReceiveMsg receiveMsg) {
        teacherReceiveMsgFeign.deleteReceiveMsgByCondition(receiveMsg);
    }


    public void readTextMsgs(List<String> ids){
        teacherReceiveMsgFeign.readMsgs(ids);
    };

    public void readAudioMsg(String id){
        teacherReceiveMsgFeign.readMsg(id);
    };
}
