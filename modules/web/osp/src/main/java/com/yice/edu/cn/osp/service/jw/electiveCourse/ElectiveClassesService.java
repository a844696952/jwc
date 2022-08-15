package com.yice.edu.cn.osp.service.jw.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveClasses;
import com.yice.edu.cn.osp.feignClient.jw.electiveCourse.ElectiveClassesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectiveClassesService {
    @Autowired
    private ElectiveClassesFeign electiveClassesFeign;

    public ElectiveClasses findElectiveClassesById(String id) {
        return electiveClassesFeign.findElectiveClassesById(id);
    }

    public ElectiveClasses saveElectiveClasses(ElectiveClasses electiveClasses) {
        return electiveClassesFeign.saveElectiveClasses(electiveClasses);
    }

    public List<ElectiveClasses> findElectiveClassesListByCondition(ElectiveClasses electiveClasses) {
        return electiveClassesFeign.findElectiveClassesListByCondition(electiveClasses);
    }

    public ElectiveClasses findOneElectiveClassesByCondition(ElectiveClasses electiveClasses) {
        return electiveClassesFeign.findOneElectiveClassesByCondition(electiveClasses);
    }

    public long findElectiveClassesCountByCondition(ElectiveClasses electiveClasses) {
        return electiveClassesFeign.findElectiveClassesCountByCondition(electiveClasses);
    }

    public void updateElectiveClasses(ElectiveClasses electiveClasses) {
        electiveClassesFeign.updateElectiveClasses(electiveClasses);
    }

    public void deleteElectiveClasses(String id) {
        electiveClassesFeign.deleteElectiveClasses(id);
    }

    public void deleteElectiveClassesByCondition(ElectiveClasses electiveClasses) {
        electiveClassesFeign.deleteElectiveClassesByCondition(electiveClasses);
    }
}
