package com.yice.edu.cn.tap.service.xwDormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanTeachers;
import com.yice.edu.cn.tap.feignClient.xwDormManage.houseApplican.HouseApplicanTeachersFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseApplicanTeachersService {
    @Autowired
    private HouseApplicanTeachersFeign houseApplicanTeachersFeign;

    public HouseApplicanTeachers findHouseApplicanTeachersById(String id) {
        return houseApplicanTeachersFeign.findHouseApplicanTeachersById(id);
    }

    public HouseApplicanTeachers saveHouseApplicanTeachers(HouseApplicanTeachers houseApplicanTeachers) {
        return houseApplicanTeachersFeign.saveHouseApplicanTeachers(houseApplicanTeachers);
    }

    public List<HouseApplicanTeachers> findHouseApplicanTeachersListByCondition(HouseApplicanTeachers houseApplicanTeachers) {
        return houseApplicanTeachersFeign.findHouseApplicanTeachersListByCondition(houseApplicanTeachers);
    }

    public HouseApplicanTeachers findOneHouseApplicanTeachersByCondition(HouseApplicanTeachers houseApplicanTeachers) {
        return houseApplicanTeachersFeign.findOneHouseApplicanTeachersByCondition(houseApplicanTeachers);
    }

    public long findHouseApplicanTeachersCountByCondition(HouseApplicanTeachers houseApplicanTeachers) {
        return houseApplicanTeachersFeign.findHouseApplicanTeachersCountByCondition(houseApplicanTeachers);
    }

    public void updateHouseApplicanTeachers(HouseApplicanTeachers houseApplicanTeachers) {
        houseApplicanTeachersFeign.updateHouseApplicanTeachers(houseApplicanTeachers);
    }

    public void deleteHouseApplicanTeachers(String id) {
        houseApplicanTeachersFeign.deleteHouseApplicanTeachers(id);
    }

    public void deleteHouseApplicanTeachersByCondition(HouseApplicanTeachers houseApplicanTeachers) {
        houseApplicanTeachersFeign.deleteHouseApplicanTeachersByCondition(houseApplicanTeachers);
    }
}
