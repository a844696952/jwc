package com.yice.edu.cn.osp.service.xw.dutyLocation;

import com.yice.edu.cn.common.pojo.xw.dutyLocation.DutyLocation;
import com.yice.edu.cn.osp.feignClient.xw.dutyLocation.DutyLocationFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyLocationService {
    @Autowired
    private DutyLocationFeign dutyLocationFeign;

    public DutyLocation findDutyLocationById(String id) {
        return dutyLocationFeign.findDutyLocationById(id);
    }

    public DutyLocation saveDutyLocation(DutyLocation dutyLocation) {
        if(!StringUtils.isEmpty(dutyLocation.getPlace())){
            dutyLocation.setPlace(dutyLocation.getPlace().trim());
        }
        return dutyLocationFeign.saveDutyLocation(dutyLocation);
    }

    public List<DutyLocation> findDutyLocationListByCondition(DutyLocation dutyLocation) {
        return dutyLocationFeign.findDutyLocationListByCondition(dutyLocation);
    }

    public DutyLocation findOneDutyLocationByCondition(DutyLocation dutyLocation) {
        return dutyLocationFeign.findOneDutyLocationByCondition(dutyLocation);
    }

    public long findDutyLocationCountByCondition(DutyLocation dutyLocation) {
        return dutyLocationFeign.findDutyLocationCountByCondition(dutyLocation);
    }

    public void updateDutyLocation(DutyLocation dutyLocation) {
        dutyLocationFeign.updateDutyLocation(dutyLocation);
    }

    public void deleteDutyLocation(String id) {
        dutyLocationFeign.deleteDutyLocation(id);
    }

    public DutyLocation deleteDutyLocationByCondition(DutyLocation dutyLocation) {
        return dutyLocationFeign.deleteDutyLocationByCondition(dutyLocation);
    }
}
