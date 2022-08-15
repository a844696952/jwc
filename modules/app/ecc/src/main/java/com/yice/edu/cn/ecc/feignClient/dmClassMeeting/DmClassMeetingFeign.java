package com.yice.edu.cn.ecc.feignClient.dmClassMeeting;

import com.yice.edu.cn.common.pojo.dm.dmClassMeeting.DmClassMeeting;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmAttachmentFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "dmClassMeetingFeign",path = "/dmClassMeeting")
public interface DmClassMeetingFeign {
    @GetMapping("/findDmClassMeetingById/{id}")
    DmClassMeeting findDmClassMeetingById(@PathVariable("id") String id);
    @PostMapping("/findDmClassMeetingListByCondition")
    List<DmClassMeeting> findDmClassMeetingListByCondition(DmClassMeeting dmClassMeeting);
    @PostMapping("/findDmClassMeetingCountByCondition")
    long findDmClassMeetingCountByCondition(DmClassMeeting dmClassMeeting);
    @GetMapping("/findDmClassMeetingImgsByclassId/{classId}")
    List<DmAttachmentFile> findDmClassMeetingImgsByclassId(@PathVariable("classId") String classId);
}
