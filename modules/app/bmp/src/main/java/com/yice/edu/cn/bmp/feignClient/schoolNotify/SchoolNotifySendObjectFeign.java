package com.yice.edu.cn.bmp.feignClient.schoolNotify;

import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.schoolNotify.SchoolNotifySendObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",path = "/schoolNotifySendObject")
public interface SchoolNotifySendObjectFeign {
    @GetMapping("/findSchoolNotifySendObjectById/{id}")
    SchoolNotifySendObject findSchoolNotifySendObjectById(@PathVariable("id") String id);
    @PostMapping("/saveSchoolNotifySendObject")
    SchoolNotifySendObject saveSchoolNotifySendObject(SchoolNotifySendObject schoolNotifySendObject);
    @PostMapping("/findSchoolNotifySendObjectListByCondition")
    List<SchoolNotifySendObject> findSchoolNotifySendObjectListByCondition(SchoolNotifySendObject schoolNotifySendObject);
    @PostMapping("/findOneSchoolNotifySendObjectByCondition")
    SchoolNotifySendObject findOneSchoolNotifySendObjectByCondition(SchoolNotifySendObject schoolNotifySendObject);
    @PostMapping("/findSchoolNotifySendObjectCountByCondition")
    long findSchoolNotifySendObjectCountByCondition(SchoolNotifySendObject schoolNotifySendObject);
    @PostMapping("/updateSchoolNotifySendObject")
    void updateSchoolNotifySendObject(SchoolNotifySendObject schoolNotifySendObject);
    @GetMapping("/deleteSchoolNotifySendObject/{id}")
    void deleteSchoolNotifySendObject(@PathVariable("id") String id);
    @PostMapping("/deleteSchoolNotifySendObjectByCondition")
    void deleteSchoolNotifySendObjectByCondition(SchoolNotifySendObject schoolNotifySendObject);

    @PostMapping("/getSchoolNotifyReadDetail")
    List<Department> getSchoolNotifyReadDetail(SchoolNotifySendObject schoolNotifySendObject);
}
