package com.yice.edu.cn.osp.feignClient.jw.stuEvaluate;

import com.yice.edu.cn.common.pojo.jw.stuEvaluate.HistoryPojo;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluate;
import com.yice.edu.cn.common.pojo.jw.stuEvaluate.StuEvaluateSendObject;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClassesForStuEvaluate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@FeignClient(value="jw",contextId = "stuEvaluateFeign",path = "/stuEvaluate")
public interface StuEvaluateFeign {
    @GetMapping("/findStuEvaluateById/{id}")
    StuEvaluate findStuEvaluateById(@PathVariable("id") String id);
    @PostMapping("/saveStuEvaluate")
    StuEvaluate saveStuEvaluate(StuEvaluate stuEvaluate);
    @PostMapping("/findStuEvaluateListByCondition")
    List<StuEvaluate> findStuEvaluateListByCondition(StuEvaluate stuEvaluate);
    @PostMapping("/findOneStuEvaluateByCondition")
    StuEvaluate findOneStuEvaluateByCondition(StuEvaluate stuEvaluate);
    @PostMapping("/findStuEvaluateCountByCondition")
    long findStuEvaluateCountByCondition(StuEvaluate stuEvaluate);
    @PostMapping("/updateStuEvaluate")
    void updateStuEvaluate(StuEvaluate stuEvaluate);
    @GetMapping("/deleteStuEvaluate/{id}")
    void deleteStuEvaluate(@PathVariable("id") String id);
    @PostMapping("/deleteStuEvaluateByCondition")
    void deleteStuEvaluateByCondition(StuEvaluate stuEvaluate);
    @GetMapping("/findClassesHeadTeacherBySchoolId")
    List<TeacherClassesForStuEvaluate> findClassesHeadTeacherBySchoolId(String schoolId);
    @PostMapping("/saveStuEvaluateSendObject")
    void saveStuEvaluateSendObject(ArrayList<StuEvaluateSendObject> sendObjectList);

    @PostMapping("/moveStuEvaluateToHistory")
    void moveStuEvaluateToHistory(HistoryPojo historyPojo);
}
