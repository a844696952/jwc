package com.yice.edu.cn.ecc.service.dmClassMeeting;

import com.yice.edu.cn.common.pojo.dm.dmClassMeeting.DmClassMeeting;
import com.yice.edu.cn.ecc.feignClient.dmClassMeeting.DmClassMeetingFeign;
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

    public List<DmClassMeeting> findDmClassMeetingListByCondition(DmClassMeeting dmClassMeeting) {
        return dmClassMeetingFeign.findDmClassMeetingListByCondition(dmClassMeeting);
    }


    public long findDmClassMeetingCountByCondition(DmClassMeeting dmClassMeeting) {
        return dmClassMeetingFeign.findDmClassMeetingCountByCondition(dmClassMeeting);
    }

}
