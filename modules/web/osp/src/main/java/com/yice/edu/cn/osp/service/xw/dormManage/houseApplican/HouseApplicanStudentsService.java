package com.yice.edu.cn.osp.service.xw.dormManage.houseApplican;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanStudents;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.houseApplican.HouseApplicanStudentsFeign;
import org.apache.poi.ss.usermodel.Workbook;
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



    public List<HouseApplicanStudents> findHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsFeign.findHouseApplicanStudents(houseApplicanStudents);
    }

    public long findHouseApplicanStudentsCount(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsFeign.findHouseApplicanStudentsCount(houseApplicanStudents);
    }

    public HouseApplicanStudents lookHouseApplicanStudentsByhouseApplicanId(String houseApplicanId) {
        return houseApplicanStudentsFeign.lookHouseApplicanStudentsByhouseApplicanId(houseApplicanId);
    }

    public HouseApplicanStudents saveHouseApplicanStudents1(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsFeign.saveHouseApplicanStudents1(houseApplicanStudents);
    }

    public List<HouseApplicanStudents> lookHouseApplicanStudents(HouseApplicanStudents houseApplicanStudents) {
        return houseApplicanStudentsFeign.lookHouseApplicanStudents(houseApplicanStudents);
    }

    public long lookHouseApplicanStudentsCount(String houseApplicanId) {
        return houseApplicanStudentsFeign.lookHouseApplicanStudentsCount(houseApplicanId);
    }

    public Workbook export(String houseApplicanId) {
        HouseApplicanStudents houseApplicanStudents1 = new HouseApplicanStudents();
        houseApplicanStudents1.setHouseApplicanId(houseApplicanId);
        List<HouseApplicanStudents> houseApplicanStudentsList = houseApplicanStudentsFeign.lookHouseApplicanStudents(houseApplicanStudents1);
        return ExcelExportUtil.exportExcel(new ExportParams(),
                HouseApplicanStudents.class, houseApplicanStudentsList);

    }
}
