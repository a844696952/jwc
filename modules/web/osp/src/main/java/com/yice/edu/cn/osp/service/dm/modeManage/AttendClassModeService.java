package com.yice.edu.cn.osp.service.dm.modeManage;

import com.yice.edu.cn.common.pojo.dm.modeManage.ExamTask;
import com.yice.edu.cn.osp.feignClient.dm.modeManage.AttendClassModeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendClassModeService {

    @Autowired
    private AttendClassModeFeign attendClassModeFeign;

    public List<String> openAttendClassMode(String schoolId) {
        return attendClassModeFeign.openAttendClassMode(schoolId);
    }

    public List<String> closeAttendClassMode(String schoolId) {
        return attendClassModeFeign.closeAttendClassMode(schoolId);
    }

    public ExamTask findClassModeTask(String id) {
        return attendClassModeFeign.findClassModeTask(id);
    }
}
