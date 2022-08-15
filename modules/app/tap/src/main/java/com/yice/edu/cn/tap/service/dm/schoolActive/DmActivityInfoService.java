package com.yice.edu.cn.tap.service.dm.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityInfo;
import com.yice.edu.cn.tap.feignClient.classes.JwClaCadresStuFeign;
import com.yice.edu.cn.tap.feignClient.dm.schoolActive.DmActivityInfoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmActivityInfoService {
    @Autowired
    private DmActivityInfoFeign dmActivityInfoFeign;

    @Autowired
    private JwClaCadresStuFeign jwClaCadresStuFeign;

    public DmActivityInfo findDmActivityInfoById(String id) {
        return dmActivityInfoFeign.findDmActivityInfoById(id);
    }

    public DmActivityInfo saveDmActivityInfo(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoFeign.saveDmActivityInfo(dmActivityInfo);
    }

    public List<DmActivityInfo> findDmActivityInfoListByCondition(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoFeign.findDmActivityInfoListByCondition(dmActivityInfo);
    }

    public DmActivityInfo findOneDmActivityInfoByCondition(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoFeign.findOneDmActivityInfoByCondition(dmActivityInfo);
    }

    public long findDmActivityInfoCountByCondition(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoFeign.findDmActivityInfoCountByCondition(dmActivityInfo);
    }

    public void updateDmActivityInfo(DmActivityInfo dmActivityInfo) {
        dmActivityInfoFeign.updateDmActivityInfo(dmActivityInfo);
    }

    public void deleteDmActivityInfo(String id) {
        dmActivityInfoFeign.deleteDmActivityInfo(id);
    }

    public void deleteDmActivityInfoByCondition(DmActivityInfo dmActivityInfo) {
        dmActivityInfoFeign.deleteDmActivityInfoByCondition(dmActivityInfo);
    }

    public List<DmActivityInfo> selectDmActivityInfosByCondition(DmActivityInfo dmActivityInfo) {
        return dmActivityInfoFeign.selectDmActivityInfosByCondition(dmActivityInfo);
    }

    public long checkStudentIdentity(String studentId) {
        return jwClaCadresStuFeign.checkStudentIdentity(studentId);
    }

    public DmActivityInfo findDmActivityInfoNoItemByActivityId(String activityId) {
        return dmActivityInfoFeign.findDmActivityInfoNoItemByActivityId(activityId);
    }

    public DmActivityInfo findDmActivityInfoByActivityId(String activityId){
        return dmActivityInfoFeign.findDmActivityInfoByActivityId(activityId);
    }
}
