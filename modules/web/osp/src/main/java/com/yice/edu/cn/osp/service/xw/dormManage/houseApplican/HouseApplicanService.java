package com.yice.edu.cn.osp.service.xw.dormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplican;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.houseApplican.HouseApplicanFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseApplicanService {
    @Autowired
    private HouseApplicanFeign houseApplicanFeign;

    public HouseApplican findHouseApplicanById(String id) {
        return houseApplicanFeign.findHouseApplicanById(id);
    }

    public HouseApplican saveHouseApplican(HouseApplican houseApplican) {
        return houseApplicanFeign.saveHouseApplican(houseApplican);
    }

    public List<HouseApplican> findHouseApplicanListByCondition(HouseApplican houseApplican) {
        return houseApplicanFeign.findHouseApplicanListByCondition(houseApplican);
    }

    public HouseApplican findOneHouseApplicanByCondition(HouseApplican houseApplican) {
        return houseApplicanFeign.findOneHouseApplicanByCondition(houseApplican);
    }

    public long findHouseApplicanCountByCondition(HouseApplican houseApplican) {
        return houseApplicanFeign.findHouseApplicanCountByCondition(houseApplican);
    }

    public void updateHouseApplican(HouseApplican houseApplican) {
        houseApplicanFeign.updateHouseApplican(houseApplican);
    }

    public void deleteHouseApplican(String id) {
        houseApplicanFeign.deleteHouseApplican(id);
    }

    public void deleteHouseApplicanByCondition(HouseApplican houseApplican) {
        houseApplicanFeign.deleteHouseApplicanByCondition(houseApplican);
    }

    public List<HouseApplican> findApprovalPending(HouseApplican houseApplican) {
        return houseApplicanFeign.findApprovalPending(houseApplican);
    }

    public long findApprovalPendingCount(HouseApplican houseApplican) {
        return houseApplicanFeign.findApprovalPendingCount(houseApplican);
    }

    public List<HouseApplican> findPassPending(HouseApplican houseApplican) {
        return houseApplicanFeign.findPassPending(houseApplican);
    }

    public long findPassPendingCount(HouseApplican houseApplican) {
        return houseApplicanFeign.findPassPendingCount(houseApplican);
    }

    public HouseApplican saveHouseApplicanFromParent(HouseApplican houseApplican) {
        return houseApplicanFeign.saveHouseApplicanFromParent(houseApplican);
    }

    public void updateHouseApplicanAndTeacher(HouseApplican houseApplican) {
        houseApplicanFeign.updateHouseApplicanAndTeacher(houseApplican);
    }
}
