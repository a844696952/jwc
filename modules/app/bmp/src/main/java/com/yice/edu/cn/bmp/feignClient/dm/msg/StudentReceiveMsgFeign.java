package com.yice.edu.cn.bmp.feignClient.dm.msg;

import com.yice.edu.cn.common.pojo.dm.msg.StudentReceiveMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/studentReceiveMsg")
public interface StudentReceiveMsgFeign {
    @GetMapping("/findReceiveMsgById/{id}")
    StudentReceiveMsg findReceiveMsgById(@PathVariable("id") String id);
    @PostMapping("/saveReceiveMsg")
    StudentReceiveMsg saveReceiveMsg(StudentReceiveMsg receiveMsg);
    @PostMapping("/findReceiveMsgListByCondition")
    List<StudentReceiveMsg> findReceiveMsgListByCondition(StudentReceiveMsg receiveMsg);
    @PostMapping("/findOneReceiveMsgByCondition")
    StudentReceiveMsg findOneReceiveMsgByCondition(StudentReceiveMsg receiveMsg);
    @PostMapping("/findReceiveMsgCountByCondition")
    long findReceiveMsgCountByCondition(StudentReceiveMsg receiveMsg);
    @PostMapping("/updateReceiveMsg")
    void updateReceiveMsg(StudentReceiveMsg receiveMsg);
    @GetMapping("/deleteReceiveMsg/{id}")
    void deleteReceiveMsg(@PathVariable("id") String id);
    @PostMapping("/deleteReceiveMsgByCondition")
    void deleteReceiveMsgByCondition(StudentReceiveMsg receiveMsg);

    @PostMapping("/batchSave")
    void batchSave(List<StudentReceiveMsg> list);


    @PostMapping("/readMsgs")
    void readMsgs(List<String> ids);

    @PostMapping("/readMsg/{id}")
    void readMsg(@PathVariable("id") String id);
}
