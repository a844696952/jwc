package com.yice.edu.cn.osp.service.jw.electiveCourse;

import com.yice.edu.cn.common.pojo.jw.electiveCourse.ElectiveSet;
import com.yice.edu.cn.osp.feignClient.jw.electiveCourse.ElectiveSetFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectiveSetService {
    @Autowired
    private ElectiveSetFeign electiveSetFeign;

    public ElectiveSet findElectiveSetById(String id) {
        return electiveSetFeign.findElectiveSetById(id);
    }

    public ElectiveSet saveElectiveSet(ElectiveSet electiveSet) {
        return electiveSetFeign.saveElectiveSet(electiveSet);
    }

    public List<ElectiveSet> findElectiveSetListByCondition(ElectiveSet electiveSet) {
        return electiveSetFeign.findElectiveSetListByCondition(electiveSet);
    }

    public ElectiveSet findOneElectiveSetByCondition(ElectiveSet electiveSet) {
        return electiveSetFeign.findOneElectiveSetByCondition(electiveSet);
    }

    public long findElectiveSetCountByCondition(ElectiveSet electiveSet) {
        return electiveSetFeign.findElectiveSetCountByCondition(electiveSet);
    }

    public void updateElectiveSet(ElectiveSet electiveSet) {
        electiveSetFeign.updateElectiveSet(electiveSet);
    }

    public void deleteElectiveSet(String id) {
        electiveSetFeign.deleteElectiveSet(id);
    }

    public void deleteElectiveSetByCondition(ElectiveSet electiveSet) {
        electiveSetFeign.deleteElectiveSetByCondition(electiveSet);
    }
}
