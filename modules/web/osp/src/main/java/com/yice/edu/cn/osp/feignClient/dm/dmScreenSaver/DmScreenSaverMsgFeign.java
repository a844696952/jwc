package com.yice.edu.cn.osp.feignClient.dm.dmScreenSaver;

import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaverMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmScreenSaverMsgFeign",path = "/dmScreenSaverMsg")
public interface DmScreenSaverMsgFeign {
    @GetMapping("/findDmScreenSaverMsgById/{id}")
    DmScreenSaverMsg findDmScreenSaverMsgById(@PathVariable("id") String id);
    @PostMapping("/saveDmScreenSaverMsg")
    DmScreenSaverMsg saveDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg);
    @PostMapping("/findDmScreenSaverMsgListByCondition")
    List<DmScreenSaverMsg> findDmScreenSaverMsgListByCondition(DmScreenSaverMsg dmScreenSaverMsg);
    @PostMapping("/findOneDmScreenSaverMsgByCondition")
    DmScreenSaverMsg findOneDmScreenSaverMsgByCondition(DmScreenSaverMsg dmScreenSaverMsg);
    @PostMapping("/findDmScreenSaverMsgCountByCondition")
    long findDmScreenSaverMsgCountByCondition(DmScreenSaverMsg dmScreenSaverMsg);
    @PostMapping("/updateDmScreenSaverMsg")
    void updateDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg);
    @GetMapping("/deleteDmScreenSaverMsg/{id}")
    void deleteDmScreenSaverMsg(@PathVariable("id") String id);
    @PostMapping("/deleteDmScreenSaverMsgByCondition")
    void deleteDmScreenSaverMsgByCondition(DmScreenSaverMsg dmScreenSaverMsg);
    @PostMapping("/batchUpdateDmScreenSaverMsg")
    void batchUpdateDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg);
    @PostMapping("/batchDeleteDmScreenSaverMsg")
    void batchDeleteDmScreenSaverMsg(DmScreenSaverMsg dmScreenSaverMsg);
    @PostMapping("/batchUpdateDmScreenSaverMsgStatus")
    void batchUpdateDmScreenSaverMsgStatus(DmScreenSaverMsg dmScreenSaverMsg);
}
