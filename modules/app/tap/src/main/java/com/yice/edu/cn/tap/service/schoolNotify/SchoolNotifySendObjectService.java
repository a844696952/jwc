package com.yice.edu.cn.tap.service.schoolNotify;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import com.yice.edu.cn.tap.feignClient.schoolNotify.SchoolNotifySendObjectFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolNotifySendObjectService {
    @Autowired
    private SchoolNotifySendObjectFeign schoolNotifySendObjectFeign;

    public SchoolNotifySendObject findSchoolNotifySendObjectById(String id) {
        return schoolNotifySendObjectFeign.findSchoolNotifySendObjectById(id);
    }

    public SchoolNotifySendObject saveSchoolNotifySendObject(SchoolNotifySendObject schoolNotifySendObject) {
        return schoolNotifySendObjectFeign.saveSchoolNotifySendObject(schoolNotifySendObject);
    }

    public List<SchoolNotifySendObject> findSchoolNotifySendObjectListByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        return schoolNotifySendObjectFeign.findSchoolNotifySendObjectListByCondition(schoolNotifySendObject);
    }

    public SchoolNotifySendObject findOneSchoolNotifySendObjectByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        return schoolNotifySendObjectFeign.findOneSchoolNotifySendObjectByCondition(schoolNotifySendObject);
    }

    public long findSchoolNotifySendObjectCountByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        return schoolNotifySendObjectFeign.findSchoolNotifySendObjectCountByCondition(schoolNotifySendObject);
    }

    public void updateSchoolNotifySendObject(SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObjectFeign.updateSchoolNotifySendObject(schoolNotifySendObject);
    }

    public void deleteSchoolNotifySendObject(String id) {
        schoolNotifySendObjectFeign.deleteSchoolNotifySendObject(id);
    }

    public void deleteSchoolNotifySendObjectByCondition(SchoolNotifySendObject schoolNotifySendObject) {
        schoolNotifySendObjectFeign.deleteSchoolNotifySendObjectByCondition(schoolNotifySendObject);
    }


    public  List<Department> getSchoolNotifyReadDetail(SchoolNotifySendObject schoolNotifySendObject) {
        return  schoolNotifySendObjectFeign.getSchoolNotifyReadDetail(schoolNotifySendObject);
    }

    public  long getSchoolNotifyReadDetailCount(SchoolNotifySendObject schoolNotifySendObject) {
        return  schoolNotifySendObjectFeign.getSchoolNotifyReadDetailCount(schoolNotifySendObject);
    }

}
