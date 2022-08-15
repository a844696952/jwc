package com.yice.edu.cn.bmp.service.xwDormManage.houseApplican;

import com.yice.edu.cn.bmp.feignClient.xwDormManage.houseApplican.HouseApplicanFeign;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplican;
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
}
