package com.yice.edu.cn.bmp.feignClient.parentMsg;

import com.yice.edu.cn.common.pojo.dm.parentMsg.DmParentQuickReply;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmParentQuickReplyFeign",path = "/dmParentQuickReply")
public interface DmParentQuickReplyFeign {
    @GetMapping("/findDmParentQuickReplyById/{id}")
    DmParentQuickReply findDmParentQuickReplyById(@PathVariable("id") String id);
    @PostMapping("/saveDmParentQuickReply")
    DmParentQuickReply saveDmParentQuickReply(DmParentQuickReply dmParentQuickReply);
    @PostMapping("/findDmParentQuickReplyListByCondition")
    List<DmParentQuickReply> findDmParentQuickReplyListByCondition(DmParentQuickReply dmParentQuickReply);
    @PostMapping("/findOneDmParentQuickReplyByCondition")
    DmParentQuickReply findOneDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply);
    @PostMapping("/findDmParentQuickReplyCountByCondition")
    long findDmParentQuickReplyCountByCondition(DmParentQuickReply dmParentQuickReply);
    @PostMapping("/updateDmParentQuickReply")
    void updateDmParentQuickReply(DmParentQuickReply dmParentQuickReply);
    @GetMapping("/deleteDmParentQuickReply/{id}")
    void deleteDmParentQuickReply(@PathVariable("id") String id);
    @PostMapping("/deleteDmParentQuickReplyByCondition")
    void deleteDmParentQuickReplyByCondition(DmParentQuickReply dmParentQuickReply);
}
