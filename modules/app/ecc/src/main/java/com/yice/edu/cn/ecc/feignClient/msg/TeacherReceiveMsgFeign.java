package com.yice.edu.cn.ecc.feignClient.msg;

import com.yice.edu.cn.common.pojo.dm.msg.TeacherReceiveMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/teacherReceiveMsg")
public interface TeacherReceiveMsgFeign {
    @GetMapping("/findReceiveMsgById/{id}")
    TeacherReceiveMsg findReceiveMsgById(@PathVariable("id") String id);
    @PostMapping("/saveReceiveMsg")
    TeacherReceiveMsg saveReceiveMsg(TeacherReceiveMsg receiveMsg);
    @PostMapping("/findReceiveMsgListByCondition")
    List<TeacherReceiveMsg> findReceiveMsgListByCondition(TeacherReceiveMsg receiveMsg);
    @PostMapping("/findOneReceiveMsgByCondition")
    TeacherReceiveMsg findOneReceiveMsgByCondition(TeacherReceiveMsg receiveMsg);
    @PostMapping("/findReceiveMsgCountByCondition")
    long findReceiveMsgCountByCondition(TeacherReceiveMsg receiveMsg);
    @PostMapping("/updateReceiveMsg")
    void updateReceiveMsg(TeacherReceiveMsg receiveMsg);
    @GetMapping("/deleteReceiveMsg/{id}")
    void deleteReceiveMsg(@PathVariable("id") String id);
    @PostMapping("/deleteReceiveMsgByCondition")
    void deleteReceiveMsgByCondition(TeacherReceiveMsg receiveMsg);

    @PostMapping("/batchSave")
    void batchSave(List<TeacherReceiveMsg> list);


    @PostMapping("/readMsgs")
    void readMsgs(List<String> ids);

    @PostMapping("/readMsg/{id}")
    void readMsg(@PathVariable("id") String id);
}
