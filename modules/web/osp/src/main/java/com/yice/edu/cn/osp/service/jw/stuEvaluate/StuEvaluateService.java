package com.yice.edu.cn.osp.service.jw.stuEvaluate;

import com.yice.edu.cn.common.pojo.jw.stuEvaluate.HistoryPojo;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluate;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateSendObject;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesForStuEvaluate;
import com.yice.edu.cn.osp.feignClient.jw.stuEvaluate.StuEvaluateFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StuEvaluateService {
    @Autowired
    private StuEvaluateFeign stuEvaluateFeign;

    public StuEvaluate findStuEvaluateById(String id) {
        return stuEvaluateFeign.findStuEvaluateById(id);
    }

    public StuEvaluate saveStuEvaluate(StuEvaluate stuEvaluate) {
        return stuEvaluateFeign.saveStuEvaluate(stuEvaluate);
    }

    public List<StuEvaluate> findStuEvaluateListByCondition(StuEvaluate stuEvaluate) {
        return stuEvaluateFeign.findStuEvaluateListByCondition(stuEvaluate);
    }

    public StuEvaluate findOneStuEvaluateByCondition(StuEvaluate stuEvaluate) {
        return stuEvaluateFeign.findOneStuEvaluateByCondition(stuEvaluate);
    }

    public long findStuEvaluateCountByCondition(StuEvaluate stuEvaluate) {
        return stuEvaluateFeign.findStuEvaluateCountByCondition(stuEvaluate);
    }

    public void updateStuEvaluate(StuEvaluate stuEvaluate) {
        stuEvaluateFeign.updateStuEvaluate(stuEvaluate);
    }

    public void deleteStuEvaluate(String id) {
        stuEvaluateFeign.deleteStuEvaluate(id);
    }

    public void deleteStuEvaluateByCondition(StuEvaluate stuEvaluate) {
        stuEvaluateFeign.deleteStuEvaluateByCondition(stuEvaluate);
    }

    public List<TeacherClassesForStuEvaluate> findClassesHeadTeacherBySchoolId(String schoolId) {
        return  stuEvaluateFeign.findClassesHeadTeacherBySchoolId(schoolId);

    }

    public void saveStuEvaluateSendObject(ArrayList<StuEvaluateSendObject> sendObjectList) {
        stuEvaluateFeign.saveStuEvaluateSendObject(sendObjectList);
    }

    public void moveStuEvaluateToHistory(HistoryPojo historyPojo) {
        stuEvaluateFeign.moveStuEvaluateToHistory(historyPojo);
    }
}
