package com.yice.edu.cn.tap.service.xwDormManage.houseApplican;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanStudents;
import com.yice.edu.cn.tap.feignClient.xwDormManage.houseApplican.HouseApplicanStudentsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseApplicanStudentsService {
    @Autowired
    private HouseApplicanStudentsFeign houseApplicanStudentsFeign;

    public HouseApplicanStudents findHouseApplicanStudentsById(String id) {
        return houseApplicanStudentsFeign.findHouseApplicanStudentsById(id);
    }

    public HouseApplicanStudents saveHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsFeign.saveHouseApplicanStudents(houseApplicanStudents);
    }

    public List<HouseApplicanStudents> findHouseApplicanStudentsListByCondition(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsFeign.findHouseApplicanStudentsListByCondition(houseApplicanStudents);
    }

    public HouseApplicanStudents findOneHouseApplicanStudentsByCondition(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsFeign.findOneHouseApplicanStudentsByCondition(houseApplicanStudents);
    }

    public long findHouseApplicanStudentsCountByCondition(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsFeign.findHouseApplicanStudentsCountByCondition(houseApplicanStudents);
    }

    public void updateHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents) {
        houseApplicanStudentsFeign.updateHouseApplicanStudents(houseApplicanStudents);
    }

    public void deleteHouseApplicanStudents(String id) {
        houseApplicanStudentsFeign.deleteHouseApplicanStudents(id);
    }

    public void deleteHouseApplicanStudentsByCondition(HouseApplicanStudents houseApplicanStudents) {
        houseApplicanStudentsFeign.deleteHouseApplicanStudentsByCondition(houseApplicanStudents);
    }
}
