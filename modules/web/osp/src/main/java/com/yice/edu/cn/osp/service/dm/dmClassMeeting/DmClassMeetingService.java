package com.yice.edu.cn.osp.service.dm.dmClassMeeting;

import com.yice.edu.cn.common.pojo.dm.dmClassMeeting.DmClassMeeting;
import com.yice.edu.cn.osp.feignClient.dm.dmClassMeeting.DmClassMeetingFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmClassMeetingService {
    @Autowired
    private DmClassMeetingFeign dmClassMeetingFeign;

    public DmClassMeeting findDmClassMeetingById(String id) {
        return dmClassMeetingFeign.findDmClassMeetingById(id);
    }

    public Boolean saveDmClassMeeting(DmClassMeeting dmClassMeeting) {
        return dmClassMeetingFeign.saveDmClassMeeting(dmClassMeeting);
    }

    public List<DmClassMeeting> findDmClassMeetingListByCondition(DmClassMeeting dmClassMeeting) {
        return dmClassMeetingFeign.findDmClassMeetingListByCondition(dmClassMeeting);
    }

    public DmClassMeeting findOneDmClassMeetingByCondition(DmClassMeeting dmClassMeeting) {
        return dmClassMeetingFeign.findOneDmClassMeetingByCondition(dmClassMeeting);
    }

    public long findDmClassMeetingCountByCondition(DmClassMeeting dmClassMeeting) {
        return dmClassMeetingFeign.findDmClassMeetingCountByCondition(dmClassMeeting);
    }

    public void updateDmClassMeeting(DmClassMeeting dmClassMeeting) {
        dmClassMeetingFeign.updateDmClassMeeting(dmClassMeeting);
    }

    public void deleteDmClassMeeting(String id) {
        dmClassMeetingFeign.deleteDmClassMeeting(id);
    }

    public void deleteDmClassMeetingByCondition(DmClassMeeting dmClassMeeting) {
        dmClassMeetingFeign.deleteDmClassMeetingByCondition(dmClassMeeting);
    }
}
