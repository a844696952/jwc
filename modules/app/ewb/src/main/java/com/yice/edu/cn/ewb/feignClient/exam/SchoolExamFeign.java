package com.yice.edu.cn.ewb.feignClient.exam;

import com.yice.edu.cn.common.pojo.jw.exam.examManage.SchoolExam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jw",contextId = "schoolExamFeign",path = "/schoolExam")
public interface SchoolExamFeign {
    /**
     * 学情分析特殊筛选条件使用
     * @param schoolExam
     * @return
     */
    @PostMapping("/findSchoolExamListByCondition2")
    List<SchoolExam> findSchoolExamListByCondition2(SchoolExam schoolExam);
    @PostMapping("/findSchoolExamCountByCondition2")
    long findSchoolExamCountByCondition2(SchoolExam schoolExam);
    @GetMapping("/findSchoolExamById/{id}")
    SchoolExam findSchoolExamById(@PathVariable("id") String id);
}
