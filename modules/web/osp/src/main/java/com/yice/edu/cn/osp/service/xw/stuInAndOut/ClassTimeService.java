package com.yice.edu.cn.osp.service.xw.stuInAndOut;

import com.yice.edu.cn.common.pojo.xw.stuInAndOut.ClassTime;
import com.yice.edu.cn.osp.feignClient.xw.stuInAndOut.ClassTimeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassTimeService {
    @Autowired
    private ClassTimeFeign classTimeFeign;

    public ClassTime findClassTimeById(String id) {
        return classTimeFeign.findClassTimeById(id);
    }

    public ClassTime saveClassTime(ClassTime classTime) {
        return classTimeFeign.saveClassTime(classTime);
    }

    public List<ClassTime> findClassTimeListByCondition(ClassTime classTime) {
        return classTimeFeign.findClassTimeListByCondition(classTime);
    }

    public ClassTime findOneClassTimeByCondition(ClassTime classTime) {
        return classTimeFeign.findOneClassTimeByCondition(classTime);
    }

    public long findClassTimeCountByCondition(ClassTime classTime) {
        return classTimeFeign.findClassTimeCountByCondition(classTime);
    }

    public void updateClassTime(ClassTime classTime) {
        classTimeFeign.updateClassTime(classTime);
    }

    public void deleteClassTime(String id) {
        classTimeFeign.deleteClassTime(id);
    }

    public void deleteClassTimeByCondition(ClassTime classTime) {
        classTimeFeign.deleteClassTimeByCondition(classTime);
    }
}
