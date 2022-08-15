package com.yice.edu.cn.osp.feignClient.dm.dmClassMeeting;

import com.yice.edu.cn.common.pojo.dm.dmClassMeeting.DmClassMeeting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmClassMeetingFeign",path = "/dmClassMeeting")
public interface DmClassMeetingFeign {
    @GetMapping("/findDmClassMeetingById/{id}")
    DmClassMeeting findDmClassMeetingById(@PathVariable("id") String id);
    @PostMapping("/saveDmClassMeeting")
    Boolean saveDmClassMeeting(DmClassMeeting dmClassMeeting);
    @PostMapping("/findDmClassMeetingListByCondition")
    List<DmClassMeeting> findDmClassMeetingListByCondition(DmClassMeeting dmClassMeeting);
    @PostMapping("/findOneDmClassMeetingByCondition")
    DmClassMeeting findOneDmClassMeetingByCondition(DmClassMeeting dmClassMeeting);
    @PostMapping("/findDmClassMeetingCountByCondition")
    long findDmClassMeetingCountByCondition(DmClassMeeting dmClassMeeting);
    @PostMapping("/updateDmClassMeeting")
    void updateDmClassMeeting(DmClassMeeting dmClassMeeting);
    @GetMapping("/deleteDmClassMeeting/{id}")
    void deleteDmClassMeeting(@PathVariable("id") String id);
    @PostMapping("/deleteDmClassMeetingByCondition")
    void deleteDmClassMeetingByCondition(DmClassMeeting dmClassMeeting);
}
