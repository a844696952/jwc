package com.yice.edu.cn.bmp.feignClient.dm.msg;

import com.yice.edu.cn.common.pojo.dm.msg.ParentReceiveMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/parentReceiveMsg")
public interface ParentReceiveMsgFeign {
    @GetMapping("/findReceiveMsgById/{id}")
    ParentReceiveMsg findReceiveMsgById(@PathVariable("id") String id);
    @PostMapping("/saveReceiveMsg")
    ParentReceiveMsg saveReceiveMsg(ParentReceiveMsg receiveMsg);
    @PostMapping("/findReceiveMsgListByCondition")
    List<ParentReceiveMsg> findReceiveMsgListByCondition(ParentReceiveMsg receiveMsg);
    @PostMapping("/findOneReceiveMsgByCondition")
    ParentReceiveMsg findOneReceiveMsgByCondition(ParentReceiveMsg receiveMsg);
    @PostMapping("/findReceiveMsgCountByCondition")
    long findReceiveMsgCountByCondition(ParentReceiveMsg receiveMsg);
    @PostMapping("/updateReceiveMsg")
    void updateReceiveMsg(ParentReceiveMsg receiveMsg);
    @GetMapping("/deleteReceiveMsg/{id}")
    void deleteReceiveMsg(@PathVariable("id") String id);
    @PostMapping("/deleteReceiveMsgByCondition")
    void deleteReceiveMsgByCondition(ParentReceiveMsg receiveMsg);

    @PostMapping("/batchSave")
    void batchSave(List<ParentReceiveMsg> list);


    @PostMapping("/readMsgs")
    void readMsgs(List<String> ids);

    @PostMapping("/readMsg/{id}")
    void readMsg(@PathVariable("id") String id);
}
