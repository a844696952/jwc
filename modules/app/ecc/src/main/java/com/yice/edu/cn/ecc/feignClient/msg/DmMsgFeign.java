package com.yice.edu.cn.ecc.feignClient.msg;

import com.yice.edu.cn.common.pojo.dm.msg.DmMsg;
import com.yice.edu.cn.common.pojo.dm.msg.StudentMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",path = "/dmMsg")
public interface DmMsgFeign {
    @GetMapping("/findDmMsgById/{id}")
    DmMsg findDmMsgById(@PathVariable("id") String id);
    @PostMapping("/saveDmMsg")
    DmMsg saveDmMsg(DmMsg dmMsg);
    @PostMapping("/findDmMsgListByCondition")
    List<DmMsg> findDmMsgListByCondition(DmMsg dmMsg);
    @PostMapping("/findOneDmMsgByCondition")
    DmMsg findOneDmMsgByCondition(DmMsg dmMsg);
    @PostMapping("/findDmMsgCountByCondition")
    long findDmMsgCountByCondition(DmMsg dmMsg);
    @PostMapping("/updateDmMsg")
    void updateDmMsg(DmMsg dmMsg);
    @GetMapping("/deleteDmMsg/{id}")
    void deleteDmMsg(@PathVariable("id") String id);
    @PostMapping("/deleteDmMsgByCondition")
    void deleteDmMsgByCondition(DmMsg dmMsg);

    @PostMapping("/getStudentMsg")
    List<StudentMsg> getStudentMsg(DmMsg dmMsg);

    @PostMapping("/sendMsg")
    DmMsg sendMsg(DmMsg dmMsg);


}
